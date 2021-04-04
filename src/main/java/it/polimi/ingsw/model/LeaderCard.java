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

  //TODO decidere se farlo con tipo di ritorno oppure no
   public void applyDiscount(HashMap<ResourceType, Integer> costMapToDiscount){
      DiscountBehaviour.apply(costMapToDiscount);
   }

   public ResourceType resourceOnWhite(){
      return MarbleModifierBehaviour.get();
   }

   public Integer getVictoryPoints() {
      return victoryPoints;
   }

//
////sono da mettere in game---------------------------------------------------------
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> servantDiscount = resources -> {
//      if(resources.containsKey(ResourceType.SERVANT))
//         resources.put(ResourceType.SERVANT, resources.get(ResourceType.SERVANT) - 1);
//   };
//
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> noDiscount = resources -> {
//      if(resources.containsKey(ResourceType.SERVANT))
//         resources.put(ResourceType.SERVANT, resources.get(ResourceType.SERVANT) - 1);
//   };
//
//   Supplier<ResourceType> whiteToServant = () -> { return ResourceType.SERVANT; };
//   Supplier<ResourceType> whiteToGold = () -> { return ResourceType.GOLD; };
//   Supplier<ResourceType> whiteToStone = () -> { return ResourceType.STONE; };
//   Supplier<ResourceType> whiteToShield = () -> { return ResourceType.SHIELD; };
//   Supplier<ResourceType> whiteToNothing = () -> { return null; };
//
}
