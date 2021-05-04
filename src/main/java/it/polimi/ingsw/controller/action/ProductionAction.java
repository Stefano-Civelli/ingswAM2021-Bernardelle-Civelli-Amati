package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

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

    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, NotActivatableException, AlreadyProducedException {
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
