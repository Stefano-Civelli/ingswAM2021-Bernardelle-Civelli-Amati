package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * An action to request to the model to activate the production on the develop top card of a specific card slot
 */
public class ProductionAction extends Action {

    private Integer cardIndex = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ProductionAction() {
        super(ActionType.PRODUCE);
    }

    public ProductionAction(int cardIndex) {
        super(ActionType.PRODUCE);
        this.cardIndex = cardIndex;
    }

    public ProductionAction(String username, int cardIndex) {
        super(ActionType.PRODUCE, username);
        this.cardIndex = cardIndex;
    }

    /**
     * Perform the production of the specified develop card of the player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws NotActivatableException the specified develop card can't be activated now
     * @throws AlreadyProducedException the production of this develop card has already been performed in this turn
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, NotActivatableException, AlreadyProducedException, InvalidCardSlotException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        gameState.getGame().getPlayerBoard(super.getUsername()).developProduce(this.cardIndex);
        return PhaseType.PRODUCING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.PRODUCE);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.cardIndex != null;
    }

}
