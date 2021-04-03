package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

public class NormalMarble extends MarketMarble{

    private final ResourceType resource;

    public NormalMarble(ResourceType resource) throws AbuseOfFaithException {
        if(resource == null)
            throw new NullPointerException();
        if(resource == ResourceType.FAITH)
            throw new AbuseOfFaithException();
        this.resource = resource;
    }

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param warehouseLevel the warehouse level at which to add resources
     * @param onWhiteMarble this parameter will be ignored in this type of marble
     * @throws IncorrectResourceTypeException the type of resources corresponding to this marble can't be added to this level of warehouse because it's occupied by another resource type
     * @throws NotEnoughSpaceException in this level of warehouse there isn't enough space
     * @throws LevelNotExistsException this level of warehouse doesn't exists
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble)
            throws IncorrectResourceTypeException, NotEnoughSpaceException, LevelNotExistsException {
        try {
            playerBoard.getWarehouse().addResources(this.resource, warehouseLevel, 1);
        } catch (AbuseOfFaithException | NegativeQuantityException ignored) {}
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        return this.resource == ((NormalMarble) obj).resource;
    }
}
