package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.ModelException;
import it.polimi.ingsw.model.modelexceptions.RowOrColumnNotExistsException;

public class ShopMarketAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private boolean inRow; // if true -> row, else -> column

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
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
