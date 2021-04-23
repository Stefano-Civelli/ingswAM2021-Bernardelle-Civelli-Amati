package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public interface IGameState {

    String getCurrentPlayer();

    PhaseType getCurrentPhase();

    Game getGame();

}
