package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.WarehouseException;
import it.polimi.ingsw.model.track.Track;

public class NormalMarble extends MarketMarble{

    private ResourceType resource;

    public NormalMarble(ResourceType resource) {
        this.resource = resource;
    }

    @Override
    public void addResource(Track track, Warehouse warehouse, ResourceType onWhiteMarble) throws WarehouseException {
        warehouse.addResources(this.resource, 1, 1);
    }

}
