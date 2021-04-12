package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.Optional;

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
     * @param leaderCard this parameter will be ignored in this type of marble
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard, Optional<LeaderCard> leaderCard)
            throws NotEnoughSpaceException {
        try {
            playerBoard.getWarehouse().addResource(this.resource);
        } catch (AbuseOfFaithException e) {
            // This code should never be executed
            e.printStackTrace();
        }
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
