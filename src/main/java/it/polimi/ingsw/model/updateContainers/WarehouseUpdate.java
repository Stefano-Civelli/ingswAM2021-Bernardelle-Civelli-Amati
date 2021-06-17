package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.ResourceType;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class WarehouseUpdate {
   private final ResourceType resourceType;
   private final int quantity;
   private final int level;

   public WarehouseUpdate(ResourceType resourceType, int quantity, int level) {
      this.resourceType = resourceType;
      this.quantity = quantity;
      this.level = level;
   }

   /**
    * returns the resource that has been changed
    * @return the resource that has been changed
    */
   public ResourceType getResourceType() {
      return resourceType;
   }

   /**
    * returns the new quantity of the changed resource
    * @return the new quantity of the changed resource
    */
   public int getQuantity() {
      return quantity;
   }

   /**
    * returns the level in which the change occurred
    * @return the level in which the change occurred
    */
   public int getLevel() {
      return level;
   }
}
