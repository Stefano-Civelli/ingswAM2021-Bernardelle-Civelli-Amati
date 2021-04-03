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


  public Integer getVictoryPoints() {
    return victoryPoints;
  }

  public ResourceType resourceOnWhite(){
    return MarbleModifierBehaviour.get();
  }
}
