package it.polimi.ingsw.network.Action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public abstract class Action {

    protected String userName = null;

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private ActionType type;

    public final void setUsername(String userName) {
        if(this.userName == null)
            this.userName = userName;
    }

    public abstract PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException;

    protected boolean checkValid(IGameState gameState) {
        return gameState.getCurrentPlayer().equals(this.userName) && gameState.getCurrentPhase().isValid(this.type);
    }

}
