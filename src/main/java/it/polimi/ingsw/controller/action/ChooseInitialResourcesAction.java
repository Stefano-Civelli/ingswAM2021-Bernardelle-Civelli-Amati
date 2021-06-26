package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * An action to specify the initial resources of a player
 */
public class ChooseInitialResourcesAction extends Action {

    // TODO controllare che funziona se arriva vuota per il primo player

    private Map<ResourceType, Integer> resources = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ChooseInitialResourcesAction() {
        super(ActionType.SETUP_CHOOSE_RESOURCES);
    }

    public ChooseInitialResourcesAction(Map<ResourceType, Integer> resources) {
        super(ActionType.SETUP_CHOOSE_RESOURCES);
        this.resources = new HashMap<>(resources);
    }

    public ChooseInitialResourcesAction(String username, Map<ResourceType, Integer> resources) {
        super(ActionType.SETUP_CHOOSE_RESOURCES, username);
        this.resources = resources;
    }

    /**
     * Assign the specified initial resources to the player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws NegativeQuantityException at least one quantity of the specified resources is negative
     * @throws WrongResourceNumberException the number of the specified resources and the number of resources that belong to the player are different
     * @throws AbuseOfFaithException one of the specified resources is faith
     * @throws NotEnoughSpaceException the player has not enough space to contain these resources
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, NegativeQuantityException, WrongResourceNumberException,
            AbuseOfFaithException, NotEnoughSpaceException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        if(this.resources.values().stream().anyMatch(i -> i < 0))
            throw new NegativeQuantityException();
        if(gameState.getGame().initialResources(super.getUsername())
                != this.resources.values().stream().reduce(0, Integer::sum))
            throw new WrongResourceNumberException(
                    gameState.getGame().initialResources(super.getUsername()),
                    this.resources.values().stream().reduce(0, Integer::sum));
        for(ResourceType resource: this.resources.keySet())
            for(int i = 0; i < this.resources.get(resource); i++)
                gameState.getGame().getPlayerBoard(super.getUsername()).getWarehouse().addResource(resource);
        return PhaseType.SETUP_DISCARDING_LEADERS;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.SETUP_CHOOSE_RESOURCES);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.resources != null;
    }

}
