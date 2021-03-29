package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.track.Track;

public class RedMarble extends MarketMarble{

    public RedMarble(){}

    @Override
    public void addResource(Track track, Warehouse warehouse, ResourceType onWhiteMarble) {
        track.moveForward(1);
    }

}
