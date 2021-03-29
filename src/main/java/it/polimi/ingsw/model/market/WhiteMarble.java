package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelException.WarehouseException;
import it.polimi.ingsw.model.track.Track;

public class WhiteMarble extends MarketMarble{

    public WhiteMarble(){}

    @Override
    public void addResource(Track track, Warehouse warehouse, ResourceType onWhiteMarble) throws WarehouseException {
        warehouse.addResources(onWhiteMarble, 1, 1);
    }

}
