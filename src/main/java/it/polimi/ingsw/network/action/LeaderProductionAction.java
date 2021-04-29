package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

public class LeaderProductionAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private int leaderCardIndex;

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private ResourceType product;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException,
            InvalidUsernameException, NeedAResourceToAddException, AlreadyProducedException,
            NotEnoughResourcesException, AbuseOfFaithException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).leaderProduce(this.leaderCardIndex, this.product);
        return PhaseType.PRODUCING;
    }
}
