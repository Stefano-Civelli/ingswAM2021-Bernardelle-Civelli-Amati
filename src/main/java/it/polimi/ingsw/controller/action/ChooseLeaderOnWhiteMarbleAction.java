package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ChooseLeaderOnWhiteMarbleAction extends Action {

    private int leaderIndex;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ChooseLeaderOnWhiteMarbleAction() {
        super(ActionType.CHOOSE_WHITE_LEADER);
    }

    public ChooseLeaderOnWhiteMarbleAction(int leaderIndex) {
        super(ActionType.CHOOSE_WHITE_LEADER);
        this.leaderIndex = leaderIndex;
    }

    public ChooseLeaderOnWhiteMarbleAction(String username, int leaderIndex) {
        super(ActionType.CHOOSE_WHITE_LEADER, username);
        this.leaderIndex = leaderIndex;
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, InvalidLeaderCardException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        try {
            gameState.getGame().getPlayerBoard(super.username).addWhiteToWarehouse(this.leaderIndex);
        } catch (NotEnoughSpaceException ignored) {}
        return gameState.getGame().getPlayerBoard(super.username).areMarblesFinished()
                ? PhaseType.FINAL : PhaseType.SHOPPING;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.CHOOSE_WHITE_LEADER);
    }

}
