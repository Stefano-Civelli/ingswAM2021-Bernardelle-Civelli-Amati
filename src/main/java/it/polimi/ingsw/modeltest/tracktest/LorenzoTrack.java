package it.polimi.ingsw.modeltest.tracktest;
import it.polimi.ingsw.controller.EndGameObserver;

import java.util.ArrayList;
import java.util.List;

public class LorenzoTrack implements VaticanReportObservable, EndGameObservable{
  Square[] track;
  int playerPosition;
  final List<VaticanReportObserver> vaticanReportObserverList = new ArrayList<>();
  final List<EndGameObserver> endGameObserverList = new ArrayList<>();

  public LorenzoTrack() {
  }

  public Square[] getTrack() {
    return track;
  }

  public void moveForward(int faith) {
    for (int i = 0; i < faith; i++) {
      if (playerPosition < 24) {

        playerPosition += 1;
        if (track[playerPosition].getRed()) {
          int active = track[playerPosition].getActive()-1;
          notifyForVaticanReport(active);
        }
      }

      if(playerPosition == 24)
        notifyForEndGame();
    }
  }

  @Override
  public void addToVaticanReportObserverList(VaticanReportObserver observerToAdd) {
    if(!vaticanReportObserverList.contains(observerToAdd))
      vaticanReportObserverList.add(observerToAdd);
  }

  @Override
  public void removeFromVaticanReportObserverList(VaticanReportObserver observerToRemove) {
      vaticanReportObserverList.remove(observerToRemove);
  }

  @Override
  public void notifyForVaticanReport(int active) {
    for(VaticanReportObserver x : vaticanReportObserverList)
      x.update(active);
  }

  @Override
  public void addToEndGameObserverList(EndGameObserver observerToAdd) {
    if(!endGameObserverList.contains(observerToAdd))
      endGameObserverList.add(observerToAdd);
  }

  @Override
  public void removeFromEndGameObserverList(EndGameObserver observerToRemove) {
    endGameObserverList.remove(observerToRemove);
  }

  @Override
  public void notifyForEndGame() {
    for(EndGameObserver x : endGameObserverList)
      x.update();
  }
}
