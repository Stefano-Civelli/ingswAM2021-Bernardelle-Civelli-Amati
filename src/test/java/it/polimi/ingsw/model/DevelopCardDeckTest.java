package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevelopCardDeckTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");

   @Test
   void buyableCardsTest() throws AbuseOfFaithException, IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
      playerBoard.getChest().addResources(ResourceType.GOLD,3);
      playerBoard.getChest().mergeMapResources();
      for(DevelopCard d : developCardDeck.buyableCards(playerBoard))
         assertTrue(d.isBuyable(playerBoard));
   }

   @Test
   void removePresentCard() throws IOException, InvalidCardException, RowOrColumnNotExistsException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      DevelopCard previousCard = developCardDeck.getCard(0,0);
      developCardDeck.removeCard(developCardDeck.getCard(0,0));
      assertNotEquals(previousCard, developCardDeck.getCard(0,0));
   }

   @Test
   void removeNullCard() throws IOException{
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      boolean pippo = false;
      boolean isSame = true;
      DevelopCard[][] previous = developCardDeck.visibleCards();
      try {
         developCardDeck.removeCard(null);
      } catch (InvalidCardException e) {
         pippo = true;
      }
      assertFalse(pippo);
      for (int i = 0; i <developCardDeck.visibleCards().length; i++)
         for (int j = 0; j < developCardDeck.visibleCards()[i].length; j++)
            if(previous[i][j] != developCardDeck.visibleCards()[i][j]) {
               isSame = false;
               break;
            }

      assertTrue(isSame);
   }

   //TODO
//   @Test
//   void removeInvalidCard() throws IOException{
//      DevelopCardDeck developCardDeck;
//      developCardDeck = GSON.cardParser(cardConfigFile);
//      boolean pippo = false;
//      try {
//         developCardDeck.removeCard();
//      } catch (InvalidCardException e) {
//         pippo = true;
//      }
//      assertTrue(pippo);
//   }

   @Test
   void getInvalidCard() throws IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      boolean pippo = false;
      try {
         developCardDeck.getCard(1,12);
      } catch (RowOrColumnNotExistsException e) {
         pippo = true;
      }
      assertTrue(pippo);

   }



}