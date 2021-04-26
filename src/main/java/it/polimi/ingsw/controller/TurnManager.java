package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.ModelException;
import it.polimi.ingsw.network.action.Action;
import it.polimi.ingsw.network.action.InvalidActionException;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;
import java.util.List;

public class TurnManager implements IGameState {

    private static class GameState {

        private final String player;
        private final PhaseType phase;

        public GameState(String player, PhaseType phase) {
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
        //TODO
        // sostituire i new Message con i messaggi corretti e spezzare la ModelException nelle varie eccezioni possibili
        try {
            this.currentPhase = action.performAction(this);
        } catch (InvalidActionException e) {
            return new Message(MessageType.ERROR,"Error");
        } catch (ModelException e) {
            return new Message(MessageType.ERROR,"Error");
        }
        if(this.currentPhase == PhaseType.END_SETUP) {
            try {
                this.currentPlayer = this.game.nextPlayer(this.currentPlayer);
            } catch (InvalidUsernameException e) {
                e.printStackTrace();
            }
            this.currentPhase = PhaseType.SETUP_CHOOSERESOURCES;
        }
        if(this.currentPhase == PhaseType.END) {
            try {
                this.currentPlayer = this.game.nextPlayer(this.currentPlayer);
            }catch (InvalidUsernameException e) {
                e.printStackTrace();
            }
            this.currentPhase = PhaseType.INITIAL;
        }
        return new Message(MessageType.NEXT_STATE, new GameState(this.currentPlayer, this.currentPhase));
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
