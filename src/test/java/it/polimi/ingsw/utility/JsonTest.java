package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.track.Square;
import it.polimi.ingsw.model.track.Track;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");
   File trackConfigFile = new File("src/SquareConfig.json");

   @Test
   void developCardParseTest() {
      DevelopCardDeck developCardDeck;
      try {
         developCardDeck = GSON.cardParser(cardConfigFile);
         System.out.println(developCardDeck.getDevelopCard().getCardFlag());
         System.out.println(developCardDeck.getDevelopCard().getCost());
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Test
   void parseTestForTrackConfigFile() {
      Track track;
      try {
         track = GSON.trackParser(trackConfigFile);
         for(Square x : track.getTrack())
            System.out.print(x.getActive() + " ");
         System.out.println();
         for(Square x : track.getTrack())
            System.out.print(x.getVictoryPoints() + " ");
         System.out.println();
         for(Square x : track.getTrack())
            System.out.print(x.getRed() + " ");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
