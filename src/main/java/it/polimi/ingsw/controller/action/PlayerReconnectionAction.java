package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;

/**
 * An action to notify the model that a disconnected player is reconnected
 */
@Deprecated
public class PlayerReconnectionAction extends Action {

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private PlayerReconnectionAction() {
        super(ActionType.PLAYER_RECONNECTION);
    }

    public PlayerReconnectionAction(String username) {
        super(ActionType.PLAYER_RECONNECTION, username);
    }

    /**
     * Reconnect a Player
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
        gameState.getGame().reconnectPlayer(super.getUsername());
        return null;
    }

    private boolean isActionValid() {
        return super.getUsername() != null;
    }

}
