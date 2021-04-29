package it.polimi.ingsw.network.action;

import it.polimi.ingsw.controller.IGameState;
import it.polimi.ingsw.controller.PhaseType;
import it.polimi.ingsw.model.modelexceptions.InvalidLeaderCardException;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;

public class DiscardLeaderAction extends Action {

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
    private int leaderCardIndex;

    @Override
    public PhaseType performAction(IGameState gameState) throws InvalidActionException,
            InvalidUsernameException, InvalidLeaderCardException {
        if(!super.checkValid(gameState))
            throw new InvalidActionException();
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            gameState.getGame().getPlayerBoard(super.username).enterFinalTurnPhase();
        if(gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDLEADER) {
            gameState.getGame().getPlayerBoard(super.username).discardLeaderAtBegin(this.leaderCardIndex);
            if(gameState.getGame().getPlayerBoard(super.username).getLeaderCards().size() > 2)
                return PhaseType.SETUP_DISCARDLEADER;
            else
                return PhaseType.END_SETUP;
        }
        gameState.getGame().getPlayerBoard(super.username).discardLeader(this.leaderCardIndex);
        if(gameState.getCurrentPhase() == PhaseType.PRODUCING)
            return PhaseType.FINAL;
        return PhaseType.INITIAL;
    }

}
