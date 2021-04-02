package it.polimi.ingsw.model.track;
import it.polimi.ingsw.controller.EndGameObserver;

import java.util.ArrayList;
import java.util.List;

public class LorenzoTrack implements VaticanReportObservable, EndGameObservable{
  Square[] track;
  int playerPosition;
  final List<VaticanReportObserver> vaticanReportObserverList = new ArrayList<>();
  final List<EndGameObserver> endGameObserverList = new ArrayList<>();

  public LorenzoTrack() {
    /*for (int i = 0; i < 25; i++) {
      if (i <= 3)
        track[i] = new Square(1, false, 0);
      else if (i <= 6) {
        if (i == 4)
          track[i] = new Square(2, false, 0);
        else
          track[i] = new Square(2, false, 1);
      } else if (i <= 9) {
        if (i < 8)
          track[i] = new Square(4, false, 1);
        else if (i == 8)
          track[i] = new Square(4, true, 1);
        else
          track[i] = new Square(4, false, 0);
      } else if (i <= 12) {
        if (i != 12)
          track[i] = new Square(6, false, 0);
        else
          track[i] = new Square(6, false, 2);
      } else if (i <= 15) {
        track[i] = new Square(9, false, 2);
      } else if (i <= 18) {
        if (i > 16)
          track[i] = new Square(12, false, 0);
        else
          track[i] = new Square(12, true, 2);
      } else if (i <= 21) {
        track[i] = new Square(16, false, 3);
      } else if (i != 24)
        track[i] = new Square(20, false, 3);
      else
        track[i] = new Square(20, true, 3);
    }*/
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
