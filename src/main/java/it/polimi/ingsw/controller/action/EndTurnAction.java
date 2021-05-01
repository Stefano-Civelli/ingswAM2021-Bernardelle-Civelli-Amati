package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class EndTurnAction extends Action {

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, InvalidUsernameException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).enterFinalTurnPhase();
        return gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER ? PhaseType.END_SETUP : PhaseType.END;
    }

}
