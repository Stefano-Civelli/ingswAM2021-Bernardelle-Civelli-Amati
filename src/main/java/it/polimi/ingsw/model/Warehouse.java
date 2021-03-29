package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelException.WarehouseException;
import it.polimi.ingsw.utility.Pair;

import java.util.Arrays;
import java.util.Objects;

public class Warehouse {

    private Pair<ResourceType, Integer>[] levels;

    public Warehouse() {
        this.levels = new Pair[3];
    }

    public void addResources(ResourceType resource, int level, int quantity) throws WarehouseException {
        if(resource == null)
            throw new NullPointerException();
        if(resource == ResourceType.FAITH)
            throw new WarehouseException("Adding faith to warehouse is not allowed");
        if(level >= 0 && level < this.levels.length) {
            if(this.levels[level] == null)
                this.levels[level] = new Pair<ResourceType, Integer>(resource, quantity);
            if(this.levels[level].getKey() == resource) {
                if (this.levels[level].getValue() + quantity >= this.levels.length - level)
                    throw new WarehouseException("In level " + level + " of warehouse there is not enough space");
                this.levels[level] = new Pair<ResourceType, Integer>(resource, this.levels[level].getValue() + quantity);
            }
            else
                throw new WarehouseException("In level " + level + " of warehouse there is already another type of resource");
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }

    public void removeResources(ResourceType resource, int level, int quantity) throws WarehouseException {
        if(resource == null)
            throw new NullPointerException();
        if(level >= 0 && level < this.levels.length) {
            if(this.levels[level] == null)
                throw new WarehouseException("The level " + level + " of warehouse is empty");
            if(this.levels[level].getKey() != resource)
                throw new WarehouseException("In level " + level + " of warehouse there is another type of resource");
            if(this.levels[level].getValue() < quantity )
                throw new WarehouseException("In level " + level + " of warehouse there aren't enough resources");
            if(this.levels[level].getValue() > quantity )
                this.levels[level] = new Pair<ResourceType, Integer>(resource, this.levels[level].getValue() - quantity);
            if(this.levels[level].getValue() == quantity )
                this.levels[level] = null;
        }
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }

    public void reOrganize(int sourceLevel, int destinationLeve ){



    }

    public int getNumberOf(ResourceType resource) { return 0; }

    public int totalResources() {
        return Arrays.stream(this.levels).filter(Objects::nonNull).map(Pair::getValue).reduce(Integer::sum).orElse(0);
        // AGGIUNGERE LE LEADER QUANDO IMPLEMENTATE
    }
}