package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ActivateLeaderAction extends Action {

    private int leaderCardIndex;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ActivateLeaderAction() {
        super(ActionType.ACTIVATE_LEADER);
    }

    public ActivateLeaderAction(int leaderCardIndex) {
        super(ActionType.ACTIVATE_LEADER);
        this.leaderCardIndex = leaderCardIndex;
    }

    public ActivateLeaderAction(String username, int leaderCardIndex) {
        super(ActionType.ACTIVATE_LEADER, username);
        this.leaderCardIndex = leaderCardIndex;
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, NotEnoughResourcesException, InvalidLeaderCardException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.username).enterFinalTurnPhase();
        gameState.getGame().getPlayerBoard(super.username).getLeaderCards().get(this.leaderCardIndex)
                .setActive(gameState.getGame().getPlayerBoard(super.username));
        return gameState.getCurrentPhase() == PhaseType.PRODUCING ? PhaseType.FINAL : PhaseType.INITIAL;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.ACTIVATE_LEADER);
    }

}
