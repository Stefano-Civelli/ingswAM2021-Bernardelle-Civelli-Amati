package it.polimi.ingsw.modeltest.tracktest;

public interface VaticanReportObservable {
  public void addToVaticanReportObserverList(VaticanReportObserver observerToAdd);
  public void removeFromVaticanReportObserverList(VaticanReportObserver observerToRemove);
  void notifyForVaticanReport(int active);
}
