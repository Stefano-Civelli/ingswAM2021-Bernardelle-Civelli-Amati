package it.polimi.ingsw.model.track;

import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class LorenzoTrackTest {
  File trackConfigFile = new File("src/SquareConfig.json");
  File lorenzoTrackConfigFile = new File("src/SquareConfig.json");
  Track track;
  LorenzoTrack lorenzoTrack;

  @Test
  void checkIfCurrentPositionIsActiveTEST() {
    {
      try {
        track = GSON.trackParser(trackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    {
      try {
        lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST2() {
    {
      try {
        track = GSON.trackParser(trackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    {
      try {
        lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(7);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST3() {
    {
      try {
        track = GSON.trackParser(trackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    {
      try {
        lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(7);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    track.moveForward(7);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 9+3+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST4() {
    {
      try {
        track = GSON.trackParser(trackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    {
      try {
        lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(5);
    lorenzoTrack.moveForward(17);
    assertEquals(track.calculateTrackScore(), 2+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST5() {
    {
      try {
        track = GSON.trackParser(trackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    {
      try {
        lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(4);
    lorenzoTrack.moveForward(8);
    track.moveForward(4);
    track.moveForward(4);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 6+3);
    //removing track from track2 observerList it shouldn't be notified when track2 reaches a red square
    lorenzoTrack.removeFromVaticanReportObserverList(track);
    track.moveForward(8);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 3+16);
  }

  //need to add a test to check the endGame notify call
  @Test
  void checkForEndGameNotifyCall(){
    //
  }
}