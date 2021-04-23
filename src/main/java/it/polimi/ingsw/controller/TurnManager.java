package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import java.io.IOException;

public class TurnManager implements IGameState {
    private String currentPlayer;
    private PhaseType currentPhase;
    private Game game;

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
