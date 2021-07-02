package it.polimi.ingsw.model.track;
import it.polimi.ingsw.model.EndGameObserver;
import it.polimi.ingsw.model.EndGameObservable;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.MoveForwardObserver;
import it.polimi.ingsw.model.modelobservables.TrackObservable;
import it.polimi.ingsw.model.updatecontainers.TrackUpdate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the Lorenzo's track and his position in a single player game
 */
public class LorenzoTrack implements VaticanReportObservable, EndGameObservable, TrackObservable, MoveForwardObserver {
  Square[] track;
  int playerPosition;
  final Set<VaticanReportObserver> vaticanReportObserverList = new HashSet<>();
  final Set<EndGameObserver> endGameObserverList = new HashSet<>();
  String username;
  transient ModelObserver controller = null;

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
    Square[] clonedTrack = new Square[25];
    int i = 0;
    for (Square x : track){
      clonedTrack[i] = new Square(x.getVictoryPoints(), x.getRed(), x.getActive());
      i++;
    }

    return clonedTrack;
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
    }

    notifyTrackUpdate(new TrackUpdate(playerPosition));
    if(playerPosition == 24){
      notifyForEndGame(false);
    }
  }

  @Override
  public void addToVaticanReportObserverList(VaticanReportObserver observerToAdd) {
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
    endGameObserverList.add(observerToAdd);
  }

  @Override
  public void removeFromEndGameObserverList(EndGameObserver observerToRemove) {
    endGameObserverList.remove(observerToRemove);
  }

  @Override
  public void notifyForEndGame(boolean onySinglePlayer) {
    for(EndGameObserver x : endGameObserverList)
      x.update(onySinglePlayer);
  }

   @Override
   public void update() {
     moveForward(1);
   }

   @Override
   public int getTrackPosition() {
     return playerPosition;
   }


  @Override
  public void notifyTrackUpdate(TrackUpdate msg) {
    if (controller != null) {
      controller.lorenzoTrackUpdate(msg);
    }
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setController(ModelObserver controller) {
    this.controller = controller;
  }
}