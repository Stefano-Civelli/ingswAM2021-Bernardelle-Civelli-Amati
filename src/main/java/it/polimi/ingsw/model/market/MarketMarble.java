package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelException.*;

public abstract class MarketMarble {

    public abstract void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble)
            throws IncorrectResourceTypeException, AbuseOfFaithException, NotEnoughSpaceException;

}
