package it.polimi.ingsw.model;

public interface MoveForwardObservable {

  /**
   * notify the moveForward observers that they have to move their faith marker
   */
  void notifyForMoveForward();

  void addToMoveForwardObserverList(MoveForwardObserver observerToAdd);
}

