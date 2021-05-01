package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class InsertMarbleAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private int marbleIndex;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException,
            InvalidUsernameException, MarbleNotExistException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        try {
            gameState.getGame().getPlayerBoard(super.username).addMarbleToWarehouse(this.marbleIndex);
        } catch (MoreWhiteLeaderCardsException e) {
            return PhaseType.SHOPPING_LEADER;
        } catch (NotEnoughSpaceException ignored) {}
        return gameState.getGame().getPlayerBoard(super.username).areMarblesFinished()
                ? PhaseType.FINAL : PhaseType.SHOPPING;
    }

}
