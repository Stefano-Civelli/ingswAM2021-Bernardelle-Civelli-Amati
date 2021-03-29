package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class DevelopCard {

   private final CardFlag cardFlag;
   private final HashMap<ResourceType, Integer> cost;
   private final HashMap<ResourceType, Integer> requirement;
   private final HashMap<ResourceType, Integer> product;
   private final int vicoryPoints;

   public DevelopCard(CardFlag cardFlag, HashMap<ResourceType, Integer> cost, HashMap<ResourceType, Integer> requirement, HashMap<ResourceType, Integer> product, int victoryPoints) {
      this.cardFlag = cardFlag;
      this.cost = new HashMap<>(cost);
      this.requirement = requirement;
      this.product = product;
      this.vicoryPoints = victoryPoints;
   }


//   //TODO
//   public Resources getCost(){
//      return null;
//   }


   //TODO
   public void produce(Chest chest, Warehouse warehouse ){
      return;
   }

   //public void buy(Warehouse w, Chest c, CardSlot[] slotArray, int slot)
   public int getVictoryPoints(){
      return vicoryPoints;

   }


   /**
    * Checks if the player passed as a parameter can buy the card (this)
    * @param playerBoard the player that wants to know if the card can be bought
    * @return true if the card can be bought, false if it can't
    */
   public boolean isBuyable(PlayerBoard playerBoard){
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      for(Map.Entry<ResourceType, Integer> entry : cost.entrySet()){
         //controlla se la somma tra magazzino e chest è sufficiente
         if(warehouse.getNumberOf(entry.getKey()) + chest.getNumberOf(entry.getKey()) < entry.getValue()) {
            return false;
         }

      }
      return true;
   }


   /**
    * Checks if the player passed as a parameter can activate the card (this)
    * @param playerBoard the player that wants to know if the card can be activated
    * @return true if the card can be activated, false if it can't
    */
   public boolean isActivatable(PlayerBoard playerBoard){
      Warehouse warehouse = playerBoard.getWarehouse();
      Chest chest = playerBoard.getChest();
      for(Map.Entry<ResourceType, Integer> entry : requirement.entrySet()){
         //controlla se la somma tra magazzino e chest è sufficiente
         if(warehouse.getNumberOf(entry.getKey()) + chest.getNumberOf(entry.getKey()) < entry.getValue()) {
            return false;
         }

      }
      return true;
   }


   public void buy(PlayerBoard playerBoard){
      //deve anche rimuoversi dal deck la carta
   }


   public CardFlag getCardFlag(){
      return cardFlag;
   }
}
