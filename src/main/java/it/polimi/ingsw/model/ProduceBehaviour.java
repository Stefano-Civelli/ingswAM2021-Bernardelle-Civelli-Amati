package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class ProduceBehaviour implements IStartBehaviour{
  private Map<ResourceType, Integer> tempResources;
  private Chest chest;
  private Warehouse warehouse;
  private DevelopCard developCard; //per me non serve salvarcelo in locale

  public ProduceBehaviour(InterfacePlayerBoard player, DevelopCard developCard){
    tempResources = new HashMap<>(developCard.getProduct());
    this.chest = player.getChest();
    this.warehouse = player.getWarehouse();
  }

  //TODO
  @Override
  public void useResource(ResourceType resource, boolean warehouse, int level) {
    return;
  }
}
