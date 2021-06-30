package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a leader card
 */
public class LeaderCard {

   @SuppressWarnings({"FieldCanBeLocal"})
   private transient final int numberOfRequiredResources = 5;
   private int leaderId;
   @SuppressWarnings({"unused"}) // Because the field is necessary for JSON serialization and it may be accessed using reflection
   private String image;
   private boolean active;
   private final ResourceType requiredResources; //is null if no resources are required
   private final Map<CardFlag,Integer> requiredCardFlags; //is empty if no flags are required
   private final int victoryPoints;
   private final CardBehaviour cardBehaviour;

   /**
    * class constructor
    *
    * @param requiredResources resource type required to buy the card. Can be {@code null}
    * @param requiredCardFlags DevelopCard's cardFlags required to buy the card. Can be {@code null}
    * @param victoryPoints card's points
    * @param cardBehaviour object containing the card's behaviour (abilities)
    */
   public LeaderCard(int ID, ResourceType requiredResources, Map<CardFlag, Integer> requiredCardFlags, int victoryPoints, CardBehaviour cardBehaviour) {
     this.leaderId = ID;
     this.active = false;
      this.requiredResources = requiredResources;
      this.requiredCardFlags = (requiredCardFlags != null) ? new HashMap<>(requiredCardFlags) : new HashMap<>();
      this.victoryPoints = victoryPoints;
      this.cardBehaviour = cardBehaviour;
   }

  public LeaderCard(ResourceType requiredResources, Map<CardFlag, Integer> requiredCardFlags, int victoryPoints, CardBehaviour cardBehaviour) {
    this.active = false;
    this.requiredResources = requiredResources;
    this.requiredCardFlags = (requiredCardFlags != null) ? new HashMap<>(requiredCardFlags) : new HashMap<>();
    this.victoryPoints = victoryPoints;
    this.cardBehaviour = cardBehaviour;
  }

   /**
    * if it's allowed sets the LeaderCard state to active
    *
    * @param playerBoard the player that wants to activate the card
    * @throws NotEnoughResourcesException if the player doesn't have the resources (CardFlags or ResourceType) to activate the card
    * @throws InvalidLeaderCardException if the player doesn't own this card
    */
   public void setActive(InterfacePlayerBoard playerBoard) throws NotEnoughResourcesException, InvalidLeaderCardException {
      if(active)
         return;

      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      CardSlots cardSlots = playerBoard.getCardSlots();

       if (requiredResources != null && warehouse.getNumberOf(requiredResources) + chest.getNumberOf(requiredResources) < numberOfRequiredResources)
           throw new NotEnoughResourcesException("you can't activate this card, you need more resources");

      try {
         this.addStorageSpace(playerBoard);
      } catch (MaxLeaderCardLevelsException e) {
         e.printStackTrace();
      }

      if(!playerBoard.getLeaderCards().contains(this))
         throw new InvalidLeaderCardException("this is not one of your cards");

      for(Map.Entry<CardFlag, Integer> entry : requiredCardFlags.entrySet()){
         if(cardSlots.numberOf(entry.getKey()) < entry.getValue()){
            throw new NotEnoughResourcesException("you need more flags to be able to activate this card");
         }
      }

      active = true;
   }

   /**
    * returns true if this is an active card in the player's playerboard
    *
    * @return true if this card is active
    */
   public boolean isActive() {
    return active;
  }

   public int getLeaderId() {
      return leaderId;
   }

   public String getImage() {
       return this.image;
   }

   /**
    * get the number of victory points
    *
    * @return the number of this card's victory points
    */
   public int getVictoryPoints() {
      return victoryPoints;
   }

   /**
    * applies discount to the map passed as a parameter
    * the method modifies the map  passed as a parameter in order to apply the discount
    * does nothing if the card is not active or doesn't have this ability
    */
   public void applyDiscount(HashMap<ResourceType, Integer> mapToDiscount){
      if(this.isActive())
         cardBehaviour.discount(mapToDiscount);

   }

   /**
    *  the ResourceType in which to convert the white marble that comes from a market
    *
    * @return the ResourceType in which to convert the white marble or {@code null} if the card is not active or doesn't have this ability
    */
   public ResourceType resourceOnWhite() {
      if(this.isActive())
         return cardBehaviour.getOnWhite();
      else
         return null;
   }

   /**
    * activates the production ability
    * does nothing if the card is not active or doesn't have this ability
    *
    * @param resourceToAdd the resource that will be produced (FAITH not allowed)
    * @param playerBoard that wants to activate this ability (needed to add and remove resources directly from his storages)
    * @throws AbuseOfFaithException if you pass {@code ResourceType.FAITH} as the resource you want to add
    * @throws NotEnoughResourcesException if the player doesn't have the resources to activate the production
    */
   public void leaderProduce(ResourceType resourceToAdd, InterfacePlayerBoard playerBoard) throws AbuseOfFaithException, NotEnoughResourcesException, NeedAResourceToAddException {
      if(this.isActive())
         cardBehaviour.produce(resourceToAdd, playerBoard);
   }

   /**
    * activates the additional storage space ability
    * does nothing if the card is not active or doesn't have this ability
    *
    * @param playerBoard that wants to activate a Storage type LeaderCard
    * @throws MaxLeaderCardLevelsException if the maximum number of additional storages is surpassed
    */
   public void addStorageSpace(InterfacePlayerBoard playerBoard) throws MaxLeaderCardLevelsException {
         cardBehaviour.createStorage(playerBoard);
   }

  public ResourceType getResToDiscount() {
     return cardBehaviour.getResourceToDiscount();
   }

  public ResourceType getProductionRequirement() {
    return cardBehaviour.getProductionRequirement();
  }

  public ResourceType getWhite() {
    return cardBehaviour.getOnWhite();
  }

  public ResourceType getResToStore() {
    return cardBehaviour.getResToStore();
  }

  public ResourceType getRequiredResources() {
    return requiredResources;
  }

  public Map<CardFlag, Integer> getRequiredCardFlags() {
    return new HashMap<>(requiredCardFlags);
  }

}
