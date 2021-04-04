package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.track.Square;
import it.polimi.ingsw.model.track.Track;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class GSONTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");
   File trackConfigFile = new File("src/SquareConfig.json");

   @Test
   void developCardParseTest() {
      DevelopCardDeck developCardDeck;
      try {
         developCardDeck = GSON.cardParser(cardConfigFile);
         System.out.println(developCardDeck.getDevelopCard().getCardFlag());
         System.out.println(developCardDeck.getDevelopCard().getCost());
         for (int i = 0; i < developCardDeck.visibleCards().length; i++) {
            for (int j = 0; j < developCardDeck.visibleCards()[i].length; j++) {
               System.out.print(developCardDeck.visibleCards()[i][j].getCardFlag().getLevel() + " ");
            }
            System.out.println();
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   @Test
   void developCardParseTest2() {
      DevelopCardDeck developCardDeck;
      try {
         developCardDeck = GSON.cardParser(cardConfigFile);
         for (int i = 0; i < developCardDeck.visibleCards().length; i++) {
            for (int j = 0; j < developCardDeck.visibleCards()[i].length; j++) {
               System.out.print(developCardDeck.visibleCards()[i][j].getCardFlag().getColor() + " ");
            }
            System.out.println();
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   //remove some cards before printing
   @Test
   void developCardParseTest3() {
      DevelopCardDeck developCardDeck;
      try {
         developCardDeck = GSON.cardParser(cardConfigFile);
         developCardDeck.removeCard(developCardDeck.getCard(0,0));
         for (int i = 0; i < developCardDeck.visibleCards().length; i++) {
            for (int j = 0; j < developCardDeck.visibleCards()[i].length; j++) {
               System.out.print(developCardDeck.visibleCards()[i][j].getCost() + " ");
            }
            System.out.println();
         }

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
