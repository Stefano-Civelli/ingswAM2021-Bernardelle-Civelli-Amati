package it.polimi.ingsw.model.updatecontainers;

import it.polimi.ingsw.model.ResourceType;

/**
 * A model update represents an update happened in a {@link it.polimi.ingsw.model.Warehouse warehouse}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class WarehouseUpdate implements ModelUpdate {
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
