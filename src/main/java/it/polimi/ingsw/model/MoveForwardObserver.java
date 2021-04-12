package it.polimi.ingsw.model;

import it.polimi.ingsw.model.track.VaticanReportObserver;

public interface MoveForwardObserver {
  /**
   * when the observable class changes it's state notify the observer calling this method
   */
  public void update();

  public void addToMoveForwardObserverList(MoveForwardObserver observerToAdd);
}
