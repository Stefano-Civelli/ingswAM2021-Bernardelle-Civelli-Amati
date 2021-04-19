package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

  File cardConfigFile = new File("src/DevelopCardConfig.json");

  PlayerBoard initializer() throws IOException {
    String usr = "talla";
    Market market = new Market();
    DevelopCardDeck developCardDeck = GSON.cardParser(cardConfigFile);
    List<LeaderCard> leaderCards = new ArrayList<>();

    return new PlayerBoard(usr, leaderCards, market, developCardDeck);
  }

  @Test
  void calculateTotalScoreOnlyTrackTest() throws IOException {
    PlayerBoard playerBoard = initializer();
    playerBoard.getTrack().moveForward(8);
    assertEquals(playerBoard.returnScore(), 6);
  }

  @Test
  void calculateTotalScoreOnlyCardSlotsTest() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso

    sum += playerBoard.getDevelopCardDeck().getCard(0,0).getVictoryPoints();
    playerBoard.addDevelopCard(0,0, 0);
    assertEquals(playerBoard.returnScore(), sum);

    sum += playerBoard.getDevelopCardDeck().getCard(1,0).getVictoryPoints();
    playerBoard.addDevelopCard(1,0, 0);
    assertEquals(playerBoard.returnScore(), sum);

    sum += playerBoard.getDevelopCardDeck().getCard(0,3).getVictoryPoints();
    playerBoard.addDevelopCard(0,3, 2);
    assertEquals(playerBoard.returnScore(), sum);
  }

//  @Test
//  void calculateTotalScoreOnlyLeaderCardTest() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException, InvalidLeaderCardException,
//          NotEnoughResourcesException, NegativeQuantityException, AbuseOfFaithException, NotEnoughSpaceException {
//    PlayerBoard playerBoard = initializer();
//    int sum = 1; //viene considerato anche il punto 1 del track non mosso
//    Chest chest = playerBoard.getChest();
//    Warehouse warehouse = playerBoard.getWarehouse();
//
//    chest.addResources(ResourceType.GOLD, 9);
//    chest.addResources(ResourceType.SHIELD, 9);
//    chest.addResources(ResourceType.SERVANT, 9);
//    chest.addResources(ResourceType.STONE, 9);
//    warehouse.addResource(ResourceType.GOLD);
//    playerBoard.getCardSlots()
//
//    sum += playerBoard.getLeaderCards().get(0).getVictoryPoints();
//    playerBoard.getLeaderCards().get(0).setActive(playerBoard);
//
//  }

  @Test
  void calculateTotalScoreOnlyAddingRemainingResourcesTest() throws IOException, NegativeQuantityException, AbuseOfFaithException, NotEnoughSpaceException, NotEnoughResourcesException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso
    Chest chest = playerBoard.getChest();
    Warehouse warehouse = playerBoard.getWarehouse();

    chest.addResources(ResourceType.GOLD, 1);
    chest.addResources(ResourceType.SHIELD, 1);
    chest.addResources(ResourceType.SERVANT, 1);
    chest.endOfTurnMapsMerge();
    warehouse.addResource(ResourceType.GOLD);
    assertEquals(playerBoard.returnScore(), 1);

    chest.addResources(ResourceType.SERVANT, 1);
    chest.endOfTurnMapsMerge();
    assertEquals(playerBoard.returnScore(), 2);

//    chest.addResources(ResourceType.SHIELD, 3);
//    chest.endOfTurnMapsMerge();
//    assertEquals(playerBoard.returnScore(), 2);//non capisco perché faccia 3 -> con wharehouse worka (guarda sotto) -> risolvere problema in chest

    warehouse.addResource(ResourceType.GOLD);
    warehouse.addResource(ResourceType.GOLD);
    assertThrows(NotEnoughSpaceException.class , () -> warehouse.addResource(ResourceType.GOLD));
    assertEquals(playerBoard.returnScore(), 2);
    warehouse.addResource(ResourceType.SHIELD);
    warehouse.addResource(ResourceType.SHIELD);
    assertEquals(playerBoard.returnScore(), 2);
    warehouse.addResource(ResourceType.STONE);
    assertEquals(playerBoard.returnScore(), 3);
  }

  @Test
  void calculateTotalScoreAlsoRemovingRemainingResourcesTest() throws IOException, NegativeQuantityException, AbuseOfFaithException, NotEnoughSpaceException, NotEnoughResourcesException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso
    Chest chest = playerBoard.getChest();
    Warehouse warehouse = playerBoard.getWarehouse();

    chest.addResources(ResourceType.GOLD, 1);
    chest.addResources(ResourceType.SHIELD, 1);
    chest.addResources(ResourceType.SERVANT, 1);
    chest.endOfTurnMapsMerge();
    chest.removeResources(ResourceType.SERVANT, 1);
    assertEquals(playerBoard.returnScore(), 1);


  }

  @Test
  void calculateTotalScoreTest() throws IOException, RowOrColumnNotExistsException, InvalidCardPlacementException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso
    playerBoard.getTrack().moveForward(8);
    sum = playerBoard.getTrack().calculateTrackScore();
    assertEquals(playerBoard.returnScore(), 6);

    sum += playerBoard.getDevelopCardDeck().getCard(0,0).getVictoryPoints();
    playerBoard.addDevelopCard(0,0, 0);
    assertEquals(playerBoard.returnScore(), sum);
  }
}