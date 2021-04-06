package it.polimi.ingsw.model.track;

public interface VaticanReportObserver {
  /**
   * when the observable class changes it's state notify the observer calling this method
   * @param active represent the active zone in which the vatican report is held
   */
  public void update(int active);
}
