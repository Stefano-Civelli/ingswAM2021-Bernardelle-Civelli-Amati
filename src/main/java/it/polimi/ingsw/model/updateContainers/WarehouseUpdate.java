package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.ResourceType;

public class WarehouseUpdate {
   private final ResourceType resourceType;
   private final int quantity;
   private final int level;

   public WarehouseUpdate(ResourceType resourceType, int quantity, int level) {
      this.resourceType = resourceType;
      this.quantity = quantity;
      this.level = level;
   }

   public ResourceType getResourceType() {
      return resourceType;
   }

   public int getQuantity() {
      return quantity;
   }

   public int getLevel() {
      return level;
   }
}
