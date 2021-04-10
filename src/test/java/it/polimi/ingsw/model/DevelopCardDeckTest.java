package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
      for (int i = 0; i < developCardDeck.visibleCards().length; i++)
         for (int j = 0; j < developCardDeck.visibleCards()[i].length; j++)
            if(previous[i][j] != developCardDeck.visibleCards()[i][j]) {
               isSame = false;
               break;
            }

      assertTrue(isSame);
   }

   @Test
   void removeInvalidCard() throws IOException, RowOrColumnNotExistsException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      boolean pippo = false;
      DevelopCard developCard = developCardDeck.getCard(0,0);
      try {
         developCardDeck.removeCard(developCard);
      } catch (InvalidCardException e) {
         System.out.println("This first remove doesn't work as it should");
      }
      try {
         developCardDeck.removeCard(developCard);
      } catch (InvalidCardException e) {
         pippo = true;
      }
      assertTrue(pippo);

      DevelopCard[][] visible = developCardDeck.visibleCards();
      for(int i=0; i<visible.length; i++)
         for(int j=0; j<visible[i].length; j++)
            if(visible[i][j].equals(developCard))
               assertTrue(false);
   }

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

   @Test
   void canBuyAllCards() throws IOException, AbuseOfFaithException, RowOrColumnNotExistsException, InvalidCardPlacementException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
      playerBoard.getChest().addResources(ResourceType.GOLD,9);
      playerBoard.getChest().addResources(ResourceType.SERVANT,9);
      playerBoard.getChest().addResources(ResourceType.SHIELD,9);
      playerBoard.getChest().addResources(ResourceType.STONE,9);
      playerBoard.getChest().mergeMapResources();

      playerBoard.getCardSlots().addDevelopCard(1, developCardDeck.getCard(0,0));
      try {
         developCardDeck.removeCard(developCardDeck.getCard(0,0));
      } catch (InvalidCardException e) {
         System.out.println("Non enough resources to buy card 1");
      }
      playerBoard.getCardSlots().addDevelopCard(1, developCardDeck.getCard(1,0));
      try {
         developCardDeck.removeCard(developCardDeck.getCard(1,0));
      } catch (InvalidCardException e) {
         System.out.println("Non enough resources to buy card 2");
      }
      playerBoard.getCardSlots().addDevelopCard(0, developCardDeck.getCard(0,0));
      try {
         developCardDeck.removeCard(developCardDeck.getCard(0,0));
      } catch (InvalidCardException e) {
         System.out.println("Non enough resources to buy card 3");
      }

      int count = 0;
      for (DevelopCard developCard : developCardDeck.buyableCards(playerBoard))
         count++;
      assertTrue(count == 12);
   }

   //need to add a test to check the endGame notify call
   @Test
   void checkForEndGameNotifyCall(){
      //
   }
}