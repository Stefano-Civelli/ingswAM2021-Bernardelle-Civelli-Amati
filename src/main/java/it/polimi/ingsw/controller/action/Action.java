package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.ControllerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public abstract class Action {

    // TODO fare test action

    @SuppressWarnings({"unused", "FieldCanBeLocal"}) // Because the field is necessary for JSON serialization and it may be accessed using reflection
    private final ActionType type;
    private String username = null;

    protected Action(ActionType type) {
        this.type = type;
    }

    protected Action(ActionType type, String username) {
        this(type);
        this.username = username;
    }

    /**
     * Set the player for which this action must be performed.
     * The player will be set only the first time.
     *
     * @param username the player to set
     * @return the player that owns this action, can be the player just set or another if it was already set.
     */
    public final String setUsername(String username) {
        if(this.username == null)
            this.username = username;
        return this.username;
    }

    /**
     *
     * @return the player for which this action must be performed
     */
    protected final String getUsername() {
        return this.username;
    }

    /**
     * Check if the player for which this action must be performed is the current player.
     *
     * @param gameState the current state of this game
     * @return true if this player is the current player, false if it's not
     */
    protected final boolean isCurrentPlayer(IGameState gameState) {
        return !(this.username == null) && gameState.getCurrentPlayer().equals(this.username);
    }

    /**
     * (See concrete subclasses for more specific documentation)
     * Perform the action.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws ControllerException a controller error
     * @throws ModelException a model error
     */
    public abstract PhaseType performAction(IGameState gameState) throws ControllerException, ModelException;

}
