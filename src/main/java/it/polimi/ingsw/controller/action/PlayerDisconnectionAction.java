package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.ControllerException;
import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.ModelException;

public class PlayerDisconnectionAction extends Action {

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private PlayerDisconnectionAction() {
        super(ActionType.PLAYER_DISCONNECTION);
    }

    public PlayerDisconnectionAction(String username) {
        super(ActionType.PLAYER_DISCONNECTION, username);
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws ControllerException, ModelException {
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
