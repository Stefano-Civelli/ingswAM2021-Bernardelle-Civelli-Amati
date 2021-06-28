package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.controller.controllerexception.NotAllowedActionException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.modelexceptions.*;

/**
 * An action used to specify which leader card must be used to convert a white marble
 */
public class ChooseLeaderOnWhiteMarbleAction extends Action {

    private Integer leaderIndex = null;

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private ChooseLeaderOnWhiteMarbleAction() {
        super(ActionType.CHOOSE_WHITE_LEADER);
    }

    public ChooseLeaderOnWhiteMarbleAction(int leaderIndex) {
        super(ActionType.CHOOSE_WHITE_LEADER);
        this.leaderIndex = leaderIndex;
    }

    public ChooseLeaderOnWhiteMarbleAction(String username, int leaderIndex) {
        super(ActionType.CHOOSE_WHITE_LEADER, username);
        this.leaderIndex = leaderIndex;
    }

    /**
     * Add a white marble of the specified player using the specified leader card to convert it.
     *
     * @param gameState the current state of this game
     * @return the next phase of this player's turn
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws NotAllowedActionException this Action can't be performed in this phase of turn or game
     * @throws WrongPlayerException the player for which this action must be performed isn't the current player
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     * @throws InvalidLeaderCardException the specified leader card doesn't exist
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, NotAllowedActionException, WrongPlayerException,
            InvalidUsernameException, InvalidLeaderCardException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(!super.isCurrentPlayer(gameState))
            throw new WrongPlayerException();
        if(!this.isActionAllowed(gameState))
            throw new NotAllowedActionException();
        try {
            gameState.getGame().getPlayerBoard(super.getUsername()).addWhiteToWarehouse(this.leaderIndex);
        } catch (NotEnoughSpaceException | MarbleNotExistException ignored) {}
        return gameState.getGame().getPlayerBoard(super.getUsername()).areMarblesFinished()
                ? PhaseType.FINAL : PhaseType.SHOPPING;
    }

    private boolean isActionAllowed(IGameState gameState) {
        return gameState.getCurrentPhase().isValid(ActionType.CHOOSE_WHITE_LEADER);
    }

    private boolean isActionValid() {
        return super.getUsername() != null && this.leaderIndex != null;
    }

}
