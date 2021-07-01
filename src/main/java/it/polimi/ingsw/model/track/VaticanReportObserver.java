package it.polimi.ingsw.model.track;

/**
 * It's used together with {@link it.polimi.ingsw.model.track.VaticanReportObservable VaticanReportObserver} to notify
 * other players' track when a track reaches a vatican report cell
 */
public interface VaticanReportObserver {

  /**
   * When the observable class changes it's state notify the observer calling this method
   *
   * @param active represent the active zone in which the vatican report is held
   */
  void update(int active);

  /**
   * Add an observer to a list of observers
   *
   * @param observerToAdd the observer to add
   */
  void addToVaticanReportObserverList(VaticanReportObserver observerToAdd);
}