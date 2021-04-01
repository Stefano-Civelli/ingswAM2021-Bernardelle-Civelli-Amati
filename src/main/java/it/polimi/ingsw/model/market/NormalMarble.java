package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.IncorrectResourceTypeException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughSpaceException;

public class NormalMarble extends MarketMarble{

    private ResourceType resource;

    public NormalMarble(ResourceType resource) {
        this.resource = resource;
    }

    @Override
    public void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble)
            throws IncorrectResourceTypeException, AbuseOfFaithException, NotEnoughSpaceException {
        playerBoard.getWarehouse().addResources(this.resource, warehouseLevel, 1);
    }

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return this.resource == ((NormalMarble) obj).resource;
    }*/

}
