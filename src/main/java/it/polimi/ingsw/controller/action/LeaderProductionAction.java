package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

public class LeaderProductionAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private int leaderCardIndex;

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
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
