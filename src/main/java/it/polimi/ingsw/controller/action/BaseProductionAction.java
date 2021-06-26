package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * An action to request to the model to activate the base player board production of a specific player
 */
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

    /**
     * Perform the player board's base production of the specified player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws AlreadyProducedException the base production has already been performed in this turn
     * @throws NotEnoughResourcesException the player doesn't have the specified resources to consume
     * @throws AbuseOfFaithException faith can't be consumed or produced in a base production
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, AlreadyProducedException, NotEnoughResourcesException, AbuseOfFaithException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        try {
            gameState.getGame().getPlayerBoard(super.getUsername()).baseProduction(this.resource1, this.resource2, this.product);
        } catch (NeedAResourceToAddException e) {
            // This code should never be executed
            // I have already checked that all the fields are not null
            e.printStackTrace();
        }
        return PhaseType.PRODUCING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.BASE_PRODUCE);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.resource1 != null && this.resource2 != null && this.product != null;
    }

}
