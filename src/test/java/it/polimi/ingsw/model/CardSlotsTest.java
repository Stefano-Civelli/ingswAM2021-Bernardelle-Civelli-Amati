package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.modelexceptions.RowOrColumnNotExistsException;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardSlotsTest {

  File cardConfigFile = new File("src/DevelopCardConfig.json");

  @Test
  void emptyCalculateDevelopCardScore() {
    int totalPoints = 0;
    CardSlots cardSlots = new CardSlots();

    assertEquals(totalPoints, cardSlots.calculateDevelopCardScore());
  }

  @Test
  void calculateDevelopCardScore() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    int totalPoints = 0;
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    totalPoints = totalPoints + developCardDeck.getCard(0,0).getVictoryPoints();
    cardSlots.addDevelopCard(1, developCardDeck.getCard(0,3));
    totalPoints = totalPoints + developCardDeck.getCard(0,3).getVictoryPoints();
    cardSlots.addDevelopCard(1, developCardDeck.getCard(1,3));
    totalPoints = totalPoints + developCardDeck.getCard(1,3).getVictoryPoints();
    cardSlots.addDevelopCard(1, developCardDeck.getCard(2,3));
    totalPoints = totalPoints + developCardDeck.getCard(2,3).getVictoryPoints();

    assertEquals(totalPoints, cardSlots.calculateDevelopCardScore());
  }

  @Test //placing the same card in 2 different slots
  void checkSameCardPlacementExceptionTest1() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(1, developCardDeck.getCard(0,0)));
  }

  @Test //placing the same card in 2 different slots
  void checkSameCardPlacementExceptionTest2() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(2,0));
    cardSlots.addDevelopCard(1, developCardDeck.getCard(0,1));
    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0)));
  }

  @Test //placing a lvl 2 card into an empty slot
  void checkInvalidLevelCardPlacementExceptionTest1() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0));
    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(1, developCardDeck.getCard(2,0)));
  }

  @Test //placing a lvl 3 card on top of a lvl 1
  void checkInvalidLevelCardPlacementExceptionTest2() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(0, developCardDeck.getCard(2,0)));
  }

  @Test //placing a lvl 1 card on top of a lvl 2
  void checkInvalidLevelCardPlacementExceptionTest3() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0));

    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(0, developCardDeck.getCard(0,3)));

  }

  @Test //trying to place a lvl 3 card in a slot that is full
  void fullSlotAddDevelopCardTest() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(2,0));
    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(0, developCardDeck.getCard(2,1)));
  }

  @Test //i can check here if notyForEndGame is called cause i've added 7 cards to CardSlots
  void activatableCards() throws IOException, AbuseOfFaithException, RowOrColumnNotExistsException, InvalidCardPlacementException, NotEnoughResourcesException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<>(), Market.getInstance(), developCardDeck);
    playerBoard.getChest().addResources(ResourceType.GOLD,10);
    playerBoard.getChest().addResources(ResourceType.SERVANT,10);
    playerBoard.getChest().addResources(ResourceType.STONE,10);
    playerBoard.getChest().addResources(ResourceType.SHIELD,10);
    playerBoard.getChest().mergeMapResources();

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(2,0));
    cardSlots.addDevelopCard(2, developCardDeck.getCard(0,2));
    cardSlots.addDevelopCard(1, developCardDeck.getCard(0,3));
    cardSlots.addDevelopCard(1, developCardDeck.getCard(1,3));
    cardSlots.addDevelopCard(1, developCardDeck.getCard(2,3));

    for(DevelopCard d : cardSlots.activatableCards(playerBoard))
      assertTrue(d.isActivatable(playerBoard));

    assertTrue(3 >= cardSlots.activatableCards(playerBoard).size());
  }

  @Test
  void returnTopCard() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    CardSlots cardSlots = new CardSlots();
    DevelopCardDeck developCardDeck;
    developCardDeck = GSON.cardParser(cardConfigFile);

    cardSlots.addDevelopCard(0, developCardDeck.getCard(0,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(1,0));
    cardSlots.addDevelopCard(0, developCardDeck.getCard(2,0));
    assertEquals(developCardDeck.getCard(2,0), cardSlots.returnTopCard(0));

    cardSlots.addDevelopCard(1, developCardDeck.getCard(0,2));
    assertEquals(developCardDeck.getCard(0,2), cardSlots.returnTopCard(1));

    cardSlots.addDevelopCard(2, developCardDeck.getCard(0,3));
    assertThrows(InvalidCardPlacementException.class, () -> cardSlots.addDevelopCard(2, developCardDeck.getCard(1,0)));

    cardSlots.addDevelopCard(2, developCardDeck.getCard(1,2));
    assertEquals(developCardDeck.getCard(1,2), cardSlots.returnTopCard(2));
  }

  @Test
  void returnTopCardOfAnEmptySlot() {
    CardSlots cardSlots = new CardSlots();

    assertEquals(0, cardSlots.returnTopCard(2).getCardFlag().getLevel());
  }

  @Test
  void addANullCard() {
    CardSlots cardSlots = new CardSlots();

    assertThrows(NullPointerException.class, () -> cardSlots.addDevelopCard(0,null));
  }
}