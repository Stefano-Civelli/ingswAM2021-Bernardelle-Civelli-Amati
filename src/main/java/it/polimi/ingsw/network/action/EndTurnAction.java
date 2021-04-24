package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.ModelException;

public class EndTurnAction extends Action {

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.userName).enterFinalTurnPhase();
        return gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER ? PhaseType.END_SETUP : PhaseType.END;
    }

}
