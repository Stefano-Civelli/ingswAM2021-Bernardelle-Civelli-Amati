package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;

import java.util.HashMap;
import java.util.Map;

public class DevelopCard {
   private  CardFlag cardFlag;
   private  HashMap<ResourceType, Integer> cost;
   private  HashMap<ResourceType, Integer> requirement;
   private  HashMap<ResourceType, Integer> product;
   private  int victoryPoints;

   public DevelopCard(){
   }

   //TODO controllare se è na roba legale
   public DevelopCard(int level){
      this.cardFlag = new CardFlag(level, null);
   }

   /**
    * Checks if the player passed as a parameter can buy the card (this)
    * @param playerBoard the player that wants to know if the card can be bought
    * @return true if the card can be bought, false if it can't
    */
   public boolean isBuyable(InterfacePlayerBoard playerBoard){
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      CardSlots cardSlots = playerBoard.getCardSlots();
      DevelopCardDeck developCardDeck = playerBoard.getDevelopCardDeck();

      //check if the card is visible
      if(!developCardDeck.visibleCards().contains(this))
         return false;

      //check if the number of resources is sufficient
      for(Map.Entry<ResourceType, Integer> entry : cost.entrySet())
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
    * @param playerBoard the player that wants to know if the card can be activated
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

   public void buy(InterfacePlayerBoard playerBoard, int cardSlotNumber) throws InvalidCardPlacementException, NotEnoughResourcesException{
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      CardSlots cardslots = playerBoard.getCardSlots();
      if(!this.isBuyable(playerBoard))
         throw new NotEnoughResourcesException("this card is not buyable");

      for(Map.Entry<ResourceType, Integer> entry : requirement.entrySet()){
         int remainingToRemove = warehouse.removeResources(entry.getKey(),entry.getValue());
         try {
            chest.removeResources(entry.getKey(), remainingToRemove);
         }catch(AbuseOfFaithException e){}
      }

      cardslots.addDevelopCard(cardSlotNumber,this);
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

}
