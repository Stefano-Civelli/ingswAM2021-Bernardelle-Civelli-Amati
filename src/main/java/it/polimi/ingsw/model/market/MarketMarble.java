package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.Optional;

public abstract class MarketMarble {

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param leaderCard in case this marble is white the resource type in which convert it, otherwise it will be ignored
     * @throws NotEnoughSpaceException in this level of warehouse there isn't enough space
     */
    public abstract void addResource(InterfacePlayerBoard playerBoard, Optional<LeaderCard> leaderCard)
            throws NotEnoughSpaceException;

}
