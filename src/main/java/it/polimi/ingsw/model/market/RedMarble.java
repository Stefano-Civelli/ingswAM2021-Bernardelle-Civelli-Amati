package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;

public class RedMarble extends MarketMarble{

    public RedMarble(){}

    /**
     * adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player which the resources belong
     * @param warehouseLevel this parameter will be ignored in this type of marble
     * @param onWhiteMarble this parameter will be ignored in this type of marble
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard, int warehouseLevel, ResourceType onWhiteMarble) {
        playerBoard.getTrack().moveForward(1);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }
}
