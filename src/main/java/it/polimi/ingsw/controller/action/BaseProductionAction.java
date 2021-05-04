package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

public class BaseProductionAction extends Action {

    private ResourceType resource1 = null,
            resource2 = null,
            product = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private BaseProductionAction() {
        super(ActionType.BASE_PRODUCE);
    }

    public BaseProductionAction(ResourceType resource1, ResourceType resource2, ResourceType product) {
        super(ActionType.BASE_PRODUCE);
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.product = product;
    }

    public BaseProductionAction(String username, ResourceType resource1, ResourceType resource2, ResourceType product) {
        super(ActionType.BASE_PRODUCE, username);
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.product = product;
    }

    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, AlreadyProducedException, NegativeQuantityException,
            NotEnoughResourcesException, AbuseOfFaithException, NeedAResourceToAddException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        gameState.getGame().getPlayerBoard(super.getUsername()).baseProduction(this.resource1, this.resource2, this.product);
        return PhaseType.PRODUCING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.BASE_PRODUCE);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.resource1 != null && this.resource2 != null && this.product != null;
    }

}
