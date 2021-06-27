package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.track.Track;

import java.util.List;

/**
 * An interface used to restrict the visible methods of {@link it.polimi.ingsw.model.PlayerBoard PlayerBoard}
 * when it's passed as a parameter of a method
 */
public interface InterfacePlayerBoard {

    Warehouse getWarehouse();

    Chest getChest();

    Track getTrack();

    CardSlots getCardSlots();

    List<LeaderCard> getLeaderCards();

    DevelopCardDeck getDevelopCardDeck();

}
