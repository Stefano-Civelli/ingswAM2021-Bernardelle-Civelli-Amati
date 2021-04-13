package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.Optional;

public class RedMarble extends MarketMarble{

    public RedMarble(){}

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard) {
        playerBoard.getTrack().moveForward(1);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

}
