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
  Track lorenzoTrack;

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
        lorenzoTrack = GSON.trackParser(lorenzoTrackConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    track.addToVaticanReportObserverList(lorenzoTrack);
    lorenzoTrack.addToVaticanReportObserverList(track);
    track.moveForward(8);
    assertEquals(lorenzoTrack.calculateTrackScore(), 1);
    assertEquals(track.calculateTrackScore(), 4+2);
    lorenzoTrack.moveForward(8);
    assertEquals(track.calculateTrackScore(), 4+2);
    assertEquals(lorenzoTrack.calculateTrackScore(), 4);
  }

}