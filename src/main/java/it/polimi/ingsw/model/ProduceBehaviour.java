package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelexceptions.*;

import java.util.HashMap;
import java.util.Map;

public class ProduceBehaviour implements IStartBehaviour{
  private Map<ResourceType, Integer> tempResources;
  private Chest chest;
  private Warehouse warehouse;

  public ProduceBehaviour(InterfacePlayerBoard player, DevelopCard developCard){
    tempResources = new HashMap<>(developCard.getProduct());
    this.chest = player.getChest();
    this.warehouse = player.getWarehouse();
  }

  //TODO
  @Override
  public void useResource(ResourceType resource, boolean warehouse, int level) {
    if(warehouse) {
      try {
        this.warehouse.removeResources(resource, level, 1);
      } catch (NotEnoughResourcesException e) {
        e.printStackTrace();
      } catch (IncorrectResourceTypeException e) {
        e.printStackTrace();
      } catch (LevelNotExistsException e) {
        e.printStackTrace();
      } catch (NegativeQuantityException e) {
        e.printStackTrace();
      }

      tempResources.compute(resource, (k,v) -> (v-1 == 0) ? tempResources.remove(k): v-1);
    }
    else {
      try {
        this.chest.removeResources(resource, 1);
      } catch (MissingResourceToRemoveException e) {
        e.printStackTrace();
      } catch (NotEnoughResourcesException e) {
        e.printStackTrace();
      } catch (AbuseOfFaithException e) {
        e.printStackTrace();
      }

      tempResources.compute(resource, (k,v) -> (v-1 == 0) ? tempResources.remove(k): v-1);
    }
    return;
  }
}
