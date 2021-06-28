package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * An action to request to terminate a player's turn
 */
public class EndTurnAction extends Action {

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private EndTurnAction() {
        super(ActionType.END_TURN);
    }

    public EndTurnAction(String username) {
        super(ActionType.END_TURN, username);
    }

    /**
     * Terminate the turn of these player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     */
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
        return gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDING_LEADERS ? PhaseType.END_SETUP : PhaseType.END;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.END_TURN);
    }

    private boolean isActionValid() {
        return super.getUsername() != null;
    }

}
