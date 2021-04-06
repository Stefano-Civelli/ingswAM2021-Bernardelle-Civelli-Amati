package it.polimi.ingsw.model.track;

public interface VaticanReportObservable {
  /**
   * add an observer to a list of observers
   * @param observerToAdd
   */
  public void addToVaticanReportObserverList(VaticanReportObserver observerToAdd);

  /**
   * remove an observer from a list of observers
   * @param observerToRemove
   */
  public void removeFromVaticanReportObserverList(VaticanReportObserver observerToRemove);

  /**
   * notify the vaticanReport observers that a vatican report is started
   */
  void notifyForVaticanReport(int active);
}
