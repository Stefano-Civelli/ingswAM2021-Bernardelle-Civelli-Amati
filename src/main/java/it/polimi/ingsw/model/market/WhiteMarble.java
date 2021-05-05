package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.stream.Collectors;

public class WhiteMarble extends MarketMarble{

    public WhiteMarble() {
        super(MarbleType.WHITE);
    }

    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     * @throws MoreWhiteLeaderCardsException the player has more than one leader card that can modify this type of marble and is not specified which must be used.
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard)
            throws NotEnoughSpaceException, MoreWhiteLeaderCardsException {

        ResourceType resourceOnWhite = null;

        for(ResourceType resourceLeader : playerBoard.getLeaderCards().stream().map(LeaderCard::resourceOnWhite).collect(Collectors.toList()))
            if(resourceLeader != null) {
                if (resourceOnWhite != null)
                    throw new MoreWhiteLeaderCardsException();
                resourceOnWhite = resourceLeader;
            }

        try{
            playerBoard.getWarehouse().addResource(resourceOnWhite);
        } catch (AbuseOfFaithException e) {
            // The resource that must be added is faith -> add it in track
            playerBoard.getTrack().moveForward(1);
        } catch (NullPointerException ignored) {
            // The resource that must be added is null -> do nothing
            // Don't print the stack trace because it's in the normal flow of the program that this exception can be thrown
        }

    }

    /**
     * Adds the resources owed to the player due to this marble converted using the specified leader
     *
     * @param playerBoard the player board of the player to whom the resources belong
     * @param leaderCard the leader card that must be used to convert this marble
     * @throws NotEnoughSpaceException there isn't enough space in the warehouse to add this resource
     * @throws WrongLeaderCardException the specified leader card don't convert this marble
     * @throws NullPointerException the specified leader card is null
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard, LeaderCard leaderCard)
            throws NotEnoughSpaceException, WrongLeaderCardException {

        ResourceType resource = leaderCard.resourceOnWhite();
        try{
            playerBoard.getWarehouse().addResource(resource);
        } catch (AbuseOfFaithException e) {
            // The resource that must be added is faith -> add it in track
            playerBoard.getTrack().moveForward(1);
        } catch (NullPointerException e) {
            throw new WrongLeaderCardException();
        }

    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

}
