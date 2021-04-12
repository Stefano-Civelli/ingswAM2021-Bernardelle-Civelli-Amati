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

   public LeaderCard(ResourceType requiredResources, Map<CardFlag, Integer> requiredCardFlags, int victoryPoints, CardBehaviour cardBehaviour) {
      this.active = false;
      this.requiredResources = requiredResources;
      this.requiredCardFlags = requiredCardFlags != null? new HashMap<>(requiredCardFlags) : new HashMap<>();
      this.victoryPoints = victoryPoints;
      this.cardBehaviour = cardBehaviour;
   }

   /**
    * if it's allowed sets the LeaderCard state to active
    * @param playerBoard the player that wants to activate the card
    * @throws NotEnoughResourcesException if the player doesn't have the resources (CardFlags or ResourceType) to activatethe card
    * @throws InvalidLeaderCardException if this card is not
    */
   public void setActive(InterfacePlayerBoard playerBoard) throws NotEnoughResourcesException, InvalidLeaderCardException {
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

      if(requiredResources != null)
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
      if(this.isActive())
         return cardBehaviour.discount(mapToDiscount);
      else
         return new HashMap<>(mapToDiscount);
   }

   /**
    *  can return null
    * @return
    */
   public ResourceType resourceOnWhite(){
      if(this.isActive())
         return cardBehaviour.getOnWhite();
      else
         return null;
   }


   public void getProduct(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException, NegativeQuantityException {
      if(this.isActive())
         cardBehaviour.produce(resourceToAdd, playerboard);
   }

   public void addStorageSpace(InterfacePlayerBoard playerBoard) throws MaxLeaderCardLevelsException, LevelAlreadyPresentException, AbuseOfFaithException {
      if(this.isActive())
         cardBehaviour.createStorage(playerBoard);
   }

}
