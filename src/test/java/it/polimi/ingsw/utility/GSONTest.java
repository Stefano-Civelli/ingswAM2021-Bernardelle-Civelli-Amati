package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.model.modelexceptions.RowOrColumnNotExistsException;
import it.polimi.ingsw.model.track.Square;
import it.polimi.ingsw.model.track.Track;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

class GSONTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");
   File trackConfigFile = new File("src/SquareConfig.json");
   File leaderCardConfigFile = new File("src/LeaderCardConfig.json");

//   @Test
//   void developCardParseTest() {
//      DevelopCardDeck developCardDeck;
//      try {
//         developCardDeck = GSON.cardParser(cardConfigFile);
//         for(DevelopCard d : developCardDeck.visibleCards()){
//            System.out.println(d.getCardFlag().getLevel());
//         }
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//   }
//
//
//   @Test
//   void developCardParseTest2() {
//      DevelopCardDeck developCardDeck;
//      try {
//         developCardDeck = GSON.cardParser(cardConfigFile);
//         for(DevelopCard d : developCardDeck.visibleCards()){
//            System.out.println(d.getCardFlag().getLevel());
//         }
//
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//   }
//
//   //remove some cards before printing
//   @Test
//   void developCardParseTest3() throws RowOrColumnNotExistsException {
//      DevelopCardDeck developCardDeck;
//      try {
//         developCardDeck = GSON.cardParser(cardConfigFile);
//         developCardDeck.removeCard(developCardDeck.getCard(0,0));
//         for(DevelopCard d : developCardDeck.visibleCards()){
//            System.out.println(d.getCardFlag().getLevel());
//         }
//
//      } catch (IOException | InvalidCardException e) {
//         e.printStackTrace();
//      }
//   }
//
//   @Test
//   void parseTestForTrackConfigFile() {
//      Track track;
//      try {
//         track = GSON.trackParser(trackConfigFile);
//         for(Square x : track.getTrack())
//            System.out.print(x.getActive() + " ");
//         System.out.println();
//         for(Square x : track.getTrack())
//            System.out.print(x.getVictoryPoints() + " ");
//         System.out.println();
//         for(Square x : track.getTrack())
//            System.out.print(x.getRed() + " ");
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//   }
//
//   @Test
//   void leaderCardParseTest() {
//      LeaderCardDeck leaderCardDeck;
//      try {
//         leaderCardDeck = GSON.leaderCardParser(leaderCardConfigFile);
//         for (LeaderCard l : leaderCardDeck.getLeaderCardList())
//            System.out.println(l.resourceOnWhite());
//
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//   }

//   @Test
//   void IOExceptionTest() {
//      final LeaderCardDeck leaderCardDeck;
//      File pippoFile = new File("pippo");
//      assertThrows(IOException.class, () -> GSON.leaderCardParser(pippoFile));
//   }
}