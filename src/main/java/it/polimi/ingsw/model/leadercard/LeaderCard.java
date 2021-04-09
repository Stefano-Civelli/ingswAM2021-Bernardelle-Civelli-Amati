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
   private CardBehaviour cardBehaviour;

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


   public HashMap<ResourceType, Integer> applyDiscount(HashMap<ResourceType, Integer> mapToDiscount){
      return cardBehaviour.discount(mapToDiscount);
   }

   /**
    *  can return null
    * @return
    */
   public ResourceType resourceOnWhite(){
      return cardBehaviour.getOnWhite();
   }


   public void getProduct(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException {
      cardBehaviour.produce(resourceToAdd, playerboard);
   }

   public void addStorageSpace(InterfacePlayerBoard playerBoard){
      cardBehaviour.createStorage(playerBoard);
   }

   //TODO cancellarla, è solo per testare
   public void printRequiredCardFlags(){
      for(Map.Entry<CardFlag,Integer> e : requiredCardFlags.entrySet()){
         System.out.println(e.getKey().getLevel());
         System.out.println(e.getKey().getColor().toString());
         System.out.println(e.getValue());
      }
   }
}
