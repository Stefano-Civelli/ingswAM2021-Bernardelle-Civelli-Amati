package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

public class LeaderProductionAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private int leaderCardIndex;

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private ResourceType product;

    public LeaderProductionAction() {
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
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, WrongPlayerException,
            InvalidUsernameException, NeedAResourceToAddException, AlreadyProducedException,
            NotEnoughResourcesException, AbuseOfFaithException {
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionValid(gameState))
            throw new InvalidActionException();
        gameState.getGame().getPlayerBoard(super.username).leaderProduce(this.leaderCardIndex, this.product);
        return PhaseType.PRODUCING;
    }

    private boolean isActionValid(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.LEADER_PRODUCE);
    }

}
