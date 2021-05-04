package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class EndTurnAction extends Action {

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private EndTurnAction() {
        super(ActionType.END_TURN);
    }

    public EndTurnAction(String username) {
        super(ActionType.END_TURN, username);
    }

    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        gameState.getGame().getPlayerBoard(super.getUsername()).enterFinalTurnPhase();
        return gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER ? PhaseType.END_SETUP : PhaseType.END;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.END_TURN);
    }

    private boolean isActionValid() {
        return super.getUsername() != null;
    }

}
