package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ProductionAction extends Action {

    private int cardIndex;

    public ProductionAction() {
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
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, NotActivatableException, AlreadyProducedException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).developProduce(this.cardIndex);
        return PhaseType.PRODUCING;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.PRODUCE);
    }

}
