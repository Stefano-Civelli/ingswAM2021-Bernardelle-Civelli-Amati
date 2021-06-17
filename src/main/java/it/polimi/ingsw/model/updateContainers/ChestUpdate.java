package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.ResourceType;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class ChestUpdate {
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
