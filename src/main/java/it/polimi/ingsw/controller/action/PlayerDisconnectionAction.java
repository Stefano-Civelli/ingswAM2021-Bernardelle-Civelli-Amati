package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.model.IGameState;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * An action to notify the model that a player is no longer connected with the server
 */
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
                > gameState.getGame().getOrderedPlayers().indexOf(gameState.getCurrentPlayer()) // if player id before current player in turn order
            || gameState.getGame().getOrderedPlayers().indexOf(super.getUsername())
                == gameState.getGame().getOrderedPlayers().indexOf(gameState.getCurrentPlayer())
            && gameState.getCurrentPhase() == PhaseType.SETUP_CHOOSING_RESOURCES // or if the player is the current player and the current phase is setup SETUP_CHOOSING_RESOURCES
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
        } else
            if( !(gameState.getGame().getOrderedPlayers().indexOf(super.getUsername())
                    == gameState.getGame().getOrderedPlayers().indexOf(gameState.getCurrentPlayer())
                    && gameState.getCurrentPhase() == PhaseType.SETUP_DISCARDING_LEADERS) // if it isn't that the player is the current player and the current phase is SETUP_DISCARDING_LEADERS
            )
                return;

        // Discard two random leader cards
        Random random = new Random();
        try {
            int leaderID1, leaderID2;
            leaderID1 = gameState.getGame().getPlayerBoard(super.getUsername()).getLeaderCards().stream()
                    .map(LeaderCard::getLeaderId).collect(Collectors.toList())
                    .get(random.nextInt(4));
            do {
                leaderID2 = gameState.getGame().getPlayerBoard(super.getUsername()).getLeaderCards().stream()
                        .map(LeaderCard::getLeaderId).collect(Collectors.toList()).get(random.nextInt(4));
            } while (leaderID2 == leaderID1);
            gameState.getGame().getPlayerBoard(super.getUsername()).discardLeaderAtBegin(leaderID1, leaderID2);
        } catch (InvalidLeaderCardException e) {
            // This code should never be executed
            e.printStackTrace();
        }

    }

}
