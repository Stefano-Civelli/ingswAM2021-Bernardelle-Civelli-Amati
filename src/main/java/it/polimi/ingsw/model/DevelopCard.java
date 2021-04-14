package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DevelopCard {
   private  CardFlag cardFlag;
   private  HashMap<ResourceType, Integer> cost;
   private  HashMap<ResourceType, Integer> requirement;
   private  HashMap<ResourceType, Integer> product;
   private  int victoryPoints;

   public DevelopCard(CardFlag cardFlag, Map<ResourceType, Integer> cost, Map<ResourceType, Integer> requirement, Map<ResourceType, Integer> product, int victoryPoints) {
      this.cardFlag = cardFlag;
      this.cost = cost != null ? new HashMap<>(cost) : new HashMap<>();
      this.requirement = requirement != null ? new HashMap<>(requirement) : new HashMap<>();
      this.product = product != null ? new HashMap<>(product) : new HashMap<>();
      this.victoryPoints = victoryPoints;
   }


   /**
    * Checks if the player passed as a parameter can buy the card (this)
    *
    * @param playerBoard that wants to know if he can buy the card
    * @return true if the card can be bought, false otherwise
    */
   public boolean isBuyable(InterfacePlayerBoard playerBoard){
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      CardSlots cardSlots = playerBoard.getCardSlots();
      DevelopCardDeck developCardDeck = playerBoard.getDevelopCardDeck();
      HashMap<ResourceType, Integer> localCost = new HashMap<>(cost);
      //apply discount
      for(LeaderCard l : playerBoard.getLeaderCards())
         l.applyDiscount(localCost);

      //check if the card is visible
      if(!developCardDeck.visibleCards().contains(this))
         return false;

      //check if the number of resources is sufficient
      for(Map.Entry<ResourceType, Integer> entry : localCost.entrySet())
         if (warehouse.getNumberOf(entry.getKey()) + chest.getNumberOf(entry.getKey()) < entry.getValue())
            return false;

      //check if there is a lower level card so that the new one can be put on top
      for(int i=0; i<cardSlots.getNumberOfCardSlots(); i++)
         if (cardSlots.returnTopCard(i).getCardFlag().getLevel() == this.getCardFlag().getLevel() - 1)
            return true;

      return false;
   }

   /**
    * Checks if the player passed as a parameter can activate the card (this)
    *
    * @param playerBoard that wants to know if he can activate the card
    * @return true if the card can be activated, false if it can't
    */
   public boolean isActivatable(InterfacePlayerBoard playerBoard){
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      CardSlots cardSlots = playerBoard.getCardSlots();

      for(Map.Entry<ResourceType, Integer> entry : requirement.entrySet()){
         //check if the number of resources is sufficient
         if(warehouse.getNumberOf(entry.getKey()) + chest.getNumberOf(entry.getKey()) < entry.getValue())
            return false;
      }

      //check if the card is on top of a card slot
      for(int i=0; i<cardSlots.getNumberOfCardSlots(); i++)
         if (cardSlots.returnTopCard(i).equals(this))
            return true;

      return false;
   }

   /**
    * handles the buying operation from start to finish.
    * Removes the resources from the Player's warehouse and adds the new card to the specified CardSlot
    * //TODO problema: potrebbe lanciare eccezione dopo aver rimosso le risorse dalla chest. potrei fare che questo metodo ritorna la carta che poi viene aggiunta allo slot nella playerboard.
    *
    * @param playerBoard that wants to buy the card
    * @param cardSlotNumber number of the slot to put the new card in. (starts at 0)
    * @throws InvalidCardPlacementException if the card can't be put in the specified slot
    * @throws NotBuyableException if the card can't be bought
    */
   public void buy(InterfacePlayerBoard playerBoard, int cardSlotNumber) throws InvalidCardPlacementException, NotBuyableException {
      CardSlots cardslots = playerBoard.getCardSlots();
      DevelopCardDeck developCardDeck = playerBoard.getDevelopCardDeck();
      HashMap<ResourceType, Integer> localCost = new HashMap<>(cost);
      if(!this.isBuyable(playerBoard))
         throw new NotBuyableException("you are trying to buy a card you cannot buy");

      //apply discount
      for(LeaderCard l : playerBoard.getLeaderCards())
         l.applyDiscount(localCost);

      try {
         removeResourcesFrom(localCost, playerBoard.getWarehouse(), playerBoard.getChest());
      } catch (NegativeQuantityException | NotEnoughResourcesException e) {
         e.printStackTrace();
      }

      //TODO fix this problem
      cardslots.addDevelopCard(cardSlotNumber,this);

      try {
         developCardDeck.removeCard(this);
      } catch (InvalidCardException e) {
         throw new NotBuyableException("you can't buy this card because it's not a visible card in the deck");
      }

   }

   /**
    * activate the card production
    * @param playerBoard that wants to activate the card
    * @throws NotActivatableException if the card is not activatable by the specified player
    */
   public void produce(InterfacePlayerBoard playerBoard) throws NotActivatableException {
      if(!this.isActivatable(playerBoard))
         throw new NotActivatableException("you can't activate this card");

      try {
         removeResourcesFrom(requirement, playerBoard.getWarehouse(), playerBoard.getChest());
      } catch (NegativeQuantityException | NotEnoughResourcesException e) {
         e.printStackTrace();
      }

      for(Map.Entry<ResourceType, Integer> entry : product.entrySet()) {
         try {
            if(entry.getKey().equals(ResourceType.FAITH))
               playerBoard.getTrack().moveForward(entry.getValue());
            else
               playerBoard.getChest().addResources(entry.getKey(),entry.getValue());
         } catch (AbuseOfFaithException | NegativeQuantityException e) {
            e.printStackTrace();
         }
      }

   }

   public CardFlag getCardFlag(){
      return cardFlag;
   }

   public int getVictoryPoints(){
      return victoryPoints;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this)
         return true;
      if (!(obj instanceof DevelopCard))
         return false;
      DevelopCard d = (DevelopCard) obj;
      return (this.cardFlag.equals(d.cardFlag)) && (this.cost.equals(d.cost)) &&
              (this.victoryPoints == d.victoryPoints) && (this.product.equals(d.product)) &&
              (this.requirement.equals(d.requirement));
   }

   private void removeResourcesFrom(HashMap<ResourceType, Integer> target, Warehouse warehouse, Chest chest) throws NegativeQuantityException, NotEnoughResourcesException {
      for(Map.Entry<ResourceType, Integer> entry : target.entrySet()) {
         int remainingToRemove = warehouse.removeResources(entry.getKey(),entry.getValue());
         try {
            chest.removeResources(entry.getKey(), remainingToRemove);
         }catch(AbuseOfFaithException e){
            e.printStackTrace();
         }
      }
   }
}