package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.CardBehaviour;
import it.polimi.ingsw.model.leadercard.MarbleModifierBehaviour;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.*;

public class LeaderCard {

   private boolean active;
   private ResourceType requiredResources;
   private Map<CardFlag,Integer> requiredCardFlags;
   private  int victoryPoints;
   //private int warehouseLevel;
   //private Supplier<ResourceType> marbleModifierBehaviour;
//   private Consumer<InterfacePlayerBoard> storageBehaviour;
//   private Supplier<HashMap<ResourceType, Integer>> producerBehaviour;
//   private Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> DiscountBehaviour;
   private CardBehaviour cardBehaviour;

   public LeaderCard(){
   }

  public void activate(){
      active = true;
   }

  public boolean isActive() {
    return active;
  }


   public HashMap<ResourceType, Integer> applyDiscount(HashMap<ResourceType, Integer> mapToDiscount){
      //return DiscountBehaviour.apply(mapToDiscount);
      return new HashMap<>();
   }

   public ResourceType resourceOnWhite(){
      return cardBehaviour.getOnWhite();
   }

   public void getProduct(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException {
      cardBehaviour.produce(resourceToAdd, playerboard);
   }

   public Integer getVictoryPoints() {
      return victoryPoints;
   }

   //TODO cancellarla, è solo per testare
   public void printRequiredCardFlags(){
      for(Map.Entry<CardFlag,Integer> e : requiredCardFlags.entrySet()){
         System.out.println(e.getKey().getLevel());
         System.out.println(e.getKey().getColor().toString());
         System.out.println(e.getValue());
      }
   }

////sono da mettere in game---------------------------------------------------------
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> servantDiscount = resources -> {
//      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
//      if(tempResources.containsKey(ResourceType.SERVANT))
//         tempResources.put(ResourceType.SERVANT, resources.get(ResourceType.SERVANT) - 1);
//      return tempResources;
//   };
//
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> goldDiscount = resources -> {
//      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
//      if(tempResources.containsKey(ResourceType.GOLD))
//         tempResources.put(ResourceType.GOLD, resources.get(ResourceType.GOLD) - 1);
//      return tempResources;
//   };
//
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> shieldDiscount = resources -> {
//      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
//      if(tempResources.containsKey(ResourceType.SHIELD))
//         tempResources.put(ResourceType.SHIELD, resources.get(ResourceType.SHIELD) - 1);
//      return tempResources;
//   };
//
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> stoneDiscount = resources -> {
//      HashMap<ResourceType, Integer> tempResources = new HashMap<>(resources);
//      if(tempResources.containsKey(ResourceType.STONE))
//         tempResources.put(ResourceType.STONE, resources.get(ResourceType.STONE) - 1);
//      return tempResources;
//   };
//
//   Function<HashMap<ResourceType, Integer>, HashMap<ResourceType, Integer>> noDiscount = resources -> {
//      return resources;
//   };
//
//   Supplier<ResourceType> whiteToServant = () -> { return ResourceType.SERVANT; };
//   Supplier<ResourceType> whiteToGold = () -> { return ResourceType.GOLD; };
//   Supplier<ResourceType> whiteToStone = () -> { return ResourceType.STONE; };
//   Supplier<ResourceType> whiteToShield = () -> { return ResourceType.SHIELD; };
//   Supplier<ResourceType> whiteToNothing = () -> { return null; };


   //Supplier<HashMap<ResourceType, Integer>>

}
