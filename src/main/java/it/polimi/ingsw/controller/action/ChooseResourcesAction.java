package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.HashMap;
import java.util.Map;

public class ChooseResourcesAction extends Action {

    private Map<ResourceType, Integer> resources;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ChooseResourcesAction() {
        super(ActionType.CHOOSE_WHITE_LEADER);
    }

    public ChooseResourcesAction(Map<ResourceType, Integer> resources) {
        super(ActionType.CHOOSE_WHITE_LEADER);
        this.resources = new HashMap<>(resources);
    }

    public ChooseResourcesAction(String username, Map<ResourceType, Integer> resources) {
        super(ActionType.CHOOSE_WHITE_LEADER, username);
        this.resources = resources;
    }

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, NegativeQuantityException, WrongResourceNumberException,
            AbuseOfFaithException, NotEnoughSpaceException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        if(this.resources.values().stream().anyMatch(i -> i < 0))
            throw new NegativeQuantityException();
        if(gameState.getGame().initialResources(super.username)
                != this.resources.values().stream().reduce(0, Integer::sum))
            throw new WrongResourceNumberException(
                    gameState.getGame().initialResources(super.username),
                    this.resources.values().stream().reduce(0, Integer::sum));
        for(ResourceType resource: this.resources.keySet())
            for(int i = 0; i < this.resources.get(resource); i++)
                gameState.getGame().getPlayerBoard(super.username).getWarehouse().addResource(resource);
        return PhaseType.SETUP_DISCARDLEADER;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.CHOSE_RESOURCES);
    }

}
