package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevelopCardDeckTest {

   @Test
   void buyableCardsTest() throws AbuseOfFaithException, IOException, NegativeQuantityException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<>(), new Market(null), developCardDeck);
      playerBoard.getChest().addResources(ResourceType.GOLD,3);
      playerBoard.getChest().endOfTurnMapsMerge();
      for(DevelopCard d : developCardDeck.buyableCards(playerBoard))
         assertTrue(d.isBuyable(playerBoard));
   }

   @Test
   void removePresentCard() throws IOException, InvalidCardException, InvalidDevelopCardException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      DevelopCard previousCard = developCardDeck.getCard(0,0);
      developCardDeck.removeCard(developCardDeck.getCard(0,0));
      assertNotEquals(previousCard, developCardDeck.getCard(0,0));
   }

   @Test
   void removeNullCard() throws IOException{
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      boolean pippo = false;
      ArrayList<DevelopCard> previous = developCardDeck.visibleCards();
      try {
         developCardDeck.removeCard(null);
      } catch (InvalidCardException e) {
         pippo = true;
      }

      assertFalse(pippo);
      assertEquals(previous, developCardDeck.visibleCards());
   }

   @Test
   void removeInvalidCard() throws IOException, InvalidDevelopCardException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      DevelopCard developCard = developCardDeck.getCard(0,0);
      try {
         developCardDeck.removeCard(developCard);
      } catch (InvalidCardException e) {
         System.out.println("This first remove doesn't work as it should");
      }

      assertThrows(InvalidCardException.class, () -> developCardDeck.removeCard(developCard));

      assertFalse(developCardDeck.visibleCards().contains(developCard));
   }

   @Test
   void getInvalidCard() throws IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      boolean pippo = false;
      try {
         developCardDeck.getCard(1,12);
      } catch (InvalidDevelopCardException e) {
         pippo = true;
      }
      assertTrue(pippo);

   }

   @Test
   void canBuyAllCards() throws IOException, AbuseOfFaithException, InvalidDevelopCardException, InvalidCardSlotException, NegativeQuantityException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<>(), new Market(null), developCardDeck);
      playerBoard.getChest().addResources(ResourceType.GOLD,9);
      playerBoard.getChest().addResources(ResourceType.SERVANT,9);
      playerBoard.getChest().addResources(ResourceType.SHIELD,9);
      playerBoard.getChest().addResources(ResourceType.STONE,9);
      playerBoard.getChest().endOfTurnMapsMerge();

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
      for (DevelopCard ignored : developCardDeck.buyableCards(playerBoard))
         count++;
      assertEquals(count, 12);
   }


   @Test
   void getCardFromIdTest() throws IOException, InvalidCardException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      DevelopCard developCard = developCardDeck.getCardFromId(1);
      assertNotNull(developCard);
   }

   @Test
   void getCardFromIdTest2() throws IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      assertThrows(InvalidCardException.class, () -> developCardDeck.getCardFromId(100000000));
   }
}