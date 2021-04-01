package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.IncorrectResourceTypeException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughSpaceException;

public class WhiteMarble extends MarketMarble{

    public WhiteMarble(){}

    @Override
    public void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble)
            throws IncorrectResourceTypeException, AbuseOfFaithException, NotEnoughSpaceException {
        playerBoard.getWarehouse().addResources(onWhiteMarble, warehouseLevel, 1);
    }

    /*@Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }*/

}
