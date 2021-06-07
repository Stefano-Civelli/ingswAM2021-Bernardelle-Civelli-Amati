package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.List;
import java.util.Random;

public class PlayerDisconnectionAction extends Action {

    @SuppressWarnings("unused") // It may be called using reflection during JSON deserialization
    private PlayerDisconnectionAction() {
        super(ActionType.PLAYER_DISCONNECTION);
    }

    public PlayerDisconnectionAction(String username) {
        super(ActionType.PLAYER_DISCONNECTION, username);
    }

    /**
     * Disconnect a Player.
     * If the player is in the initial setup phase: initial resources and leader to discard are chosen randomly.
     * if the player must adds marbles they are added in order, if possible, and discarded otherwise.
     *
     * @param gameState the current state of this game
     * @return the next turn phase if the player was the current player, null otherwise
     * @throws InvalidActionException this Action is not correctly initialized
     * @throws InvalidUsernameException the player for which this action must be performed doesn't exist in this game
     */
    @Override
    public PhaseType performAction(IGameState gameState)
            throws InvalidActionException, InvalidUsernameException, NoConnectedPlayerException {
        if(!this.isActionValid())
            throw new InvalidActionException("This Action is not correctly initialized.");
        if(gameState.getCurrentPhase().isSetup())
            this.setupDisconnection(gameState);
        gameState.getGame().getPlayerBoard(super.getUsername()).enterFinalTurnPhase();
        gameState.getGame().disconnectPlayer(super.getUsername());
        return super.isCurrentPlayer(gameState)
                ? ( gameState.getCurrentPhase().isSetup() ? PhaseType.END_SETUP : PhaseType.END )
                : null;
    }

    private boolean isActionValid() {
        return super.getUsername() != null;
    }

    private void setupDisconnection(IGameState gameState) throws InvalidUsernameException {
        if(gameState.getGame().getOrderedPlayers().indexOf(super.getUsername())
                > gameState.getGame().getOrderedPlayers().indexOf(gameState.getCurrentPlayer())
            || gameState.getGame().getOrderedPlayers().indexOf(super.getUsername())
                == gameState.getGame().getOrderedPlayers().indexOf(gameState.getCurrentPlayer())
            && gameState.getCurrentPhase() == PhaseType.SETUP_CHOOSING_RESOURCES
        ) {
            // Player must choose initial resources
            ResourceType[] possibleResources = List.of(ResourceType.values()).stream().
                    filter(resource -> resource != ResourceType.FAITH).toArray(ResourceType[]::new);
            Random random = new Random();
            for(int i = 0; i < gameState.getGame().initialResources(super.getUsername()); i++) {
                ResourceType resource = possibleResources[random.nextInt(possibleResources.length)];
                try {
                    gameState.getGame().getPlayerBoard(super.getUsername()).getWarehouse().addResource(resource);
                } catch (AbuseOfFaithException | NotEnoughSpaceException e) {
                    // This code should never be executed
                    e.printStackTrace();
                }
            }
        }

        // Discard two leader cards
        Random random = new Random();
        try {
            gameState.getGame().getPlayerBoard(super.getUsername()).discardLeaderAtBegin(
                    random.nextInt(4), random.nextInt(3));
        } catch (InvalidLeaderCardException e) {
            // This code should never be executed
            e.printStackTrace();
        }

    }

}
