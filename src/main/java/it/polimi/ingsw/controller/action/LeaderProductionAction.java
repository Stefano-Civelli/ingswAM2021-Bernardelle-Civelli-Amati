package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

public class LeaderProductionAction extends Action {

    private Integer leaderCardIndex = null;
    private ResourceType product = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private LeaderProductionAction() {
        super(ActionType.LEADER_PRODUCE);
    }

    public LeaderProductionAction(int leaderCardIndex, ResourceType product) {
        super(ActionType.LEADER_PRODUCE);
        this.leaderCardIndex = leaderCardIndex;
        this.product = product;
    }

    public LeaderProductionAction(String username, int leaderCardIndex, ResourceType product) {
        super(ActionType.LEADER_PRODUCE, username);
        this.leaderCardIndex = leaderCardIndex;
        this.product = product;
    }

    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, NeedAResourceToAddException, AlreadyProducedException,
            NotEnoughResourcesException, AbuseOfFaithException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        gameState.getGame().getPlayerBoard(super.getUsername()).leaderProduce(this.leaderCardIndex, this.product);
        return PhaseType.PRODUCING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.LEADER_PRODUCE);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.leaderCardIndex != null&& this.product != null;
    }

}
