package it.polimi.ingsw.model.updatecontainers;

import it.polimi.ingsw.model.ResourceType;

/**
 * A model update represents an update happened in a {@link it.polimi.ingsw.model.Chest chest}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class ChestUpdate implements ModelUpdate {

   private final ResourceType resourceType;
   private final int quantity;

   public ChestUpdate(ResourceType resourceType, int quantity) {
      this.resourceType = resourceType;
      this.quantity = quantity;
   }

   /**
    * returns the resource type that has changed quantity
    * @return the resource type that has changed quantity
    */
   public ResourceType getResourceType() {
      return resourceType;
   }

   /**
    * returns the new quantity value of the updated resource type
    * @return the new quantity value of the updated resource type
    */
   public int getQuantity() {
      return quantity;
   }

}
