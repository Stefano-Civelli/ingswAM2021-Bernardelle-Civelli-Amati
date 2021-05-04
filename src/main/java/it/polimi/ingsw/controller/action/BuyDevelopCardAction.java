package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class BuyDevelopCardAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because fields' value is assigned using reflection
    private int row,
            column,
            cardSlotIndex;

    public BuyDevelopCardAction() {}

    public BuyDevelopCardAction(int row, int column, int cardSlotIndex) {
        this.row = row;
        this.column = column;
        this.cardSlotIndex = cardSlotIndex;
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, NotBuyableException, InvalidCardPlacementException,
            InvalidDevelopCardException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).addDevelopCard(row, column, cardSlotIndex);
        return PhaseType.FINAL;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.BUY_CARD);
    }

}
