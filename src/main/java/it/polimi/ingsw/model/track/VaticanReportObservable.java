package it.polimi.ingsw.model.track;

/**
 * It's used together with {@link it.polimi.ingsw.model.track.VaticanReportObserver VaticanReportObserver} to notify
 * other players' track when a track reaches a vatican report cell
 */
public interface VaticanReportObservable {

  /**
   * Add an observer to a list of observers
   *
   * @param observerToAdd the observer to add
   */
  void addToVaticanReportObserverList(VaticanReportObserver observerToAdd);

  /**
   * Remove an observer from a list of observers
   *
   * @param observerToRemove the observer to remove
   */
  void removeFromVaticanReportObserverList(VaticanReportObserver observerToRemove);

  /**
   * Notify the vaticanReport observers that a vatican report is started
   */
  void notifyForVaticanReport(int active);

}
