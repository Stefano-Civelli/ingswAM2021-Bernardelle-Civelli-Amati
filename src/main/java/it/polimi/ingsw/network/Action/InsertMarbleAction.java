package it.polimi.ingsw.network.Action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.ModelException;

public class InsertMarbleAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private int marbleIndex;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.userName).addMarbleToWarehouse(this.marbleIndex);
        return gameState.getGame().getPlayerBoard(super.userName).areMarblesFinished()
                ? PhaseType.FINAL : PhaseType.SHOPPING;
    }

}
