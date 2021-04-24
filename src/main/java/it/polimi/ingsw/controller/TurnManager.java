package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.ModelException;
import it.polimi.ingsw.network.Action.Action;
import it.polimi.ingsw.network.Action.InvalidActionException;
import it.polimi.ingsw.network.messages.Message;

import java.io.File;
import java.io.IOException;

public class TurnManager implements IGameState {
    private String currentPlayer = null;
    private PhaseType currentPhase = null;
    private final Game game;

    public TurnManager() throws IOException {
        this.game = new Game(new File("src/DevelopCardConfig.json"), new File("src/LeaderCardConfig.json"));
    }

    public void addPlayer(String userName) throws IOException, InvalidUsernameException {
        this.game.addPlayer(userName);
    }

    public void startGame() {
        this.currentPlayer = this.game.startGame();
        this.currentPhase = PhaseType.SETUP_CHOOSERESOURCES;
    }

    public synchronized Message handleAction(Action action) throws InvalidUsernameException {
        //TODO
        // sostituire i new Message con i messaggi corretti e spezzare la ModelException nelle varie eccezioni possibili
        try {
            this.currentPhase = action.performAction(this);
        } catch (InvalidActionException e) {
            return new Message();
        } catch (ModelException e) {
            return new Message();
        }
        if(this.currentPhase == PhaseType.END_SETUP) {
            this.currentPlayer = this.game.nextPlayer(this.currentPlayer);
            this.currentPhase = PhaseType.SETUP_CHOOSERESOURCES;
        }
        if(this.currentPhase == PhaseType.END) {
            this.currentPlayer = this.game.nextPlayer(this.currentPlayer);
            this.currentPhase = PhaseType.INITIAL;
        }
        return new Message();
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
