package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * An action to request to the model to discard a specific leader card of a player
 */
public class DiscardLeaderAction extends Action {

    private Integer leaderCardID = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private DiscardLeaderAction() {
        super(ActionType.DISCARD_LEADER);
    }

    public DiscardLeaderAction(int leaderCardID) {
        super(ActionType.DISCARD_LEADER);
        this.leaderCardID = leaderCardID;
    }

    public DiscardLeaderAction(String username, int leaderCardID) {
        super(ActionType.DISCARD_LEADER, username);
        this.leaderCardID = leaderCardID;
    }

    /**
     * Discard the specified leader card for the player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws InvalidLeaderCardException the specified leader card doesn't exist
     * @throws LeaderIsActiveException the specified leader is already active
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, InvalidLeaderCardException, LeaderIsActiveException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        gameState.getGame().getPlayerBoard(super.getUsername()).enterFinalTurnPhase();
        gameState.getGame().getPlayerBoard(super.getUsername()).discardLeader(this.leaderCardID);
        if(gameState.getCurrentPhase() == PhaseType.INITIAL)
            return PhaseType.INITIAL;
        return PhaseType.FINAL;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.DISCARD_LEADER);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.leaderCardID != null;
    }

}
