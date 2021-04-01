package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.IncorrectResourceTypeException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesExeption;
import it.polimi.ingsw.model.modelexceptions.NotEnoughSpaceException;
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

    public void addResources(ResourceType resource, int level, int quantity)
            throws IncorrectResourceTypeException, NotEnoughSpaceException, AbuseOfFaithException {
        if(resource == null)
            throw new NullPointerException();
        if(resource == ResourceType.FAITH)
            throw new AbuseOfFaithException("Adding faith to warehouse is not allowed");
        if(level >= 0 && level < this.levels.length) {
            if(this.levels[level] == null)
                this.levels[level] = new Pair<>(resource, quantity);
            if(this.levels[level].getKey() == resource) {
                if (this.levels[level].getValue() + quantity >= this.levels.length - level)
                    throw new NotEnoughSpaceException("In level " + level + " of warehouse there is not enough space");
                this.levels[level] =
                        new Pair<>(resource, this.levels[level].getValue() + quantity);
            }
            else
                throw new IncorrectResourceTypeException(
                        "In level " + level + " of warehouse there is already another type of resource"
                );
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }

    public void removeResources(ResourceType resource, int level, int quantity)
            throws NotEnoughResourcesExeption, IncorrectResourceTypeException {
        if(resource == null)
            throw new NullPointerException();
        if(level >= 0 && level < this.levels.length) {
            if(this.levels[level] == null)
                throw new NotEnoughResourcesExeption("The level " + level + " of warehouse is empty");
            if(this.levels[level].getKey() != resource)
                throw new IncorrectResourceTypeException("In level " + level + " of warehouse there is another type of resource");
            if(this.levels[level].getValue() < quantity )
                throw new NotEnoughResourcesExeption("In level " + level + " of warehouse there aren't enough resources");
            if(this.levels[level].getValue() > quantity )
                this.levels[level] =
                        new Pair<>(resource, this.levels[level].getValue() - quantity);
            if(this.levels[level].getValue() == quantity )
                this.levels[level] = null;
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }

    public void swapLevels(int Level1, int Level2 ) {}

    public void moveResource(int sourceLevel, int destinationLevel, int quantity) {}

    public int getNumberOf(ResourceType resource) {
        return Arrays.stream(this.levels).filter(Objects::nonNull).filter( (i) -> i.getKey() == resource )
                .map(Pair::getValue).reduce(Integer::sum).orElse(0);
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }

    public int totalResources() {
        return Arrays.stream(this.levels).filter(Objects::nonNull).map(Pair::getValue)
                .reduce(Integer::sum).orElse(0);
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }
}