package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.ControllerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public abstract class Action {

    // TODO fare test e java doc action
    // TODO fare get di username e rendere username privato

    @SuppressWarnings({"unused", "FieldCanBeLocal"}) // Because the field is necessary for JSON serialization and it may be accessed using reflection
    private final ActionType type;
    protected String username = null;

    public Action(ActionType type) {
        this.type = type;
    }

    public Action(ActionType type, String username) {
        this(type);
        this.username = username;
    }

    public final void setUsername(String username) {
        if(this.username == null)
            this.username = username;
    }

    public abstract PhaseType performAction(IGameState gameState) throws ControllerException, ModelException;

    protected boolean isCurrentPlayer(IGameState gameState) {
        return !(this.username == null) && gameState.getCurrentPlayer().equals(this.username);
    }

}
