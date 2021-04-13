package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {


   @Test
   void setActiveCardYouDontHave() throws IOException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new StorageBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", null, null, null);

      assertThrows(InvalidLeaderCardException.class, () -> card1.setActive(playerBoard1));

   }

   @Test
   void setActiveCardYouHave() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new StorageBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      assertTrue(card1.isActive());
   }

   @Test
   void setActiveNotEnoughResources() throws IOException {
      LeaderCard card1 = new LeaderCard(
              ResourceType.SERVANT, null, 0, new StorageBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      assertThrows(NotEnoughResourcesException.class, () -> card1.setActive(playerBoard1));

   }

   @Test
   void setActiveNotEnoughFlags() throws IOException {
      Map<CardFlag,Integer> requiredCardFlags = Map.of(
              new CardFlag(1,DevelopCardColor.BLUE), 1);
      LeaderCard card1 = new LeaderCard(
              null, requiredCardFlags, 0, new StorageBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      assertThrows(NotEnoughResourcesException.class, () -> card1.setActive(playerBoard1));

   }

   @Test
   void getVictoryPointsTest() {
      LeaderCard card1 = new LeaderCard(
              ResourceType.SERVANT, null, 2, new StorageBehaviour(ResourceType.GOLD));
      assertEquals(card1.getVictoryPoints(),2);
   }

   @Test
   void applyDiscountTest() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.GOLD));

      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      HashMap<ResourceType, Integer>  mapToDiscount = new HashMap<>();
      mapToDiscount.put(ResourceType.GOLD,2);
      mapToDiscount.put(ResourceType.SHIELD,1);

      card1.applyDiscount(mapToDiscount);
      assertEquals(mapToDiscount.get(ResourceType.GOLD),1);
      assertEquals(mapToDiscount.get(ResourceType.SHIELD),1);
      card1.applyDiscount(mapToDiscount);
      assertFalse(mapToDiscount.containsKey(ResourceType.GOLD));

   }

   @Test
   void applyDiscountTest2() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new DiscountBehaviour(ResourceType.SERVANT));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      HashMap<ResourceType, Integer>  mapToDiscount = new HashMap<>();
      mapToDiscount.put(ResourceType.GOLD,2);
      mapToDiscount.put(ResourceType.SHIELD,1);

      card1.applyDiscount(mapToDiscount);
      assertEquals(mapToDiscount.get(ResourceType.GOLD),2);
      assertEquals(mapToDiscount.get(ResourceType.SHIELD),1);

   }

   @Test
   void resourceOnWhiteServantError() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      assertEquals(card1.resourceOnWhite(),ResourceType.GOLD);
   }

   @Test
   void getProductTest1() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException, AbuseOfFaithException, NotEnoughSpaceException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new LeaderProduceBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      playerBoard1.getWarehouse().addResource(ResourceType.GOLD);
      card1.getProduct(ResourceType.SERVANT, playerBoard1);
      assertEquals(playerBoard1.getTrack().calculateTrackScore(),1);
      playerBoard1.getChest().endOfTurnMapsMerge();
      assertEquals(playerBoard1.getChest().getNumberOf(ResourceType.SERVANT),1);


   }

   @Test
   void getProductNotEnoughResources() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException, AbuseOfFaithException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new LeaderProduceBehaviour(ResourceType.GOLD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      assertThrows(NotEnoughResourcesException.class, () -> card1.getProduct(ResourceType.SERVANT, playerBoard1));


   }

   @Test
   void getProductTestProduceFaith() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException, NotEnoughSpaceException, AbuseOfFaithException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new LeaderProduceBehaviour(ResourceType.SHIELD));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      playerBoard1.getWarehouse().addResource(ResourceType.SHIELD);
      assertThrows(AbuseOfFaithException.class, () -> card1.getProduct(ResourceType.FAITH,playerBoard1));

   }


   @Test
   void addStorageSpaceTest() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException, MaxLeaderCardLevelsException, AbuseOfFaithException, LevelAlreadyPresentException, NotEnoughSpaceException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new StorageBehaviour(ResourceType.STONE));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      card1.addStorageSpace(playerBoard1);
      //add 6 resources of the same type to see if it throws an exception (it shouldn't)
      for(int i = 0; i<5; i++)
         playerBoard1.getWarehouse().addResource(ResourceType.STONE);
   }

   @Test
   void addStorageSpaceTest2() throws IOException, InvalidLeaderCardException, NotEnoughResourcesException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new StorageBehaviour(ResourceType.FAITH));
      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1)), null, null);

      card1.setActive(playerBoard1);
      assertThrows(AbuseOfFaithException.class, () -> card1.addStorageSpace(playerBoard1));

   }

   @Test
   void defaultImplementationsTest() throws IOException, AbuseOfFaithException, NotEnoughResourcesException, MaxLeaderCardLevelsException, LevelAlreadyPresentException, InvalidLeaderCardException {
      LeaderCard card1 = new LeaderCard(
              null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
      LeaderCard card2 = new LeaderCard(
              null, null, 0, new StorageBehaviour(ResourceType.STONE));

      InterfacePlayerBoard playerBoard1 = new PlayerBoard(
              "Mario", new ArrayList<>(List.of(card1, card2)), null, null);

      card1.setActive(playerBoard1);
      card2.setActive(playerBoard1);

      HashMap<ResourceType, Integer>  mapToDiscount = new HashMap<>();
      mapToDiscount.put(ResourceType.GOLD,2);
      mapToDiscount.put(ResourceType.SHIELD,1);
      HashMap<ResourceType, Integer> mapToDiscountClone = new HashMap<>(mapToDiscount);
      card1.addStorageSpace(playerBoard1);
      card1.getProduct(ResourceType.SERVANT,playerBoard1);
      card1.applyDiscount(mapToDiscount);

      assertEquals(mapToDiscount, mapToDiscountClone);
      assertNull(card2.resourceOnWhite());
   }
}
