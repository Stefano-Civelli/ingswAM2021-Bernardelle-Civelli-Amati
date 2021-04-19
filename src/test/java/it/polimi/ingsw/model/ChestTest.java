package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NegativeQuantityException;
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
   void totalResourcesTest() throws AbuseOfFaithException, NegativeQuantityException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.STONE, 3);
      chest.addResources(ResourceType.SERVANT, 4);
      chest.addResources(ResourceType.SHIELD, 5);
      chest.addResources(ResourceType.SHIELD, 5);

      chest.endOfTurnMapsMerge();
      assertEquals(chest.totalNumberOfResources(),19);
   }

   @Test
   void totalResourcesTest2() throws AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.STONE, 3);
      chest.addResources(ResourceType.SERVANT, 4);
      chest.addResources(ResourceType.SHIELD, 5);
      chest.addResources(ResourceType.SHIELD, 5);
      chest.endOfTurnMapsMerge();
      chest.removeResources(ResourceType.GOLD, 1);

      assertEquals(chest.totalNumberOfResources(),18);
      assertEquals(chest.getNumberOf(ResourceType.GOLD), 1);
   }

   @Test
   void exceptionSubtraction() throws AbuseOfFaithException, NegativeQuantityException {

      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.endOfTurnMapsMerge();
      assertThrows(NotEnoughResourcesException.class, () -> chest.removeResources(ResourceType.GOLD, 3));

   }

   @Test
   void exceptionSubtraction2() throws AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.endOfTurnMapsMerge();
      chest.removeResources(ResourceType.GOLD, 2);

      assertEquals(chest.getNumberOf(ResourceType.GOLD), 0);
   }


   @Test
   void removeZeroResourcesTest() throws AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.endOfTurnMapsMerge();
      chest.removeResources(ResourceType.GOLD, 0);

      assertEquals(chest.getNumberOf(ResourceType.GOLD), 2);
      assertEquals(chest.getNumberOf(ResourceType.SHIELD), 4);
   }

   @Test
   void removeNegativeQuantity() throws AbuseOfFaithException, NegativeQuantityException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.endOfTurnMapsMerge();
      assertThrows(NotEnoughResourcesException.class, () -> chest.removeResources(ResourceType.GOLD, -10));
   }

   @Test
   void addNegativeQuantity() throws AbuseOfFaithException, NegativeQuantityException {
      Chest chest = new Chest();

      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.endOfTurnMapsMerge();
      assertEquals(chest.getNumberOf(ResourceType.GOLD), 2);
   }
}