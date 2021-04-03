package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class BuyBehaviour implements IStartBehaviour{
    private Map<ResourceType, Integer> tempResources;
    private Chest chest;
    private Warehouse warehouse;
    private DevelopCard developCard; //per me non serve salvarcelo in locale

    public BuyBehaviour(InterfacePlayerBoard player, DevelopCard developCard){
      tempResources = new HashMap<>(developCard.getRequirement());
      this.chest = player.getChest();
      this.warehouse = player.getWarehouse();
    }

    //TODO
    @Override
    public void useResource(ResourceType resource, boolean warehouse, int level) {
      return;
    }
}
