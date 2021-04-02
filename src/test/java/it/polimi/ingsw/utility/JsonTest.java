package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.track.Track;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");
   @Test
   void parseTest3() {
      try {
         GSON.cardParser(cardConfigFile);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   File trackConfigFile = new File("src/SquareConfig.json");
   @Test
   void parseTest4() {
      Track track;
      try {
         track = GSON.trackParser(trackConfigFile);
         System.out.println(track.getTrack()[0].getActive());
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
