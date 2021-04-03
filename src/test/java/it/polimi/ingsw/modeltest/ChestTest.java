package it.polimi.ingsw.modeltest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.ResourceType;
import org.junit.jupiter.api.Test;

class ChestTest {
  @Test
  public void voidRemoveTest()
  {
    boolean pippo = false;
    Chest chest = new Chest();
    try {
      chest.removeResources(ResourceType.GOLD, 2);
    }
    catch (Exception e){pippo = true;}
    assertTrue( pippo );
  }

  @Test
  public void totalResourcesTest(){
    Chest chest = new Chest();
    try {
      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.STONE, 3);
      chest.addResources(ResourceType.SERVANT, 4);
      chest.addResources(ResourceType.SHIELD, 5);
      chest.addResources(ResourceType.SHIELD, 5);

    }
    catch (Exception e){}
    assertEquals(chest.totalNumberOfResources(),19);
  }

  @Test
  public void totalResourcesTest2(){
    Chest chest = new Chest();
    try {
      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.STONE, 3);
      chest.addResources(ResourceType.SERVANT, 4);
      chest.addResources(ResourceType.SHIELD, 5);
      chest.addResources(ResourceType.SHIELD, 5);
      chest.removeResources(ResourceType.GOLD, 1);

    }
    catch (Exception e){}
    assertEquals(chest.totalNumberOfResources(),18);

  }


  @Test
  public void exceptionSubtraction()
  {
    boolean pippo = false;
    Chest chest = new Chest();
    try {
      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.removeResources(ResourceType.GOLD, 3);
    }
    catch (Exception e){pippo = true;}
    assertTrue( pippo );
  }


  @Test
  public void exceptionSubtraction2()
  {
    boolean pippo = false;
    Chest chest = new Chest();
    try {
      chest.addResources(ResourceType.GOLD, 2);
      chest.addResources(ResourceType.SHIELD, 4);
      chest.removeResources(ResourceType.GOLD, 2);
    }
    catch (Exception e){pippo = true;}
    assertFalse( pippo );
  }
}