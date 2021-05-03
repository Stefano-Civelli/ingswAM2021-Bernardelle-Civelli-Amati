package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.controllerexception.ControllerException;
import it.polimi.ingsw.controller.controllerexception.WrongPlayerException;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.controllerexception.InvalidActionException;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;
import java.util.List;

public class TurnManager implements IGameState {

    public static class TurnState {

        private final String player;
        private final PhaseType phase;

        public TurnState(String player, PhaseType phase) {
            this.player = player;
            this.phase = phase;
        }

        public String getPlayer() {
            return player;
        }

        public PhaseType getPhase() {
            return phase;
        }

    }

    private String currentPlayer = null;
    private PhaseType currentPhase = null;
    private final Game game;

    public TurnManager(Game game, List<String> usernames) throws IOException {
        this.game = game;
        for(String username : usernames)
            this.game.addPlayer(username);
    }

    public List<String> startGame() {
        this.currentPlayer = this.game.startGame();
        this.currentPhase = PhaseType.SETUP_CHOOSERESOURCES;
        return this.game.getOrderedPlayers();
    }

    public synchronized Message handleAction(Action action) {
        try {
            this.currentPhase = action.performAction(this);
        } catch (WrongPlayerException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.WRONG_PLAYER);
        } catch (InvalidActionException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.INVALID_ACTION);
        } catch (ControllerException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.UNKNOWN_CONTROLLER_ERROR);
        } catch (InvalidUsernameException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.INVALID_USERNAME);
        } catch (NotEnoughResourcesException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.NOT_ENOUGH_RESOURCES);
        } catch (InvalidLeaderCardException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.INVALID_LEADERCARD);
        } catch(LeaderIsActiveException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.CANNOT_DISCARD_ACTIVE_LEADER);
        } catch (AlreadyProducedException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.ALREADY_PRODUCED);
        } catch (NegativeQuantityException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.NEGATIVE_QUANTITY);
        } catch (AbuseOfFaithException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.ABUSE_OF_FAITH);
        } catch (NotBuyableException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.NOT_BUYABLE);
        } catch (InvalidCardPlacementException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.INVALID_CARD_PLACEMENT);
        } catch (RowOrColumnNotExistsException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.INVALID_ROW_OR_COLUMN);
        } catch(InvalidDevelopCardException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.INVALID_DEVELOP_CARD);
        }catch (WrongResourceNumberException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.WRONG_RESOURCES_NUMBER);
        } catch (NotEnoughSpaceException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.NOT_ENOUGH_SPACE);
        } catch (MarbleNotExistException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.MARBLE_NOT_EXIST);
        } catch (NeedAResourceToAddException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.NEED_RESOURCE_TO_PRODUCE);
        } catch (NotActivatableException e) {
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.NOT_ACTIVATABLE_PRODUCTION);
        } catch (ModelException e) {
            e.printStackTrace();
            return new Message(this.currentPlayer, MessageType.ERROR,ErrorType.UNKNOWN_MODEL_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(this.currentPlayer, MessageType.ERROR, ErrorType.UNKNOWN_ERROR);
        }

        // If the player turn is ended change the current player
        if(this.currentPhase == PhaseType.END_SETUP) {
            try {
                this.currentPlayer = this.game.nextPlayer(this.currentPlayer);
            } catch (InvalidUsernameException e) {
                // This code should never be executed
                e.printStackTrace();
            }
            this.currentPhase = PhaseType.SETUP_CHOOSERESOURCES;
        }
        if(this.currentPhase == PhaseType.END) {
            try {
                this.currentPlayer = this.game.nextPlayer(this.currentPlayer);
            }catch (InvalidUsernameException e) {
                // This code should never be executed
                e.printStackTrace();
            }
            this.currentPhase = PhaseType.INITIAL;
        }

        // Send new turn state to players
        return new Message(MessageType.NEXT_TURN_STATE, new TurnState(this.currentPlayer, this.currentPhase));
    }

    @Override
    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public PhaseType getCurrentPhase() {
        return this.currentPhase;
    }

    @Override
    public Game getGame() {
        return this.game;
    }

}
