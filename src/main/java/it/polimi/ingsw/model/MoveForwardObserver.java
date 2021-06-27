package it.polimi.ingsw.model;

/**
 * It's used together with {@link it.polimi.ingsw.model.MoveForwardObservable MoveForwardObservable} to notify
 * a track that have to move their faith marker
 */
public interface MoveForwardObserver {

  /**
   * When the observable class changes it's state notify the observer calling this method
   */
  void update();

  int getTrackPosition();

}
