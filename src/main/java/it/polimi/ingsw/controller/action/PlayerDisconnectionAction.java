package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;

public class PlayerDisconnectionAction extends Action {

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private PlayerDisconnectionAction() {
        super(ActionType.PLAYER_DISCONNECTION);
    }

    public PlayerDisconnectionAction(String username) {
        super(ActionType.PLAYER_DISCONNECTION, username);
    }

    /**
     * Disconnect a Player
     *
     * @param gameState the current state of this game
     * @return null
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     */
    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, InvalidUsernameException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        gameState.getGame().getPlayerBoard(super.getUsername()).enterFinalTurnPhase();
        gameState.getGame().disconnectPlayer(super.getUsername());
        return super.isCurrentPlayer(gameState)
                ? ( gameState.getCurrentPhase().isSetup() ? PhaseType.END_SETUP : PhaseType.END )
                : null;
    }

    private boolean isActionValid() {
        return super.getUsername() != null;
    }

}
