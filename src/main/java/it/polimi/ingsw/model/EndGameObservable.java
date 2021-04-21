package it.polimi.ingsw.model;
import it.polimi.ingsw.controller.EndGameObserver;

public interface EndGameObservable {
  /**
   * add an observer to a list of observers
   * @param observerToAdd
   */
  void addToEndGameObserverList(EndGameObserver observerToAdd);

  /**
   * remove an observer from a list of observers
   * @param observerToRemove
   */
  void removeFromEndGameObserverList(EndGameObserver observerToRemove);

  /**
   * notify the endgame observers that the game is in the endgame state
   */
  void notifyForEndGame();
}
