package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.ResourceType;

public class ChestUpdate {
   private final ResourceType resourceType;
   private final int quantity;

   public ChestUpdate(ResourceType resourceType, int quantity) {
      this.resourceType = resourceType;
      this.quantity = quantity;
   }

   public ResourceType getResourceType() {
      return resourceType;
   }

   public int getQuantity() {
      return quantity;
   }


}
