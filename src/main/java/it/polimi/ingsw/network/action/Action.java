package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public abstract class Action {

    protected String username = null;

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private ActionType type;

    public final void setUsername(String username) {
        if(this.username == null)
            this.username = username;
    }

    public abstract PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException;

    protected boolean checkValid(IGameState gameState) {
        return gameState.getCurrentPlayer().equals(this.username) && gameState.getCurrentPhase().isValid(this.type);
    }
}
