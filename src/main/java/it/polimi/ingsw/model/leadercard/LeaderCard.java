package it.polimi.ingsw.model.leadercard;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.HashMap;
import java.util.Map;

public class LeaderCard {

   @Expose(deserialize = false)
   private final int numberOfRequiredResources = 5;
   private boolean active;
   private ResourceType requiredResources; //is null if no resources are required
   private Map<CardFlag,Integer> requiredCardFlags; //is empty if no flags are required
   private  int victoryPoints;
   private CardBehaviour cardBehaviour;

   public LeaderCard(){
   }

   /**
    * if allowed sets the LeaderCard state to active
    * @param playerBoard the player that wants to activate the card
    * @throws NotEnoughResourcesException if the player doesn't have the resources (CardFlags or ResourceType) to activatethe card
    * @throws InvalidLeaderCardException if this card is not
    */
   public void activate(InterfacePlayerBoard playerBoard) throws NotEnoughResourcesException, InvalidLeaderCardException {
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      CardSlots cardSlots = playerBoard.getCardSlots();

      if(!playerBoard.getLeaderCards().contains(this))
         throw new InvalidLeaderCardException("this is not one of your cards");

      for(Map.Entry<CardFlag, Integer> entry : requiredCardFlags.entrySet()){
         if(cardSlots.numberOf(entry.getKey()) < entry.getValue()){
            throw new NotEnoughResourcesException("you need more flags to be able to activate this card");
         }
      }

      if (warehouse.getNumberOf(requiredResources) + chest.getNumberOf(requiredResources) < numberOfRequiredResources)
            throw new NotEnoughResourcesException("you can't activate this card, you need more resources");

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


   public void getProduct(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException, NegativeQuantityException {
      cardBehaviour.produce(resourceToAdd, playerboard);
   }

   public void addStorageSpace(InterfacePlayerBoard playerBoard) throws MaxLeaderCardLevelsException, LevelAlreadyPresentException {
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
