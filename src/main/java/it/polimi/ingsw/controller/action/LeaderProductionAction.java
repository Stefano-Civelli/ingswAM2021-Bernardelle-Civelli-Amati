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

    /**
     * Perform the production of the specified leader card of the player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws AlreadyProducedException the production of this leader card has already been performed in this turn
     * @throws NotEnoughResourcesException the player doesn't have the specified resources to consume
     * @throws AbuseOfFaithException the produced resource is faith
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, AlreadyProducedException,
            NotEnoughResourcesException, AbuseOfFaithException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        try {
            gameState.getGame().getPlayerBoard(super.getUsername()).leaderProduce(this.leaderCardIndex, this.product);
        } catch (NeedAResourceToAddException e) {
            // This code should never be executed
            // I have already checked that all the fields are not null
            e.printStackTrace();
        }
        return PhaseType.PRODUCING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.LEADER_PRODUCE);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.leaderCardIndex != null&& this.product != null;
    }

}
