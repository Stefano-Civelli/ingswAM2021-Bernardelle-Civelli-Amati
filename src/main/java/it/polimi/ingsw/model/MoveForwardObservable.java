package it.polimi.ingsw.model;

/**
 * It's used together with {@link it.polimi.ingsw.model.MoveForwardObserver MoveForwardObserver} to notify
 * a track that have to move their faith marker
 */
public interface MoveForwardObservable {
  /**
   * Notify the moveForward observers that they have to move their faith marker
   */
  void notifyForMoveForward();

  /**
   * Add an observer to a list of observers
   *
   * @param observerToAdd the observer to add
   */
  void addToMoveForwardObserverList(MoveForwardObserver observerToAdd);
}