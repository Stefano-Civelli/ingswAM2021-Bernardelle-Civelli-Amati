package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public interface InterfacePlayerBoard {

    Warehouse getWarehouse();

    Chest getChest();

    Track getTrack();

    CardSlots getCardSlots();

    ArrayList<LeaderCard> getLeaderCards();

    DevelopCardDeck getDevelopCardDeck();
}
