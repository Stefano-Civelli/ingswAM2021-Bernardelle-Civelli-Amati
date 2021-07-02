package it.polimi.ingsw.model;

/**
 * It's used together with {@link it.polimi.ingsw.model.EndGameObservable EndGameObservable} to notify of the end of the game
 */
public interface EndGameObserver {

  void update(boolean onySinglePlayer);

}
