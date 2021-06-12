package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.MarbleModifierBehaviour;
import it.polimi.ingsw.model.leadercard.StorageBehaviour;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.model.market.RedMarble;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

  Market market = new Market(null);

  PlayerBoard initializer() throws IOException {
    String usr = "talla";
    DevelopCardDeck developCardDeck = GSON.cardParser();
    List<LeaderCard> leaderCards = new ArrayList<>();
    leaderCards.add(new LeaderCard(1,null, null, 4, new MarbleModifierBehaviour(ResourceType.SERVANT)));
    leaderCards.add(new LeaderCard(2,null, null, 6, new MarbleModifierBehaviour(ResourceType.SHIELD)));
    leaderCards.add(new LeaderCard(3,null, null, 4, new StorageBehaviour(ResourceType.SERVANT)));
    leaderCards.add(new LeaderCard(4,null, null, 4, new StorageBehaviour(ResourceType.GOLD)));

    return new PlayerBoard(usr, leaderCards, market, developCardDeck);
  }

  @Test
  void calculateTotalScoreOnlyTrackTest() throws IOException {
    PlayerBoard playerBoard = initializer();
    playerBoard.getTrack().moveForward(8);
    assertEquals(playerBoard.returnScore(), 6);
  }

  @Test
  void calculateTotalScoreOnlyCardSlotsTest() throws IOException, InvalidDevelopCardException, InvalidCardPlacementException,
          NotBuyableException, NegativeQuantityException, AbuseOfFaithException, NotEnoughResourcesException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso

    playerBoard.getChest().addResources(ResourceType.GOLD, 99);
    playerBoard.getChest().addResources(ResourceType.STONE, 99);
    playerBoard.getChest().addResources(ResourceType.SHIELD, 99);
    playerBoard.getChest().addResources(ResourceType.SERVANT, 99);
    playerBoard.getChest().endOfTurnMapsMerge();

    sum += playerBoard.getDevelopCardDeck().getCard(0,0).getVictoryPoints();
    playerBoard.addDevelopCard(0,0, 0);

    playerBoard.getChest().removeResources(ResourceType.GOLD, playerBoard.getChest().getNumberOf(ResourceType.GOLD));
    playerBoard.getChest().removeResources(ResourceType.STONE, playerBoard.getChest().getNumberOf(ResourceType.STONE));
    playerBoard.getChest().removeResources(ResourceType.SHIELD, playerBoard.getChest().getNumberOf(ResourceType.SHIELD));
    playerBoard.getChest().removeResources(ResourceType.SERVANT, playerBoard.getChest().getNumberOf(ResourceType.SERVANT));

    assertEquals(playerBoard.returnScore(), sum);

    playerBoard.getChest().addResources(ResourceType.GOLD, 99);
    playerBoard.getChest().addResources(ResourceType.STONE, 99);
    playerBoard.getChest().addResources(ResourceType.SHIELD, 99);
    playerBoard.getChest().addResources(ResourceType.SERVANT, 99);
    playerBoard.getChest().endOfTurnMapsMerge();

    sum += playerBoard.getDevelopCardDeck().getCard(1,0).getVictoryPoints();
    playerBoard.addDevelopCard(1,0, 0);

    playerBoard.getChest().removeResources(ResourceType.GOLD, playerBoard.getChest().getNumberOf(ResourceType.GOLD));
    playerBoard.getChest().removeResources(ResourceType.STONE, playerBoard.getChest().getNumberOf(ResourceType.STONE));
    playerBoard.getChest().removeResources(ResourceType.SHIELD, playerBoard.getChest().getNumberOf(ResourceType.SHIELD));
    playerBoard.getChest().removeResources(ResourceType.SERVANT, playerBoard.getChest().getNumberOf(ResourceType.SERVANT));

    assertEquals(playerBoard.returnScore(), sum);

    playerBoard.getChest().addResources(ResourceType.GOLD, 99);
    playerBoard.getChest().addResources(ResourceType.STONE, 99);
    playerBoard.getChest().addResources(ResourceType.SHIELD, 99);
    playerBoard.getChest().addResources(ResourceType.SERVANT, 99);
    playerBoard.getChest().endOfTurnMapsMerge();

    sum += playerBoard.getDevelopCardDeck().getCard(0,3).getVictoryPoints();
    playerBoard.addDevelopCard(0,3, 2);

    playerBoard.getChest().removeResources(ResourceType.GOLD, playerBoard.getChest().getNumberOf(ResourceType.GOLD));
    playerBoard.getChest().removeResources(ResourceType.STONE, playerBoard.getChest().getNumberOf(ResourceType.STONE));
    playerBoard.getChest().removeResources(ResourceType.SHIELD, playerBoard.getChest().getNumberOf(ResourceType.SHIELD));
    playerBoard.getChest().removeResources(ResourceType.SERVANT, playerBoard.getChest().getNumberOf(ResourceType.SERVANT));

    assertEquals(playerBoard.returnScore(), sum);
  }

  @Test
  void calculateTotalScoreOnlyAddingRemainingResourcesTest() throws IOException, NegativeQuantityException,
          AbuseOfFaithException, NotEnoughSpaceException {
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

    chest.addResources(ResourceType.SHIELD, 5);
    chest.endOfTurnMapsMerge();
    assertEquals(playerBoard.returnScore(), 3);

    warehouse.addResource(ResourceType.GOLD);
    warehouse.addResource(ResourceType.GOLD);
    assertThrows(NotEnoughSpaceException.class , () -> warehouse.addResource(ResourceType.GOLD));
    assertEquals(playerBoard.returnScore(), 3);

    warehouse.addResource(ResourceType.SHIELD);
    warehouse.addResource(ResourceType.SHIELD);
    assertEquals(playerBoard.returnScore(), 3);

    warehouse.addResource(ResourceType.STONE);
    assertEquals(playerBoard.returnScore(), 4);
  }

  @Test
  void calculateTotalScoreAlsoRemovingRemainingResourcesTest() throws IOException, NegativeQuantityException,
          AbuseOfFaithException, NotEnoughSpaceException, NotEnoughResourcesException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso
    Chest chest = playerBoard.getChest();
    Warehouse warehouse = playerBoard.getWarehouse();

    chest.addResources(ResourceType.GOLD, 1);
    chest.addResources(ResourceType.SHIELD, 1);
    chest.addResources(ResourceType.SERVANT, 1);
    chest.endOfTurnMapsMerge();
    assertEquals(playerBoard.returnScore(), 1);

    warehouse.addResource(ResourceType.GOLD);
    warehouse.addResource(ResourceType.GOLD);
    assertEquals(playerBoard.returnScore(), 2);

    chest.removeResources(ResourceType.SERVANT, 1);
    assertEquals(playerBoard.returnScore(), 1);
  }


  @Test
  void calculateTotalScoreOnlyLeaderCardTest() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException {
    PlayerBoard playerBoard = initializer();
    int sum = 1; //viene considerato anche il punto 1 del track non mosso

    playerBoard.getLeaderCards().get(0).setActive(playerBoard);
    sum += playerBoard.getLeaderCards().get(0).getVictoryPoints();

    playerBoard.getLeaderCards().get(1).setActive(playerBoard);
    sum += playerBoard.getLeaderCards().get(1).getVictoryPoints();

    assertEquals(playerBoard.returnScore(), sum);
  }

  @Test
  void emptyCalculateTotalScoreTest() throws IOException {
    PlayerBoard playerBoard = initializer();
    assertEquals(1, playerBoard.returnScore());
  }

  @Test
  void shopMarketRowTest() throws IOException, RowOrColumnNotExistsException {
    PlayerBoard playerBoard = initializer();
    List<MarketMarble> support = new ArrayList<>();
    int row = 2;

    for(int i=0; i<market.getNumberOfColumn(); i++)
      support.add(market.getStatus()[row][i]);

    assertEquals(support, playerBoard.shopMarketRow(row));
    assertThrows(RowOrColumnNotExistsException.class, () -> playerBoard.shopMarketRow(row+1));
  }

  @Test
  void shopMarketColumnTest() throws IOException, RowOrColumnNotExistsException {
    PlayerBoard playerBoard = initializer();
    List<MarketMarble> support = new ArrayList<>();
    int column = 2;

    for(int i=0; i<market.getNumberOfRow(); i++)
      support.add(market.getStatus()[i][column]);

    assertEquals(support, playerBoard.shopMarketColumn(column));
    assertThrows(RowOrColumnNotExistsException.class, () -> playerBoard.shopMarketRow(-1));
  }

  @Test
  void baseProductionTest() throws IOException, NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException,
          NotEnoughResourcesException, AlreadyProducedException, NeedAResourceToAddException {
    PlayerBoard playerBoard = initializer();
    Chest chest = playerBoard.getChest();
    Warehouse warehouse = playerBoard.getWarehouse();

    warehouse.addResource(ResourceType.GOLD);
    warehouse.addResource(ResourceType.GOLD);
    warehouse.addResource(ResourceType.STONE);
    warehouse.addResource(ResourceType.SHIELD);

    playerBoard.baseProduction(ResourceType.GOLD, ResourceType.STONE, ResourceType.SERVANT);
    chest.endOfTurnMapsMerge();
    assertEquals(warehouse.totalResources(), 2);
    assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 1);
    assertEquals(chest.getNumberOf(ResourceType.SERVANT), 1);
  }

  @Test
  void baseProductionWithChestRemovingTest() throws IOException, NotEnoughSpaceException, AbuseOfFaithException,
          NegativeQuantityException, NotEnoughResourcesException, AlreadyProducedException, NeedAResourceToAddException {
    PlayerBoard playerBoard = initializer();
    Chest chest = playerBoard.getChest();
    Warehouse warehouse = playerBoard.getWarehouse();

    warehouse.addResource(ResourceType.GOLD);
    warehouse.addResource(ResourceType.STONE);
    warehouse.addResource(ResourceType.SHIELD);
    chest.addResources(ResourceType.GOLD, 3);
    chest.endOfTurnMapsMerge();

    playerBoard.baseProduction(ResourceType.GOLD, ResourceType.GOLD, ResourceType.SERVANT);
    chest.endOfTurnMapsMerge();
    assertEquals(warehouse.totalResources(), 2);
    assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 0);
    assertEquals(chest.getNumberOf(ResourceType.SERVANT), 1);
    assertEquals(chest.getNumberOf(ResourceType.GOLD), 2);
  }

  @Test
  void addMarbleToWarehouseTest() throws IOException, RowOrColumnNotExistsException, NotEnoughSpaceException,
          MoreWhiteLeaderCardsException, MarbleNotExistException {
    PlayerBoard playerBoard = initializer();

    playerBoard.shopMarketRow(1);
    assertEquals(4, playerBoard.getTempMarketMarble().size());

    playerBoard.addMarbleToWarehouse(0);
    assertEquals(3, playerBoard.getTempMarketMarble().size());

    playerBoard.addMarbleToWarehouse(2);
    assertThrows(MarbleNotExistException.class, () -> playerBoard.addMarbleToWarehouse(2));
    assertEquals(2, playerBoard.getTempMarketMarble().size());

    playerBoard.addMarbleToWarehouse(1);
    try {
      playerBoard.addMarbleToWarehouse(0);
    } catch (NotEnoughSpaceException ignored) {}
    assertEquals(0, playerBoard.getTempMarketMarble().size());
  }

  @Test
  void NotEnoughSpaceExceptionTest() throws IOException, RowOrColumnNotExistsException, NotEnoughSpaceException,
          InvalidLeaderCardException, NotEnoughResourcesException, AbuseOfFaithException,
          MoreWhiteLeaderCardsException, MarbleNotExistException {

    PlayerBoard playerBoard = initializer();
    playerBoard.getLeaderCards().get(0).setActive(playerBoard);
    playerBoard.getWarehouse().addResource(ResourceType.GOLD);
    playerBoard.getWarehouse().addResource(ResourceType.GOLD);
    playerBoard.getWarehouse().addResource(ResourceType.GOLD);
    playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
    playerBoard.getWarehouse().addResource(ResourceType.SHIELD);
    playerBoard.getWarehouse().addResource(ResourceType.STONE);

    playerBoard.shopMarketColumn(1);

    if(playerBoard.getTempMarketMarble().get(2) instanceof RedMarble)
      playerBoard.addMarbleToWarehouse(2);
    else
      assertThrows(NotEnoughSpaceException.class, () -> playerBoard.addMarbleToWarehouse(2));

    if(playerBoard.getTempMarketMarble().get(1) instanceof RedMarble)
      playerBoard.addMarbleToWarehouse(1);
    else
      assertThrows(NotEnoughSpaceException.class, () -> playerBoard.addMarbleToWarehouse(1));

    if(playerBoard.getTempMarketMarble().get(0) instanceof RedMarble)
      playerBoard.addMarbleToWarehouse(0);
    else
      assertThrows(NotEnoughSpaceException.class, () -> playerBoard.addMarbleToWarehouse(0));
  }

  @Test
  void discardLeaderAtBeginningTest() throws IOException, InvalidLeaderCardException {
    PlayerBoard playerBoard = initializer();
    List<LeaderCard> support = new ArrayList<>(playerBoard.getLeaderCards());

    playerBoard.discardLeaderAtBegin(support.get(2).getLeaderId(), support.get(0).getLeaderId());
    assertFalse(playerBoard.getLeaderCards().contains(support.get(2)));
    assertFalse(playerBoard.getLeaderCards().contains(support.get(0)));
    assertEquals(playerBoard.getLeaderCards().size(), 2);

  }

  @Test
  void discardLeaderDuringTheGameTest() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException, LeaderIsActiveException {
    PlayerBoard playerBoard = initializer();
    List<LeaderCard> support = new ArrayList<>(playerBoard.getLeaderCards());

    playerBoard.discardLeaderAtBegin(support.get(2).getLeaderId(), support.get(0).getLeaderId());

    playerBoard.getLeaderCards().get(0).setActive(playerBoard);
    assertThrows(LeaderIsActiveException.class, () -> playerBoard.discardLeader(playerBoard.getLeaderCards().get(0).getLeaderId()));
    playerBoard.discardLeader(playerBoard.getLeaderCards().get(1).getLeaderId());
    assertEquals(1, playerBoard.getLeaderCards().size());
  }
}