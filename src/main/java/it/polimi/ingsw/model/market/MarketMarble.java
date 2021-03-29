package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelException.WarehouseException;
import it.polimi.ingsw.model.track.Track;

public abstract class MarketMarble {

    public abstract void addResource(Track track, Warehouse warehouse, ResourceType onWhiteMarble) throws WarehouseException;

}
