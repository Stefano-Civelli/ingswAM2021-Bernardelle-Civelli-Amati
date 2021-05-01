package it.polimi.ingsw.model;

public interface IGameState {

    String getCurrentPlayer();

    PhaseType getCurrentPhase();

    Game getGame();

}
