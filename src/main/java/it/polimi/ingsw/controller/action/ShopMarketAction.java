package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ShopMarketAction extends Action {

    private Boolean inRow = null; // if true -> row, else -> column
    private Integer index = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ShopMarketAction() {
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

    /**
     * Buy the specified row or column of the market for the player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws RowOrColumnNotExistsException the specified row or column doesn't exist
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, RowOrColumnNotExistsException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        if(inRow)
            gameState.getGame().getPlayerBoard(super.getUsername()).shopMarketRow(index);
        else
            gameState.getGame().getPlayerBoard(super.getUsername()).shopMarketColumn(index);
        return PhaseType.SHOPPING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.SHOP_MARKET);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.inRow != null && this.index != null;
    }

}
