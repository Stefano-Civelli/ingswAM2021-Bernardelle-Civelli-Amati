package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.*;

public class LeaderCard {

   private boolean active;
   private ResourceType requiredResources;
   private Map<CardFlag,Integer> requiredDevCards;
   private  int victoryPoints;
   //private int warehouseLevel;
   private Consumer<InterfacePlayerBoard> storageBehaviour;
   private Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> producerBehaviour;
   private Supplier<ResourceType> MarbleModifierBehaviour;
   private Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> DiscountBehaviour;

   public LeaderCard(){
   }

   public void activate(){
      active = true;
   }

  public boolean isActive() {
    return active;
  }


   public HashMap<ResourceType, Integer> applyDiscount(HashMap<ResourceType, Integer> mapToDiscount){
      return DiscountBehaviour.apply(mapToDiscount);
   }

   public ResourceType resourceOnWhite(){
      return MarbleModifierBehaviour.get();
   }

   public Integer getVictoryPoints() {
      return victoryPoints;
   }



//sono da mettere in game---------------------------------------------------------
   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> servantDiscount = resources -> {
      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
      if(tempResources.containsKey(ResourceType.SERVANT))
         tempResources.put(ResourceType.SERVANT, resources.get(ResourceType.SERVANT) - 1);
      return tempResources;
   };

   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> goldDiscount = resources -> {
      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
      if(tempResources.containsKey(ResourceType.GOLD))
         tempResources.put(ResourceType.GOLD, resources.get(ResourceType.GOLD) - 1);
      return tempResources;
   };

   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> shieldDiscount = resources -> {
      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
      if(tempResources.containsKey(ResourceType.SHIELD))
         tempResources.put(ResourceType.SHIELD, resources.get(ResourceType.SHIELD) - 1);
      return tempResources;
   };

   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> stoneDiscount = resources -> {
      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
      if(tempResources.containsKey(ResourceType.STONE))
         tempResources.put(ResourceType.STONE, resources.get(ResourceType.STONE) - 1);
      return tempResources;
   };

   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> noDiscount = resources -> {
      return resources;
   };

   Supplier<ResourceType> whiteToServant = () -> { return ResourceType.SERVANT; };
   Supplier<ResourceType> whiteToGold = () -> { return ResourceType.GOLD; };
   Supplier<ResourceType> whiteToStone = () -> { return ResourceType.STONE; };
   Supplier<ResourceType> whiteToShield = () -> { return ResourceType.SHIELD; };
   Supplier<ResourceType> whiteToNothing = () -> { return null; };

}
