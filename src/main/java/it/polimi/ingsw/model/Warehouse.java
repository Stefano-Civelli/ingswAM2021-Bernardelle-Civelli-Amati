package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.Pair;

import java.util.Arrays;
import java.util.Objects;

public class Warehouse {

    private final int NUMBER_OF_WAREHOUSELEVELS = 3;

    private final Pair<ResourceType, Integer>[] levels;

    @SuppressWarnings("unchecked")
    public Warehouse() {
        this.levels = new Pair[NUMBER_OF_WAREHOUSELEVELS];
    }

    public int NumberOfWarehouseLevels() {
        return NUMBER_OF_WAREHOUSELEVELS;
    }

    /**
     * Add some resources in a specified level of warehouse
     *
     * @param resource the type of resource to add
     * @param level the level of warehouse in which to add resources
     * @param quantity the number of resources to add
     * @throws IncorrectResourceTypeException this resource type can't be added in this level of warehouse
     * @throws NotEnoughSpaceException there is not enough space on this level to add this amount of resources
     * @throws AbuseOfFaithException resource type is faith, faith can't be added into warehouse
     * @throws LevelNotExistsException this level doesn't exist
     */
    public void addResources(ResourceType resource, int level, int quantity)
            throws IncorrectResourceTypeException, NotEnoughSpaceException,
            AbuseOfFaithException, LevelNotExistsException {
        if(resource == null)
            throw new NullPointerException();
        if(resource == ResourceType.FAITH)
            throw new AbuseOfFaithException("Adding faith to warehouse is not allowed");
        if(level >= 0 && level < this.levels.length) {
            if(this.levels[level] == null) {
                if(quantity > this.levels.length - level)
                    throw new NotEnoughSpaceException("In level " + level + " of warehouse there is not enough space");
                for(int i = 0; i < this.levels.length; i++)
                    if(i != level && this.levels[i] != null && this.levels[i].getKey() == resource)
                        throw new IncorrectResourceTypeException(
                                "The resource type " + resource + " is already present in another warehouse level"
                        );
                this.levels[level] = new Pair<>(resource, quantity);
            } else if(this.levels[level].getKey() == resource) {
                if (this.levels[level].getValue() + quantity > this.levels.length - level)
                    throw new NotEnoughSpaceException("In level " + level + " of warehouse there is not enough space");
                this.levels[level] = new Pair<>(resource, this.levels[level].getValue() + quantity);
            } else
                throw new IncorrectResourceTypeException(
                    "In level " + level + " of warehouse there is already another type of resource"
                );
            return;
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
        throw new LevelNotExistsException();
    }

    /**
     * Remove some resources from a specified level of warehouse
     *
     * @param resource the type of resource to remove
     * @param level the level of warehouse from which to remove resources
     * @param quantity the number of resources to remove
     * @throws NotEnoughResourcesException there are not enough resources on this level to remove this amount of them
     * @throws IncorrectResourceTypeException In this level there is another type of resources
     * @throws LevelNotExistsException this level doesn't exist
     */
    public void removeResources(ResourceType resource, int level, int quantity)
            throws NotEnoughResourcesException, IncorrectResourceTypeException, LevelNotExistsException {
        if(resource == null)
            throw new NullPointerException();
        if(level >= 0 && level < this.levels.length) {
            if(this.levels[level] == null)
                throw new NotEnoughResourcesException("The level " + level + " of warehouse is empty");
            else if(this.levels[level].getKey() != resource)
                throw new IncorrectResourceTypeException("In level " + level + " of warehouse there is another type of resource");
            else if(this.levels[level].getValue() < quantity )
                throw new NotEnoughResourcesException("In level " + level + " of warehouse there aren't enough resources");
            else if(this.levels[level].getValue() > quantity )
                this.levels[level] = new Pair<>(resource, this.levels[level].getValue() - quantity);
            else if(this.levels[level].getValue() == quantity )
                this.levels[level] = null;
            return;
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
        throw new LevelNotExistsException();
    }

    /**
     * Swap the contained resources of two level
     *
     * @param level1 one level to swap
     * @param level2 the other level to swap
     * @throws NotEnoughSpaceException one of these level too small to contain the number of resources contained in the other
     * @throws LevelNotExistsException one or both of these levels doesn't exist
     */
    public void swapLevels(int level1, int level2) throws NotEnoughSpaceException, LevelNotExistsException {
        if(0 <= level1 && level1 <= this.levels.length - 1 && 0 <= level2 && level2 <= this.levels.length - 1) {
            if(this.levels[level1] != null && this.levels[level1].getValue() >= this.levels.length - level2 ||
                    this.levels[level2] != null && this.levels[level2].getValue() >= this.levels.length - level1)
                throw new NotEnoughSpaceException();
            Pair<ResourceType, Integer> swap = this.levels[level1];
            this.levels[level1] = this.levels[level2];
            this.levels[level2] = swap;
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
        throw new LevelNotExistsException();
    }

    /**
     * Move some resources from one level to another
     *
     * @param sourceLevel the level from which to take resources
     * @param destinationLevel the level in which to put resources
     * @param quantity the number of resources to move
     */
    public void moveResource(int sourceLevel, int destinationLevel, int quantity) {}

    /**
     * Returns the total amount of the specified resources contained in the warehouse
     *
     * @param resource the resource type
     * @return the number of contained resources of this type
     */
    public int getNumberOf(ResourceType resource) {
        return Arrays.stream(this.levels).filter(Objects::nonNull).filter( (i) -> i.getKey() == resource )
                .map(Pair::getValue).reduce(Integer::sum).orElse(0);
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE

    }

    /**
     *
     * @return total number of resources contained in the warehouse
     */
    public int totalResources() {
        return Arrays.stream(this.levels).filter(Objects::nonNull).map(Pair::getValue)
                .reduce(Integer::sum).orElse(0);
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE

    }
}