package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.Optional;
import java.util.stream.Collectors;

public class WhiteMarble extends MarketMarble{

    public WhiteMarble(){}

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param leaderCard in case the player has more than one leader card that can modify this type of marble:
     *                   the leader card that must be used to convert it; otherwise it will be ignored.
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     * @throws MoreWhiteLeaderCardsException the player has more than one leader card that can modify this type of marble and is not specified which must be used.
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard, Optional<LeaderCard> leaderCard)
            throws NotEnoughSpaceException, MoreWhiteLeaderCardsException {

        ResourceType resourceOnWhite = null;

        if(leaderCard.isPresent()) {
            resourceOnWhite = leaderCard.get().resourceOnWhite();
        } else {
            for(ResourceType resourceLeader : playerBoard.getLeaderCards().stream().map(LeaderCard::resourceOnWhite).collect(Collectors.toList()))
                if(resourceLeader != null) {
                    if (resourceOnWhite != null)
                        throw new MoreWhiteLeaderCardsException();
                    resourceOnWhite = resourceLeader;
                }
        }

        try{
            playerBoard.getWarehouse().addResource(resourceOnWhite);
        } catch (AbuseOfFaithException ignored){
            playerBoard.getTrack().moveForward(1);
        }

    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }
}
