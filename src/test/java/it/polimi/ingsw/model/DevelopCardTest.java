package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


class DevelopCardTest {
   File cardConfigFile = new File("src/DevelopCardConfig.json");

   @Test
   void testIfLevel1AreBuyable() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,2);
      playerBoard.getChest().addResources(ResourceType.STONE,3);
      playerBoard.getChest().addResources(ResourceType.SERVANT,2);
      playerBoard.getChest().addResources(ResourceType.SHIELD,2);
      playerBoard.getChest().endOfTurnMapsMerge();

      for(DevelopCard d : developCardDeck.visibleCards())
         if(d.getCardFlag().getLevel() == 1)
            assertTrue(d.isBuyable(playerBoard));
   }

   @Test
   void everyCardIsBuyable() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      developCardDeck.getCard(0,2).buy(playerBoard,1);
      developCardDeck.getCard(1,3).buy(playerBoard,1);
      developCardDeck.getCard(0,2).buy(playerBoard,2);
      for(DevelopCard d : developCardDeck.visibleCards())
            assertTrue(d.isBuyable(playerBoard));
   }

   @Test
   void notVisibleCardIsBuyable() throws NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.GOLD,1);
      playerBoard.getChest().addResources(ResourceType.STONE,2);
      playerBoard.getChest().endOfTurnMapsMerge();
      HashMap<ResourceType, Integer> cost = new HashMap<>();
      cost.put(ResourceType.GOLD,2);
      cost.put(ResourceType.STONE,2);
      DevelopCard developCard = new DevelopCard(new CardFlag(1,DevelopCardColor.BLUE)
                                                , cost, new HashMap<>(), new HashMap<>()
                                                , 10);

      //false because in order to be buyable the card has to be a visible card in the deck
      assertFalse(developCard.isBuyable(playerBoard));
   }

   @Test
   void nullValueIsBuyable() throws IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      assertThrows(NullPointerException.class, () -> developCardDeck.getCard(0,0).isBuyable(null));
   }


   @Test
   void buyCardsFromSamePosition() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      developCardDeck.getCard(0,2).buy(playerBoard,0);
      developCardDeck.getCard(0,2).buy(playerBoard,1);
      developCardDeck.getCard(0,2).buy(playerBoard,2);
   }

   @Test
   void invalidCardSlotPlacementInBuy() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      //these tests do not influence eachother because they are done using a different cardslot numbers (0, 1 and 2)
      //level 2 card in empty card slot (cardslot number 0)
      assertThrows(NotBuyableException.class, () -> developCardDeck.getCard(1,3).buy(playerBoard,0));
      //level 3 card on top of level 1 card (cardslot number 1)
      developCardDeck.getCard(0,0).buy(playerBoard,2);
      developCardDeck.getCard(1,0).buy(playerBoard,2);
      developCardDeck.getCard(0,0).buy(playerBoard,1);
      assertThrows(InvalidCardPlacementException.class, () -> developCardDeck.getCard(2,0).buy(playerBoard,1));
   }


   @Test
   void isActivatebleTest() throws IOException, RowOrColumnNotExistsException, NegativeQuantityException, AbuseOfFaithException, NotEnoughSpaceException, InvalidCardPlacementException, NotBuyableException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      developCardDeck.getCard(0,0).buy(playerBoard,0);
      CardSlots cardSlots = playerBoard.getCardSlots();
      assertTrue(cardSlots.activatableCards(playerBoard).contains(cardSlots.returnTopCard(0)));

   }

   @Test
   void produceTestNoExceptions() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException, NotActivatableException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      developCardDeck.getCard(0,0).buy(playerBoard,0);
      CardSlots cardSlots = playerBoard.getCardSlots();
      cardSlots.returnTopCard(0).produce(playerBoard);

   }

   @Test
   void produceCardNotOnTop() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException, NotActivatableException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      DevelopCard bottomCard = developCardDeck.getCard(0,0);
      bottomCard.buy(playerBoard,0);
      developCardDeck.getCard(1,0).buy(playerBoard,0);
      CardSlots cardSlots = playerBoard.getCardSlots();
      assertThrows(NotActivatableException.class, () -> bottomCard.produce(playerBoard));

      //produce on the top card shouldn't throw exceptions
      cardSlots.returnTopCard(0).produce(playerBoard);

   }


//   @Test //TODO
//   void produceWithNotEnoughResources() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException, NotActivatableException {
//      DevelopCardDeck developCardDeck;
//      developCardDeck = GSON.cardParser(cardConfigFile);
//      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
//      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
//      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
//      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
//      playerBoard.getChest().addResources(ResourceType.GOLD,99);
//      playerBoard.getChest().addResources(ResourceType.STONE,99);
//      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
//      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
//      playerBoard.getChest().endOfTurnMapsMerge();
//      developCardDeck.getCard(0,0).buy(playerBoard,0);
//      developCardDeck.getCard(1,0).buy(playerBoard,0);
//      CardSlots cardSlots = playerBoard.getCardSlots();
//      assertThrows(NotActivatableException.class, () -> bottomCard.produce(playerBoard));
//
//      //produce on the top card shouldn't throw exceptions
//      cardSlots.returnTopCard(0).produce(playerBoard);
//
//   }
}