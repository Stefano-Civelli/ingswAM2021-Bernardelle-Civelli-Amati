package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.ModelException;

public class ActivateLeaderAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private int leaderCard;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.userName).enterFinalTurnPhase();
        gameState.getGame().getPlayerBoard(super.userName).getLeaderCards().get(this.leaderCard)
                .setActive(gameState.getGame().getPlayerBoard(super.userName));
        return gameState.getCurrentPhase() == PhaseType.PRODUCING ? PhaseType.FINAL : PhaseType.INITIAL;
    }

}
