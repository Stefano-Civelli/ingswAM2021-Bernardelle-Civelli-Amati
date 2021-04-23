package it.polimi.ingsw.network.Action;

public enum ActionType {

   MARKET_SETUP,
   MARKET_UPDATE,
   CHOSE_RESOURCES,
   PRODUCE,
   BASE_PRODUCE,
   LEADER_PRODUCE,
   SHOP_MARKET,
   BUY_CARD,
   DISCARD_LEADER,
   END_TURN,
   ACTIVATE_LEADER,
   PLACE_MARBLE,
   CHOOSE_WHITE_LEADER;

   private String typeOfMessage;

   ActionType(String typeOfMessage) {
      this.typeOfMessage = typeOfMessage;
   }

   ActionType() {
   }

   public String toString() {
      return typeOfMessage;
   }
}