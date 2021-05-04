package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class EndTurnAction extends Action {

    public EndTurnAction() {
        super(ActionType.END_TURN);
    }

    public EndTurnAction(String username) {
        super(ActionType.END_TURN, username);
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).enterFinalTurnPhase();
        return gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER ? PhaseType.END_SETUP : PhaseType.END;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.END_TURN);
    }

}
