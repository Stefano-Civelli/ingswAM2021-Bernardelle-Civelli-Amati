package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class DiscardLeaderAction extends Action {

    private Integer leaderCardIndex = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private DiscardLeaderAction() {
        super(ActionType.DISCARD_LEADER);
    }

    public DiscardLeaderAction(int leaderCardIndex) {
        super(ActionType.DISCARD_LEADER);
        this.leaderCardIndex = leaderCardIndex;
    }

    public DiscardLeaderAction(String username, int leaderCardIndex) {
        super(ActionType.DISCARD_LEADER, username);
        this.leaderCardIndex = leaderCardIndex;
    }

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
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.getUsername()).enterFinalTurnPhase();
        if(gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER) {
            gameState.getGame().getPlayerBoard(super.getUsername()).discardLeaderAtBegin(this.leaderCardIndex);
            if(gameState.getGame().getPlayerBoard(super.getUsername()).getLeaderCards().size() > 2)
                return PhaseType.SETUP_DISCARDLEADER;
            else
                return PhaseType.END_SETUP;
        }
        gameState.getGame().getPlayerBoard(super.getUsername()).discardLeader(this.leaderCardIndex);
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            return PhaseType.FINAL;
        return PhaseType.INITIAL;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.DISCARD_LEADER);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.leaderCardIndex != null;
    }

}
