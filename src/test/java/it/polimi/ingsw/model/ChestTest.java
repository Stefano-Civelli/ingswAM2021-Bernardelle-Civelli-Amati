package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;
import org.junit.jupiter.api.Test;

class ChestTest {
   @Test
   void voidRemoveTest() {
      Chest chest = new Chest();

      assertThrows(NotEnoughResourcesException.class, () -> chest.removeResources(ResourceType.GOLD, 2));
   }

   @Test
   void voidTotalResourceTest() {
      Chest chest = new Chest();

      assertEquals(chest.totalNumberOfResources(),0);
      assertEquals(chest.getNumberOf(ResourceType.SHIELD),0);
      assertEquals(chest.getNumberOf(ResourceType.STONE),0);
      assertEquals(chest.getNumberOf(ResourceType.SERVANT),0);
      assertEquals(chest.getNumberOf(ResourceType.GOLD),0);
   }

   @Test
   void totalResourcesTest() {
      Chest chest = new Chest();
      try {
         chest.addResources(ResourceType.GOLD, 2);
         chest.addResources(ResourceType.STONE, 3);
         chest.addResources(ResourceType.SERVANT, 4);
         chest.addResources(ResourceType.SHIELD, 5);
         chest.addResources(ResourceType.SHIELD, 5);
      }
      catch (AbuseOfFaithException | NotEnoughResourcesException e){e.getMessage();}
      chest.mergeMapResources();
      assertEquals(chest.totalNumberOfResources(),19);
   }

   @Test
   void totalResourcesTest2() {
      Chest chest = new Chest();
      try {
         chest.addResources(ResourceType.GOLD, 2);
         chest.addResources(ResourceType.STONE, 3);
         chest.addResources(ResourceType.SERVANT, 4);
         chest.addResources(ResourceType.SHIELD, 5);
         chest.addResources(ResourceType.SHIELD, 5);
         chest.mergeMapResources();
         chest.removeResources(ResourceType.GOLD, 1);
      }
      catch (Exception e){e.getMessage();}
      assertEquals(chest.totalNumberOfResources(),18);
      assertEquals(chest.getNumberOf(ResourceType.GOLD), 1);
   }

   @Test
   void exceptionSubtraction() throws AbuseOfFaithException, NotEnoughResourcesException {

      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.mergeMapResources();
      assertThrows(NotEnoughResourcesException.class, () -> chest.removeResources(ResourceType.GOLD, 3));

   }

   @Test
   void exceptionSubtraction2() {
      boolean flag = false;
      Chest chest = new Chest();
      try {
         chest.addResources(ResourceType.GOLD, 2);
         chest.addResources(ResourceType.SHIELD, 4);
         chest.mergeMapResources();
         chest.removeResources(ResourceType.GOLD, 2);
      }
      catch (Exception e){flag = true;}
      assertFalse( flag );
   }

   @Test
   void abuseOfFaithExceptionTest() throws AbuseOfFaithException, NotEnoughResourcesException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      assertThrows(AbuseOfFaithException.class, () -> chest.addResources(ResourceType.FAITH, 4));
      chest.mergeMapResources();
      assertThrows(AbuseOfFaithException.class, () -> chest.removeResources(ResourceType.FAITH, 1));
   }

   @Test
   void removeZeroResourcesTest() throws AbuseOfFaithException, NotEnoughResourcesException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.mergeMapResources();
      chest.removeResources(ResourceType.GOLD, 0);

      assertEquals(chest.getNumberOf(ResourceType.GOLD), 2);
      assertEquals(chest.getNumberOf(ResourceType.SHIELD), 4);
   }

   @Test
   void removeNegativeQuantity() throws AbuseOfFaithException, NotEnoughResourcesException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.mergeMapResources();
      assertThrows(NotEnoughResourcesException.class, () -> chest.removeResources(ResourceType.GOLD, -10));
   }

   @Test
   void addNegativeQuantity() throws AbuseOfFaithException, NotEnoughResourcesException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.mergeMapResources();
      assertThrows(NotEnoughResourcesException.class, () -> chest.addResources(ResourceType.GOLD, -10));
      assertEquals(chest.getNumberOf(ResourceType.GOLD), 2);
   }
}