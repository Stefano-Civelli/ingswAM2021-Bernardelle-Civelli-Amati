package it.polimi.ingsw.network.Action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.ModelException;

public class DiscardLeaderAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private int leaderCardIndex;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException, ModelException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.userName).enterFinalTurnPhase();
        gameState.getGame().getPlayerBoard(super.userName).discardLeader(this.leaderCardIndex);
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            return PhaseType.FINAL;
        if(gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER) {
            if(gameState.getGame().getPlayerBoard(super.userName).getLeaderCards().size() > 2)
                return PhaseType.SETUP_DISCARDLEADER;
            else
                return PhaseType.END_SETUP;
        }
        return PhaseType.INITIAL;
    }

}
