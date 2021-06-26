package it.polimi.ingsw.controller.action;

/**
 * All the possible type of action: they are necessary for json serialization
 */
public enum ActionType {

   //if you add a type here you probably should add the name to the actionBuilder in GSON
   PLAYER_DISCONNECTION,
   PLAYER_RECONNECTION,
   SETUP_CHOOSE_RESOURCES,
   SETUP_DISCARD_LEADERS,
   PRODUCE,
   BASE_PRODUCE,
   LEADER_PRODUCE,
   SHOP_MARKET,
   BUY_CARD,
   DISCARD_LEADER,
   END_TURN,
   ACTIVATE_LEADER,
   INSERT_MARBLE,
   CHOOSE_WHITE_LEADER;

   /**
    * static factory method that constructs enum by string
    *
    * @param value string to create the enum
    * @return a new enumeration
    */
   public static ActionType fromValue(String value) {
      for (ActionType actionType : values()) {
         if (actionType.name().equals(value)) {
            return actionType;
         }
      }
      throw new IllegalArgumentException("invalid string value passed: " + value);
   }

}