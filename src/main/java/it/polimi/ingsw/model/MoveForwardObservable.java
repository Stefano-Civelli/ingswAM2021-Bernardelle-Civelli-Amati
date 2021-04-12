package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.EndGameObserver;

public interface MoveForwardObservable {
  /**
   * add an observer to a list of observers
   * @param observerToAdd
   */
  public void addToMoveForwardObserverList (MoveForwardObserver observerToAdd);

  /**
   * remove an observer from a list of observers
   * @param observerToRemove
   */
  public void removeFromMoveForwardObserverList (MoveForwardObserver observerToRemove);

  /**
   * notify the moveForward observers that they have to move their faith marker
   */
  void notifyForMoveForward();
}

