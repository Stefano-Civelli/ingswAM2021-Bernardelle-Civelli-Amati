package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ShopMarketAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private boolean inRow; // if true -> row, else -> column

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private int index;

    public ShopMarketAction() {
        super(ActionType.SHOP_MARKET);
    }

    public ShopMarketAction(boolean inRow, int index) {
        super(ActionType.SHOP_MARKET);
        this.inRow = inRow;
        this.index = index;
    }

    public ShopMarketAction(String username, boolean inRow, int index) {
        super(ActionType.SHOP_MARKET, username);
        this.inRow = inRow;
        this.index = index;
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, RowOrColumnNotExistsException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        if(inRow)
            gameState.getGame().getPlayerBoard(super.username).shopMarketRow(index);
        else
            gameState.getGame().getPlayerBoard(super.username).shopMarketColumn(index);
        return PhaseType.SHOPPING;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.SHOP_MARKET);
    }

}
