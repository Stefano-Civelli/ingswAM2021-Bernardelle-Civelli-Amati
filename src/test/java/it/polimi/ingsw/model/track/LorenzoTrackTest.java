package it.polimi.ingsw.model.track;

import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoTrackTest {
  InputStream trackConfigStream = ConfigParameters.trackConfigStream;
  InputStream lorenzoTrackConfigStream = ConfigParameters.lorenzoTrackConfigStream;
  Track track;
  LorenzoTrack lorenzoTrack;

  @Test
  void checkIfCurrentPositionIsActiveTEST() throws IOException {

    track = GSON.trackParser(trackConfigStream);
    lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigStream);

    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST2() throws IOException {

    track = GSON.trackParser(trackConfigStream);
    lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigStream);

    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(7);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST3() throws IOException {

    track = GSON.trackParser(trackConfigStream);
    lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigStream);

    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(7);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    track.moveForward(7);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 9+3+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST4() throws IOException {

    track = GSON.trackParser(trackConfigStream);
    lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigStream);

    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(5);
    lorenzoTrack.moveForward(17);
    assertEquals(track.calculateTrackScore(), 2+2);
  }

  @Test
  void checkIfCurrentPositionIsActiveTEST5() throws IOException {

    track = GSON.trackParser(trackConfigStream);
    lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigStream);

    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(4);
    lorenzoTrack.moveForward(8);
    track.moveForward(4);
    track.moveForward(4);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 6+3);
    //removing track from lorenzoTrack observerList it shouldn't be notified when lorenzoTrack reaches a red square
    lorenzoTrack.removeFromVaticanReportObserverList(track);
    track.moveForward(8);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 3+16);
  }

  @Test
  void moreMovesThanAllowed() throws IOException {

    track = GSON.trackParser(trackConfigStream);
    lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigStream);

    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(4);
    lorenzoTrack.moveForward(30);

    assertEquals(lorenzoTrack.playerPosition, 24);
    assertEquals(track.calculateTrackScore(), 2);
  }
}