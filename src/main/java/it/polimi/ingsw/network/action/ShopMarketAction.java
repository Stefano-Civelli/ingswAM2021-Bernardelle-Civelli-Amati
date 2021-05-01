package it.polimi.ingsw.network.action;

import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ShopMarketAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private boolean inRow; // if true -> row, else -> column

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private int index;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException,
            InvalidUsernameException, RowOrColumnNotExistsException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        if(inRow)
            gameState.getGame().getPlayerBoard(super.username).shopMarketRow(index);
        else
            gameState.getGame().getPlayerBoard(super.username).shopMarketColumn(index);
        return PhaseType.SHOPPING;
    }

}
