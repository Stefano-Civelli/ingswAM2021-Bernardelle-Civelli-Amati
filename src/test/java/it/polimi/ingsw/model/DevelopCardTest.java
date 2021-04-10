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


class DevelopCardTest {
   File cardConfigFile = new File("src/DevelopCardConfig.json");

   @Test
   void testIfLevel1AreBuyable() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
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
   void everyCardIsBuyable() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotEnoughResourcesException, NotBuyableException, InvalidCardException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
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
   void nullValueIsBuyable() throws RowOrColumnNotExistsException, IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
      assertThrows(NullPointerException.class, () -> developCardDeck.getCard(0,0).isBuyable(null));
   }


   @Test
   void buyCardsFromSamePosition() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException, NotEnoughResourcesException, InvalidCardException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
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
   void invalidCardSlotPlacementInBuy() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotBuyableException, NotEnoughResourcesException, InvalidCardException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser(cardConfigFile);
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
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



}