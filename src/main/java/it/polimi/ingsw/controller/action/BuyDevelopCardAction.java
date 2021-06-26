package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * An action to request to the model to buy a specific develop card for a specific player
 */
public class BuyDevelopCardAction extends Action {

    private Integer row = null,
            column = null,
            cardSlotIndex = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private BuyDevelopCardAction() {
        super(ActionType.BUY_CARD);
    }

    public BuyDevelopCardAction(int row, int column, int cardSlotIndex) {
        super(ActionType.BUY_CARD);
        this.row = row;
        this.column = column;
        this.cardSlotIndex = cardSlotIndex;
    }

    public BuyDevelopCardAction(String username, int row, int column, int cardSlotIndex) {
        super(ActionType.BUY_CARD, username);
        this.row = row;
        this.column = column;
        this.cardSlotIndex = cardSlotIndex;
    }

    /**
     * Buy the specified develop card for the specified player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws NotBuyableException the player can't buy the specified develop card
     * @throws InvalidCardPlacementException the specified develop card can't be placed in the specified slot
     * @throws InvalidDevelopCardException the specified develop card doesn't exist
     */ // FIXME se lo slot non esiste? IndexOutOfBound????
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, NotBuyableException, InvalidCardPlacementException,
            InvalidDevelopCardException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        gameState.getGame().getPlayerBoard(super.getUsername()).addDevelopCard(row, column, cardSlotIndex);
        return PhaseType.FINAL;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.BUY_CARD);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.row != null && this.column != null && this.cardSlotIndex != null;
    }

}
