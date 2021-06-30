package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.ResourceType;

import java.util.HashMap;

/**
 * The CardBehaviour that represents the discount behaviour: a leader card with this behaviour will reduce the develop card price
 */
public class DiscountBehaviour extends CardBehaviour{

   private final ResourceType resourceToDiscount;

   public DiscountBehaviour(ResourceType resourceToDiscount) {
      this.resourceToDiscount = resourceToDiscount;
   }

   @Override
   public void discount(HashMap<ResourceType, Integer> resources){
      if(resources.containsKey(resourceToDiscount)){
         if(resources.get(resourceToDiscount) == 1)
            resources.remove(resourceToDiscount);
         else
            resources.replace(resourceToDiscount, resources.get(resourceToDiscount) - 1);
   }
   }

   @Override
   public ResourceType getResourceToDiscount() {
      return resourceToDiscount;
   }
}
