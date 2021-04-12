package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.Optional;

public abstract class MarketMarble {

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param leaderCard in case the player has more than one leader card that can modify this type of marble:
     *                   the leader card that must be used to convert it; otherwise it will be ignored.
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     * @throws MoreWhiteLeaderCardsException in case the player has more than one leader card that can modify this type of marble,
     *                   and is not specified which must be used.
     */

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public abstract void addResource(InterfacePlayerBoard playerBoard, Optional<LeaderCard> leaderCard)
            throws NotEnoughSpaceException, MoreWhiteLeaderCardsException;
}
