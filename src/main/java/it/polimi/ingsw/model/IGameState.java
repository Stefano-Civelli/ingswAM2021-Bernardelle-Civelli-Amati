package it.polimi.ingsw.model;

/**
 * Represents the current state of a game
 */
public interface IGameState {

    String getCurrentPlayer();

    PhaseType getCurrentPhase();

    Game getGame();

}
