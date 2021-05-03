package it.polimi.ingsw.model.track;
import it.polimi.ingsw.controller.EndGameObserver;
import it.polimi.ingsw.model.EndGameObservable;
import it.polimi.ingsw.model.ModelObservable;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.MoveForwardObserver;
import it.polimi.ingsw.network.messages.Message;

import java.util.HashSet;
import java.util.Set;

 //dovrei implementare MoveForewardObserver ma é un casino
public class LorenzoTrack implements VaticanReportObservable, EndGameObservable, ModelObservable, MoveForwardObserver {
  Square[] track;
  int playerPosition;
  final Set<VaticanReportObserver> vaticanReportObserverList = new HashSet<>();
  final Set<EndGameObserver> endGameObserverList = new HashSet<>();

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
  public void notifyForEndGame() {
    for(EndGameObserver x : endGameObserverList)
      x.update();
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
  public void notifyModelChange(Message msg) {
    //for(ModelObserver x : wrebfjkwrhqbfqr)
      //x.update(msg);
  }

//  private Message TrackMoveUpdate(int playerPosition){
//    return new
//  }


//   @Override
//   public void addToMoveForwardObserverList(MoveForwardObserver observerToAdd) {
//     moveForwardObserverList.add(observerToAdd);
//     //moveForwardObserverList.add(observerToAdd); //non penso sia necessaria
//   }
}
