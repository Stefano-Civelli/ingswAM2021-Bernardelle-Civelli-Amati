package it.polimi.ingsw.network.Action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.ModelException;

public class BaseProductionAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private ResourceType resource1, resource2, product;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.userName).baseProduction(this.resource1, this.resource2, this.product);
        return PhaseType.PRODUCING;
    }

}
