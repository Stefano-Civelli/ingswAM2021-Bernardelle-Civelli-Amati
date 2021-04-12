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
   public HashMap<ResourceType, Integer> discount(HashMap<ResourceType, Integer> resources){
      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
      if(tempResources.containsKey(resourceToDiscount))
         tempResources.replace(resourceToDiscount, resources.get(resourceToDiscount) - 1);
      return tempResources;
   }

}