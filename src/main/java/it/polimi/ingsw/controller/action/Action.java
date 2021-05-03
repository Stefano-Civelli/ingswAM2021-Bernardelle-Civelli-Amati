package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.ControllerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public abstract class Action {

    // TODO fare test e java doc action
    // TODO costruttori action per costruirle nel client

    protected String username = null;

    public final void setUsername(String username) {
        if(this.username == null)
            this.username = username;
    }

    public abstract PhaseType performAction(IGameState gameState) throws ControllerException, ModelException;

    protected boolean isCurrentPlayer(IGameState gameState) {
        return !(this.username == null) && gameState.getCurrentPlayer().equals(this.username);
    }

}
