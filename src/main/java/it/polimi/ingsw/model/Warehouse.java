package it.polimi.ingsw.model;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Warehouse implements ModelObservable{

    private final int NUMBER_OF_NORMAL_LEVELS = 3;
    private final int MAX_SPECIAL_LEVELS = 2;

    @Expose(deserialize = false)
    private Controller controller = null;

    private final Pair<ResourceType, Integer>[] levels;
    private final List<Pair<ResourceType, Integer>> leaderLevels;

    @SuppressWarnings("unchecked")
    public Warehouse() {
        this.levels = new Pair[this.NUMBER_OF_NORMAL_LEVELS];
        this.leaderLevels = new ArrayList<>(this.MAX_SPECIAL_LEVELS);
    }



    public class WarehouseUpdate{
        private ResourceType resourceType;
        private int quantity;
        private int level;

        public WarehouseUpdate(ResourceType resourceType, int quantity, int level) {
            this.resourceType = resourceType;
            this.quantity = quantity;
            this.level = level;
        }

        public ResourceType getResourceType() {
            return resourceType;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     * 
     * @return the maximum number of leader card's levels that this warehouse can contain
     */
    public int maxLeaderCardsLevels() {
        return this.MAX_SPECIAL_LEVELS;
    }

    /**
     * 
     * @return the number of normal levels in this warehouse
     */
    public int numberOfNormalLevels() {
        return this.NUMBER_OF_NORMAL_LEVELS;
    }

    /**
     * 
     * @return the current number of leader card's level in this warehouse
     */
    public int numberOfLeaderCardsLevels() {
        return this.leaderLevels.size();
    }

    /**
     * 
     * @return the total number of levels in this warehouse
     */
    public int numberOfAllLevels() {
        return this.NUMBER_OF_NORMAL_LEVELS + this.leaderLevels.size();
    }

    /**
     * Create a new special level in warehouse for a leader card of type storage
     *
     * @param resourceType the resource type of the storage leader card to activate
     * @return index of the level created
     * @throws MaxLeaderCardLevelsException the maximum number of leader card's levels has already been added. It is not possible to add another one.
     * @throws AbuseOfFaithException resource type is faith, a warehouse can't contain faith
     */
    public int addLeaderCardLevel(ResourceType resourceType)
            throws MaxLeaderCardLevelsException, AbuseOfFaithException {
        if(resourceType == null)
            throw new NullPointerException();
        if(resourceType == ResourceType.FAITH)
            throw new AbuseOfFaithException();
        if(this.numberOfLeaderCardsLevels() + 1 > this.maxLeaderCardsLevels())
            throw new MaxLeaderCardLevelsException();
        Pair<ResourceType, Integer> pair = new Pair<>(resourceType, 0);
        this.leaderLevels.add(pair);
        return this.leaderLevels.lastIndexOf(pair);
    }

    /**
     * Add one resource of the specified resource type into warehouse
     *
     * @param resource the resource type to add
     * @throws NotEnoughSpaceException there isn't enough space for adding this resource, it can't be added.
     * @throws AbuseOfFaithException resource type is faith, faith can't be added into warehouse
     * @throws NullPointerException the specified resource is null
     */
    public void addResource(ResourceType resource) throws AbuseOfFaithException, NotEnoughSpaceException {
        if(resource == null)
            throw new NullPointerException();
        if(resource == ResourceType.FAITH)
            throw new AbuseOfFaithException();

        //Try adding in leader card levels
        for(int i = 0; i < this.numberOfLeaderCardsLevels(); i++) {
            if(this.leaderLevels.get(i).getKey() == resource && this.leaderLevels.get(i).getValue() + 1 <= 2) {
                this.leaderLevels.set(i, new Pair<>(resource, this.leaderLevels.get(i).getValue() + 1));
                notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resource, this.leaderLevels.get(i).getValue(),  i+3))); //3 is the first leader that as been activated
                return;
            }
        }

        //Try adding in normal levels
        int level = this.getNormalLevel(resource);
        if(level != -1) { //There is already a level with this type of resource
            if (this.levels[level].getValue() + 1 <= this.numberOfNormalLevels() - level) {
                this.levels[level] = new Pair<>(resource, this.levels[level].getValue() + 1);
                notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resource, this.levels[level].getValue(),  level)));
                return;
            } else { //Try to swap levels
                for(int i = level - 1; i >= 0; i--) {
                    if(this.levels[level].getValue() + 1 <= this.numberOfNormalLevels() - i
                            && (this.levels[i] == null || this.levels[i].getValue() <= this.numberOfNormalLevels() - level)) {
                        Pair<ResourceType, Integer> swap;
                        swap = this.levels[i];
                        this.levels[i] = new Pair<>(resource, this.levels[level].getValue() + 1);
                        this.levels[level] = swap;
                        notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resource, this.levels[i].getValue(), i)));
                        return;
                    }
                }
            }
        } else { //There isn't a level with this type of resource
            for(int i = this.numberOfNormalLevels() - 1; i >= 0; i--)
                if(this.levels[i] == null) {
                    this.levels[i] = new Pair<>(resource, 1);
                    notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resource, this.levels[i].getValue(),  i)));
                    return;
                }
        }

        throw new NotEnoughSpaceException();
    }

    /**
     * Remove from warehouse all the possible resources of specified resource type up to a maximum of the specified quantity
     *
     * @param resourceType the type of the resource to remove
     * @param quantity the maximum number of resources to remove
     * @return the number of resources which couldn't be removed
     * @throws NegativeQuantityException quantity is a negative number
     * @throws NullPointerException the specified resource is null
     */
    public int removeResources(ResourceType resourceType, int quantity) throws NegativeQuantityException {
        if(resourceType == null)
            throw new NullPointerException();
        if(quantity < 0)
            throw new NegativeQuantityException();

        // Remove in normal levels
        int level = this.getNormalLevel(resourceType);
        if(level != -1) {
            if( this.levels[level].getValue() <= quantity) {
                quantity -= this.levels[level].getValue();
                this.levels[level] = null;
                notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resourceType, 0, level)));
            } else {
                this.levels[level] = new Pair<>(resourceType, this.levels[level].getValue() - quantity);
                quantity = 0;
                notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resourceType, this.levels[level].getValue(), level)));
            }

        }

        //Remove in leader card levels
        for(int i = 0; i < this.numberOfLeaderCardsLevels(); i++) {
            if(this.leaderLevels.get(i).getKey() == resourceType) {
                if(this.leaderLevels.get(i).getValue() < quantity) {
                    quantity -= this.leaderLevels.get(i).getValue();
                    this.leaderLevels.set(i, new Pair<>(this.leaderLevels.get(i).getKey(), 0));
                }
                else {
                    this.leaderLevels.set(i, new Pair<>(this.leaderLevels.get(i).getKey(),
                            this.leaderLevels.get(i).getValue() - quantity));
                    quantity = 0;
                }

                notifyModelChange(new Message(MessageType.WAREHOUSE_UPDATE, new WarehouseUpdate(resourceType, this.leaderLevels.get(i).getValue(), i+3)));
            }
        }

        return quantity;
    }

    private int getNormalLevel(ResourceType resource) {
        return Arrays.stream(this.levels).map( (i) -> i!= null ? i.getKey() : null )
                .collect(Collectors.toList()).indexOf(resource) ;
    }

    /**
     * Returns the total amount of the specified resources contained in the warehouse
     *
     * @param resource the resource type
     * @return the number of contained resources of this type
     */
    public int getNumberOf(ResourceType resource) {
        return Arrays.stream(this.levels).filter(Objects::nonNull).filter( (i) -> i.getKey() == resource )
                .map(Pair::getValue).reduce(Integer::sum).orElse(0)
                +
                this.leaderLevels.stream().filter(Objects::nonNull).filter( (i) -> i.getKey() == resource )
                        .map(Pair::getValue).reduce(Integer::sum).orElse(0);
    }

    /**
     *
     * @return total amount of resources contained in the warehouse
     */
    public int totalResources() {
        return Arrays.stream(this.levels).filter(Objects::nonNull).map(Pair::getValue)
                .reduce(Integer::sum).orElse(0)
                +
                this.leaderLevels.stream().filter(Objects::nonNull).map(Pair::getValue)
                        .reduce(Integer::sum).orElse(0);
    }


    @Override
    public void notifyModelChange(Message msg) {
        if (controller != null)
            controller.broadcastUpdate(msg);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
