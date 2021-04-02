package it.polimi.ingsw.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DevelopCard {
   //removed final to eneble JSON parsing
   private  CardFlag cardFlag;
   private  HashMap<ResourceType, Integer> cost;
   private  HashMap<ResourceType, Integer> requirement;
   private  HashMap<ResourceType, Integer> product;
   private  int victoryPoints;

   public DevelopCard(CardFlag cardFlag, HashMap<ResourceType, Integer> cost, HashMap<ResourceType, Integer> requirement, HashMap<ResourceType, Integer> product, int victoryPoints) {
      this.cardFlag = cardFlag;
      this.cost = new HashMap<>(cost);
      this.requirement = requirement;
      this.product = product;
      this.victoryPoints = victoryPoints;
   }
   public DevelopCard(){

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
      //check if the number of resources is sufficient
      for(Map.Entry<ResourceType, Integer> entry : cost.entrySet())
         if (warehouse.getNumberOf(entry.getKey()) + chest.getNumberOf(entry.getKey()) < entry.getValue())
            return false;

      //check if there is a lower level card so that the new one can be put on top
      boolean foundSuitableSlot = false;
      for(int i=1; i<=cardSlots.getNumberOfCardSlots() && !foundSuitableSlot; i++)
         if (cardSlots.returnTopCard(i).getCardFlag().getLevel() == this.getCardFlag().getLevel() - 1)
            foundSuitableSlot = true;

      return foundSuitableSlot;
   }

   /**
    * Checks if the player passed as a parameter can activate the card (this)
    * @param playerBoard the player that wants to know if the card can be activated
    * @return true if the card can be activated, false if it can't
    */
   public boolean isActivatable(InterfacePlayerBoard playerBoard){
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      for(Map.Entry<ResourceType, Integer> entry : requirement.entrySet()){
         //check if the number of resources is sufficient
         if(warehouse.getNumberOf(entry.getKey()) + chest.getNumberOf(entry.getKey()) < entry.getValue()) {
            return false;
         }

      }
      return true;
   }

   public CardFlag getCardFlag(){
      return cardFlag;
   }

   public HashMap<ResourceType, Integer> getCost(){
      return new HashMap<>(cost);
   }

   public HashMap<ResourceType, Integer> getProduct(){
      return new HashMap<>(product);
   }

   public int getVictoryPoints(){
      return victoryPoints;
   }

   public HashMap<ResourceType, Integer> getRequirement() {
      return requirement;
   }
}
