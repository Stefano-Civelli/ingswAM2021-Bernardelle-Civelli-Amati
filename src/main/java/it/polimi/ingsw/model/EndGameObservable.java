package it.polimi.ingsw.model;

/**
 * It's used together with {@link it.polimi.ingsw.model.EndGameObserver EndGameObserver} to notify of the end of the game
 */
public interface EndGameObservable {
  /**
   * add an observer to a list of observers
   * @param observerToAdd the observer to add
   */
  void addToEndGameObserverList(EndGameObserver observerToAdd);

  /**
   * remove an observer from a list of observers
   * @param observerToRemove the observer to remove
   */
  void removeFromEndGameObserverList(EndGameObserver observerToRemove);

  /**
   * notify the endgame observers that the game is in the endgame state
   */
  void notifyForEndGame();
}