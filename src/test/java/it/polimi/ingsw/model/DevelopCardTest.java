package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.DiscountBehaviour;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;


class DevelopCardTest {

   @Test
   void testIfLevel1AreBuyable() throws IOException, NotEnoughSpaceException,
           AbuseOfFaithException, NegativeQuantityException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "Mario", new ArrayList<>(), new Market(null), developCardDeck);
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
   void everyCardIsBuyable() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, InvalidDevelopCardException, NotBuyableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "Mario", new ArrayList<>(), new Market(null), developCardDeck);
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

//   @Test    COMMENTED BECAUSE I REMOVED THIS CONROL FROM THE isBuyable METHOD
//   void notVisibleCardIsBuyable() throws NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException, IOException {
//      DevelopCardDeck developCardDeck;
//      developCardDeck = GSON.cardParser(cardConfigStream);
//      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), new Market(), developCardDeck);
//      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
//      playerBoard.getChest().addResources(ResourceType.GOLD,1);
//      playerBoard.getChest().addResources(ResourceType.STONE,2);
//      playerBoard.getChest().endOfTurnMapsMerge();
//      HashMap<ResourceType, Integer> cost = new HashMap<>();
//      cost.put(ResourceType.GOLD,2);
//      cost.put(ResourceType.STONE,2);
//      DevelopCard developCard = new DevelopCard(new CardFlag(1,DevelopCardColor.BLUE)
//                                                , cost, new HashMap<>(), new HashMap<>()
//                                                , 10);
//
//      //false because in order to be buyable the card has to be a visible card in the deck
//      assertFalse(developCard.isBuyable(playerBoard));
//   }

   @Test
   void nullValueIsBuyable() throws IOException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      assertThrows(NullPointerException.class, () -> developCardDeck.getCard(0,0).isBuyable(null));
   }


   @Test
   void buyCardsFromSamePosition() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, InvalidDevelopCardException, NotBuyableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "Mario", new ArrayList<>(), new Market(null), developCardDeck);
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
   void invalidCardSlotPlacementInBuy() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, InvalidDevelopCardException, NotBuyableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "Mario", new ArrayList<>(), new Market(null), developCardDeck);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.SERVANT);
      playerBoard.getChest().addResources(ResourceType.GOLD,99);
      playerBoard.getChest().addResources(ResourceType.STONE,99);
      playerBoard.getChest().addResources(ResourceType.SERVANT,99);
      playerBoard.getChest().addResources(ResourceType.SHIELD,99);
      playerBoard.getChest().endOfTurnMapsMerge();
      //these tests do not influence each other because they are done using a different cardslot numbers (0, 1 and 2)
      //level 2 card in empty card slot (cardslot number 0)
      assertThrows(NotBuyableException.class, () -> developCardDeck.getCard(1,3).buy(playerBoard,0));
      //level 3 card on top of level 1 card (cardslot number 1)
      developCardDeck.getCard(0,0).buy(playerBoard,2);
      developCardDeck.getCard(1,0).buy(playerBoard,2);
      developCardDeck.getCard(0,0).buy(playerBoard,1);
      assertThrows(InvalidCardSlotException.class, () -> developCardDeck.getCard(2,0).buy(playerBoard,1));
   }


   @Test
   void isActivatebleTest() throws IOException, InvalidDevelopCardException,
           AbuseOfFaithException, NotEnoughSpaceException, NotBuyableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<>(), new Market(null), developCardDeck);
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
   void produceTestNoExceptions() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, InvalidDevelopCardException, NotBuyableException, NotActivatableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "Mario", new ArrayList<>(), new Market(null), developCardDeck);
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
   void produceCardNotOnTop() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, InvalidDevelopCardException, NotBuyableException, NotActivatableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCardDeck developCardDeck;
      developCardDeck = GSON.cardParser();
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "Mario", new ArrayList<>(), new Market(null), developCardDeck);
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


   //-----------------------------Test with activated LeaderCards in the PlayerBoard--------------------------------------------

   @Test
   void isBuyableWithDiscount() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException, AbuseOfFaithException, NegativeQuantityException {
      DevelopCard devCard1 = new DevelopCard(
              new CardFlag(1,DevelopCardColor.BLUE),
              Map.of(ResourceType.SERVANT, 3, ResourceType.STONE, 1),
              null, null, 0);
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.STONE));
      LeaderCard card2 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.SERVANT));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1, card2)), null, null);


      card1.setActive(playerBoard1);
      card2.setActive(playerBoard1);
      playerBoard1.getChest().addResources(ResourceType.SERVANT,2);
      playerBoard1.getChest().endOfTurnMapsMerge();
      assertTrue(devCard1.isBuyable(playerBoard1));
   }

   @Test
   void buyWithDiscountTest() throws IOException, NotEnoughResourcesException, InvalidLeaderCardException,
           AbuseOfFaithException, NotEnoughSpaceException, NotBuyableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1,DevelopCardColor.BLUE),
              Map.of(ResourceType.GOLD, 3, ResourceType.SHIELD, 1, ResourceType.STONE, 4),
              null, null, 0);
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.STONE));
      LeaderCard card2 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.SHIELD));
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(List.of(card1, card2)), null, null);

      card1.setActive(playerBoard);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getChest().addResources(ResourceType.GOLD, 1);
      playerBoard.getChest().addResources(ResourceType.STONE, 3);
      playerBoard.getChest().endOfTurnMapsMerge();
      try{
         devCard.buy(playerBoard, 1);
      } catch (NullPointerException | InvalidCardSlotException ignored) {
         // card can't remove itself from developCardDeck because developCardDeck is null
      }
      assertEquals(0, playerBoard.getWarehouse().totalResources());
      assertEquals(0, playerBoard.getChest().totalNumberOfResources());
      assertSame(playerBoard.getCardSlots().returnTopCard(1), devCard);
   }

   @Test
   void buyNoResourcesTest() throws IOException, NotEnoughResourcesException, InvalidLeaderCardException,
           AbuseOfFaithException, NotEnoughSpaceException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE),
              Map.of(ResourceType.GOLD, 3, ResourceType.SHIELD, 1, ResourceType.STONE, 4),
              null, null, 0);
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.STONE));
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getChest().addResources(ResourceType.STONE, 3);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertThrows(NotBuyableException.class, () -> devCard.buy(playerBoard, 1));
      assertEquals(3, playerBoard.getWarehouse().totalResources());
      assertEquals(3, playerBoard.getChest().totalNumberOfResources());
      assertEquals(0, playerBoard.getCardSlots().returnTopCard(1).getCardFlag().getLevel());
   }

   @Test
   void buyNoSlotTest() throws IOException, NotEnoughResourcesException, InvalidLeaderCardException,
           AbuseOfFaithException, NotEnoughSpaceException, NegativeQuantityException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(2, DevelopCardColor.BLUE),
              Map.of(ResourceType.GOLD, 3, ResourceType.SHIELD, 1, ResourceType.STONE, 4),
              null, null, 0);
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.STONE));
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 3);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertThrows(NotBuyableException.class, () -> devCard.buy(playerBoard, 1));
   }

   @Test
   void buyLevelTwoTest() throws IOException, NotEnoughResourcesException, InvalidLeaderCardException,
           AbuseOfFaithException, NotEnoughSpaceException, NotBuyableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(2, DevelopCardColor.BLUE),
              Map.of(ResourceType.GOLD, 3, ResourceType.SHIELD, 1, ResourceType.STONE, 4),
              null, null, 0);
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.STONE));
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(List.of(card1)), null, null);


      DevelopCard devCard1 = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE), Map.of(), null, null, 0);
      try {
         devCard1.buy(playerBoard, 1);
      } catch (NullPointerException ignored) {}

      card1.setActive(playerBoard);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 3);
      playerBoard.getChest().endOfTurnMapsMerge();

      try {
         devCard.buy(playerBoard, 1);
      } catch (NullPointerException ignored) {}

      assertSame(devCard, playerBoard.getCardSlots().returnTopCard(1));
   }

   @Test
   void buyWrongLevelTest() throws AbuseOfFaithException, NotEnoughSpaceException, NotEnoughResourcesException,
           InvalidLeaderCardException, IOException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(2, DevelopCardColor.BLUE),
              Map.of(ResourceType.GOLD, 3, ResourceType.SHIELD, 1, ResourceType.STONE, 4),
              null, null, 0);
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.STONE));
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(List.of(card1)), null, null);


      DevelopCard devCard1 = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE), Map.of(), null, null, 0);
      try {
         devCard1.buy(playerBoard, 1);
      } catch (NullPointerException | NotBuyableException | InvalidCardSlotException ignored) {}

      card1.setActive(playerBoard);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 3);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertThrows(InvalidCardSlotException.class, () -> devCard.buy(playerBoard, 2));
      assertEquals(0, playerBoard.getCardSlots().returnTopCard(2).getCardFlag().getLevel());
      assertSame(devCard1, playerBoard.getCardSlots().returnTopCard(1));
      assertEquals(4, playerBoard.getWarehouse().totalResources());
      assertEquals(3, playerBoard.getChest().totalNumberOfResources());
   }

   @Test
   void activatableTest() throws IOException, NotBuyableException, AbuseOfFaithException,
           NotEnoughSpaceException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE), null,
              Map.of(ResourceType.GOLD, 2, ResourceType.STONE, 1), null, 0);
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(), null, null);

      try {
         devCard.buy(playerBoard, 0);
      } catch (NullPointerException ignored) {}

      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 1);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertTrue(devCard.isActivatable(playerBoard));
   }

   @Test
   void activatableNoResourcesTest() throws IOException, NotBuyableException, AbuseOfFaithException,
           NotEnoughSpaceException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE), null,
              Map.of(ResourceType.GOLD, 2, ResourceType.STONE, 1), null, 0);
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(), null, null);

      try {
         devCard.buy(playerBoard, 0);
      } catch (NullPointerException | InvalidCardSlotException ignored) {}

      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);

      assertFalse(devCard.isActivatable(playerBoard));
   }

   @Test
   void activatableNotInSlotTest() throws IOException, AbuseOfFaithException,
           NotEnoughSpaceException, NegativeQuantityException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE), null,
              Map.of(ResourceType.GOLD, 2, ResourceType.STONE, 1), null, 0);
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(), null, null);

      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 1);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertFalse(devCard.isActivatable(playerBoard));
   }

   @Test
   void activatableNotOnTopTest() throws IOException, NotBuyableException, AbuseOfFaithException,
           NotEnoughSpaceException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE), Map.of(),
              Map.of(ResourceType.GOLD, 2, ResourceType.STONE, 1), null, 0);
      DevelopCard devCard2 = new DevelopCard(
              new CardFlag(2, DevelopCardColor.BLUE), Map.of(), null, null, 0);
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(), null, null);

      try {
         devCard.buy(playerBoard, 0);
      } catch (NullPointerException ignored) {}
      try {
         devCard2.buy(playerBoard, 0);
      } catch (NullPointerException ignored) {}

      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 1);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertFalse(devCard.isActivatable(playerBoard));
   }

   @Test
   void produceTest() throws IOException, NotBuyableException, AbuseOfFaithException, NotEnoughSpaceException, NotActivatableException, NegativeQuantityException, InvalidCardSlotException {
      DevelopCard devCard = new DevelopCard(
              new CardFlag(1, DevelopCardColor.BLUE),
              Map.of(),
              Map.of(ResourceType.GOLD, 2, ResourceType.STONE, 1),
              Map.of(ResourceType.FAITH, 1, ResourceType.SERVANT, 2),
              0);
      InterfacePlayerBoard playerBoard = new PlayerBoard(
              "test", new ArrayList<>(), null, null);

      try {
         devCard.buy(playerBoard, 0);
      } catch (NullPointerException ignored) {}

      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getWarehouse().addResource(ResourceType.GOLD);
      playerBoard.getChest().addResources(ResourceType.STONE, 2);
      playerBoard.getChest().endOfTurnMapsMerge();

      int points0 = playerBoard.getTrack().getTrack()[0].getVictoryPoints(),
              points1 = 0,
              position = 0;
      for(int i = 0; i < playerBoard.getTrack().getTrack().length; i++)
         if(playerBoard.getTrack().getTrack()[i].getVictoryPoints() > points0) {
            points1 = playerBoard.getTrack().getTrack()[i].getVictoryPoints();
            position = i;
            break;
         }
      playerBoard.getTrack().moveForward(position - 1);
      assertEquals(points0, playerBoard.getTrack().calculateTrackScore());

      devCard.produce(playerBoard);
      playerBoard.getChest().endOfTurnMapsMerge();

      assertEquals(points1, playerBoard.getTrack().calculateTrackScore());
      assertEquals(0, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));
      assertEquals(1, playerBoard.getChest().getNumberOf(ResourceType.STONE));
      assertEquals(2, playerBoard.getChest().getNumberOf(ResourceType.SERVANT));
   }
}

