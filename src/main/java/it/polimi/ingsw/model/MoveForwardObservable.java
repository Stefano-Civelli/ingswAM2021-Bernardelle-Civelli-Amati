package it.polimi.ingsw.model;

public interface MoveForwardObservable {
  /**
   * add an observer to a list of observers
   * @param observerToAdd
   */
  void addToMoveForwardObserverList (MoveForwardObserver observerToAdd);

  /**
   * remove an observer from a list of observers
   * @param observerToRemove
   */
  void removeFromMoveForwardObserverList (MoveForwardObserver observerToRemove);

  /**
   * notify the moveForward observers that they have to move their faith marker
   */
  void notifyForMoveForward();
}

