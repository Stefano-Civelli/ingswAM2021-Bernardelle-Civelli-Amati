package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.ResourceType;

import java.util.HashMap;

public class DiscountBehaviour extends CardBehaviour{

   private ResourceType resourceToDiscount;

   public DiscountBehaviour() {}

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

}
