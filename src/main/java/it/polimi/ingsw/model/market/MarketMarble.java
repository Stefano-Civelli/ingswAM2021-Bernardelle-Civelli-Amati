package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

public abstract class MarketMarble {

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param warehouseLevel the level at which to add resources, if these are to be put into the warehouse, otherwise it will be ignored
     * @param onWhiteMarble in case this marble is white the resource type in which convert it, otherwise it will be ignored
     * @throws IncorrectResourceTypeException the type of resources corresponding to this marble can't be added to this level of warehouse because it's occupied by another resource type
     * @throws NotEnoughSpaceException in this level of warehouse there isn't enough space
     * @throws LevelNotExistsException this level of warehouse doesn't exists
     */
    public abstract void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble)
            throws IncorrectResourceTypeException, NotEnoughSpaceException, LevelNotExistsException;

}
