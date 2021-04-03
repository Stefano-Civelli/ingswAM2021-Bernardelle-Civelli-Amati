package it.polimi.ingsw.model;

import it.polimi.ingsw.modeltest.tracktest.Track;

public interface InterfacePlayerBoard {

    Warehouse getWarehouse();

    Chest getChest();

    Track getTrack();

    CardSlots getCardSlots();
}
