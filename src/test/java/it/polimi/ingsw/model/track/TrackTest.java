package it.polimi.ingsw.model.track;

import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

   File trackConfigFile = new File("src/SquareConfig.json");
   Track track;
   Track track2;

   @Test //test the score of the track if the player has never moved his faith marker
   void calculateTrackScoreTEST() throws IOException {

      track = GSON.trackParser(trackConfigFile);
      assertEquals(track.calculateTrackScore(), 1);
   }

   @Test //test if victory points implementation actually works
   void calculateTrackScoreTEST2() throws IOException {

      track = GSON.trackParser(trackConfigFile);

      track.moveForward(4);
      assertEquals(track.calculateTrackScore(), 2);
      track.moveForward(1);
      assertEquals(track.calculateTrackScore(), 2);
      track.moveForward(2);
      assertEquals(track.calculateTrackScore(), 4);
   }

   @Test
   void checkIfCurrentPositionIsActiveTEST() throws IOException {

      track = GSON.trackParser(trackConfigFile);
      track2 = GSON.trackParser(trackConfigFile);

      track.addToVaticanReportObserverList(track2);
      track2.addToVaticanReportObserverList(track);
      track.moveForward(8);
      assertEquals(track2.calculateTrackScore(), 1);
      assertEquals(track.calculateTrackScore(), 4+2);
      track2.moveForward(8);
      assertEquals(track2.calculateTrackScore(), 4);
      assertEquals(track.calculateTrackScore(), 4+2);
   }

   @Test
   void checkIfCurrentPositionIsActiveTEST2() throws IOException {
      track = GSON.trackParser(trackConfigFile);
      track2 = GSON.trackParser(trackConfigFile);

      track.addToVaticanReportObserverList(track2);
      track2.addToVaticanReportObserverList(track);
      track.moveForward(7);
      track2.moveForward(8);
      track2.moveForward(8);
      assertEquals(track.calculateTrackScore(), 4+2);
      assertEquals(track2.calculateTrackScore(), 12+2+3);
   }

   @Test
   void checkIfCurrentPositionIsActiveTEST3() throws IOException {

      track = GSON.trackParser(trackConfigFile);
      track2 = GSON.trackParser(trackConfigFile);

      track.addToVaticanReportObserverList(track2);
      track2.addToVaticanReportObserverList(track);
      track.moveForward(5);
      track2.moveForward(17);
      assertEquals(track.calculateTrackScore(), 2+2);
      assertEquals(track2.calculateTrackScore(), 12+2+3);
   }

   @Test
   void checkIfCurrentPositionIsActiveTEST4() throws IOException {

      track = GSON.trackParser(trackConfigFile);
      track2 = GSON.trackParser(trackConfigFile);

      track.addToVaticanReportObserverList(track2);
      track2.addToVaticanReportObserverList(track);
      track.moveForward(4);
      track2.moveForward(8);
      track.moveForward(4);
      track.moveForward(4);
      track2.moveForward(8);
      assertEquals(track.calculateTrackScore(), 9);
      assertEquals(track2.calculateTrackScore(), 2+3+12);
      //removing track from track2 observerList it shouldn't be notified when track2 reaches a red square
      track2.removeFromVaticanReportObserverList(track);
      track.moveForward(8);
      track2.moveForward(8);
      assertEquals(track.calculateTrackScore(), 3+16);
      assertEquals(track2.calculateTrackScore(), 2+3+4+20);
   }

   @Test
   void negativeInputTest() throws IOException {
      track = GSON.trackParser(trackConfigFile);
      track.moveForward(-1);
      assertEquals(track.calculateTrackScore(), 1);
   }

   @Test
   void negativeInputTest2() throws IOException {

      track = GSON.trackParser(trackConfigFile);
      track2 = GSON.trackParser(trackConfigFile);

      track.addToVaticanReportObserverList(track2);
      track2.addToVaticanReportObserverList(track);
      track.moveForward(8);
      assertEquals(track2.calculateTrackScore(), 1);
      assertEquals(track.calculateTrackScore(), 4+2);
      track2.moveForward(8);
      assertEquals(track2.calculateTrackScore(), 4);
      assertEquals(track.calculateTrackScore(), 4+2);
      track.moveForward(-1);
      assertEquals(track2.calculateTrackScore(), 4);
      assertEquals(track.calculateTrackScore(), 4+2);
      track.moveForward(8);
      assertEquals(track2.calculateTrackScore(), 4);
      assertEquals(track.calculateTrackScore(), 12+3+2);
   }

   @Test
   void moreMovesThanAllowed() throws IOException {

      track = GSON.trackParser(trackConfigFile);
      track2 = GSON.trackParser(trackConfigFile);

      track2.addToVaticanReportObserverList(track);
      track.addToVaticanReportObserverList(track2);
      track.moveForward(4);
      track2.moveForward(30);

      assertEquals(track2.playerPosition, 24);
      assertEquals(track.calculateTrackScore(), 2);
   }
}