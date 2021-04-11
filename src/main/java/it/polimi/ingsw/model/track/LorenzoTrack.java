package it.polimi.ingsw.model.track;
import it.polimi.ingsw.controller.EndGameObserver;

import java.util.ArrayList;
import java.util.List;

public class LorenzoTrack implements VaticanReportObservable, EndGameObservable{
  Square[] track;
  int playerPosition;
  final List<VaticanReportObserver> vaticanReportObserverList = new ArrayList<>();
  final List<EndGameObserver> endGameObserverList = new ArrayList<>();

  /**
   * constructor of the class
   * instances of this class are made with a json file
   */
  public LorenzoTrack() {
  }

  /**
   * getter of the track
   * @return a track's clone
   */
  public Square[] getTrack() {
    return track.clone();
  }

  /**
   * Let Lorenzo move his faith marker along the track
   * Every time Lorenzo steps on a red Square the track's observers are notified
   * When Lorenzo reaches the end (Square 24) the observer that manage the end game is notified
   * @param faith number of steps to do on the track from the current position
   */
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
    if (!vaticanReportObserverList.contains(observerToAdd)){
      vaticanReportObserverList.add(observerToAdd);
    }
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
