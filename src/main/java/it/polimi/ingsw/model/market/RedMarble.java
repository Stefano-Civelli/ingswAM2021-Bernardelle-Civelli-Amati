package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.track.Track;

public class RedMarble extends MarketMarble{

    public RedMarble(){}

    @Override
    public void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble) {
        playerBoard.getTrack().moveForward(1);
    }

    /*@Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }*/

}
