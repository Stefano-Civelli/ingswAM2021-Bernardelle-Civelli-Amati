package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class InsertMarbleAction extends Action {

    private int marbleIndex;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private InsertMarbleAction() {
        super(ActionType.INSERT_MARBLE);
    }

    public InsertMarbleAction(int marbleIndex) {
        super(ActionType.INSERT_MARBLE);
        this.marbleIndex = marbleIndex;
    }

    public InsertMarbleAction(String username, int marbleIndex) {
        super(ActionType.INSERT_MARBLE, username);
        this.marbleIndex = marbleIndex;
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, MarbleNotExistException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        try {
            gameState.getGame().getPlayerBoard(super.getUsername()).addMarbleToWarehouse(this.marbleIndex);
        } catch (MoreWhiteLeaderCardsException e) {
            return PhaseType.SHOPPING_LEADER;
        } catch (NotEnoughSpaceException ignored) {}
        return gameState.getGame().getPlayerBoard(super.getUsername()).areMarblesFinished()
                ? PhaseType.FINAL : PhaseType.SHOPPING;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.INSERT_MARBLE);
    }

}
