package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ActivateLeaderAction extends Action {

    private Integer leaderCardID = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ActivateLeaderAction() {
        super(ActionType.ACTIVATE_LEADER);
    }

    public ActivateLeaderAction(int leaderCardID) {
        super(ActionType.ACTIVATE_LEADER);
        this.leaderCardID = leaderCardID;
    }

    public ActivateLeaderAction(String username, int leaderCardID) {
        super(ActionType.ACTIVATE_LEADER, username);
        this.leaderCardID = leaderCardID;
    }

    /**
     * Activate the specified leader card of the specified player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws NotEnoughResourcesException the player doesn't have the requirements to activate this leader card
     * @throws InvalidLeaderCardException the specified leader card doesn't exist
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, NotEnoughResourcesException, InvalidLeaderCardException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.getUsername()).enterFinalTurnPhase();

        gameState.getGame().getPlayerBoard(super.getUsername()).setActiveLeadercard(this.leaderCardID);

        return gameState.getCurrentPhase() == PhaseType.INITIAL ? PhaseType.INITIAL : PhaseType.FINAL;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.ACTIVATE_LEADER);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.leaderCardID != null;
    }

}
