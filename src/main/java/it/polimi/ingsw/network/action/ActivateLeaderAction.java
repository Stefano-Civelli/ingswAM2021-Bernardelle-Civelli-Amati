package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class ActivateLeaderAction extends Action {

    @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
    private int leaderCardIndex;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException,
            InvalidUsernameException, NotEnoughResourcesException, InvalidLeaderCardException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.username).enterFinalTurnPhase();
        gameState.getGame().getPlayerBoard(super.username).getLeaderCards().get(this.leaderCardIndex)
                .setActive(gameState.getGame().getPlayerBoard(super.username));
        return gameState.getCurrentPhase() == PhaseType.PRODUCING ? PhaseType.FINAL : PhaseType.INITIAL;
    }

}
