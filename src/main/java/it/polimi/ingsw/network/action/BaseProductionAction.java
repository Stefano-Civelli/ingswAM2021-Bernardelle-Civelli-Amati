package it.polimi.ingsw.network.action;

import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

public class BaseProductionAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private ResourceType resource1,
            resource2,
            product;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException,
            InvalidUsernameException, AlreadyProducedException, NegativeQuantityException,
            NotEnoughResourcesException, AbuseOfFaithException, NeedAResourceToAddException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).baseProduction(this.resource1, this.resource2, this.product);
        return PhaseType.PRODUCING;
    }

}
