package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * Represents a generic market marble
 */
public abstract class MarketMarble {

    @SuppressWarnings({"unused", "FieldCanBeLocal"}) // Because the field is necessary for JSON serialization and it may be accessed using reflection
    private final MarbleColor color;

    protected MarketMarble(MarbleColor color) {
        if(color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Adds the resources owed to the player due to this marble possibly converting it with the player's leader cards
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     * @throws MoreWhiteLeaderCardsException the player has more than one leader card that can modify this type of marble
     */
    public abstract void addResource(InterfacePlayerBoard playerBoard)
            throws NotEnoughSpaceException, MoreWhiteLeaderCardsException;

    /**
     * Adds the resources owed to the player due to this marble converted using the specified leader card if it can be converted,
     * otherwise the default resources corresponding to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param leaderCard the leader card that must be used to convert this marble,
     *                   if this marble can't be converted this parameter will be ignored
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     * @throws WrongLeaderCardException this type of marble must be converted, but the specified leader card don't convert it
     * @throws NullPointerException this type of marble must be converted, but the specified leader card is null
     */
    public void addResource(InterfacePlayerBoard playerBoard, LeaderCard leaderCard)
            throws NotEnoughSpaceException, WrongLeaderCardException {

        // This default implementation of this method is for a marble that can't be converted with any type of leader card
        try {
            this.addResource(playerBoard);
        } catch (MoreWhiteLeaderCardsException e) {
            // This code should never be executed
            e.printStackTrace();
        }
    }

    public MarbleColor getColor() {
        return color;
    }
}
