package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

public class InsertMarbleAction extends Action {

    private Integer marbleIndex = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private InsertMarbleAction() {
        super(ActionType.INSERT_MARBLE);
    }

    public InsertMarbleAction(int marbleIndex) {
        super(ActionType.INSERT_MARBLE);
        this.marbleIndex = marbleIndex;
    }

    public InsertMarbleAction(String username, int marbleIndex) {
        super(ActionType.INSERT_MARBLE, username);
        this.marbleIndex = marbleIndex;
    }

    /**
     * Insert the specified marble of the player.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws MarbleNotExistException the specified marble doesn't exist
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, MarbleNotExistException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        try {
            gameState.getGame().getPlayerBoard(super.getUsername()).addMarbleToWarehouse(this.marbleIndex);
        } catch (MoreWhiteLeaderCardsException e) {
            return PhaseType.SHOPPING_LEADER;
        } catch (NotEnoughSpaceException ignored) {}
        return gameState.getGame().getPlayerBoard(super.getUsername()).areMarblesFinished()
                ? PhaseType.FINAL : PhaseType.SHOPPING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.INSERT_MARBLE);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.marbleIndex != null;
    }

}
