package it.polimi.ingsw.controller;


import it.polimi.ingsw.network.Action.ActionType;

import java.util.List;

import static it.polimi.ingsw.network.Action.ActionType.*;

public enum PhaseType {

   SETUP_CHOOSERESOURCES(CHOSE_RESOURCES),
   SETUP_DISCARDLEADER(DISCARD_LEADER),
   INITIAL(List.of(PRODUCE, BASE_PRODUCE, LEADER_PRODUCE, SHOP_MARKET, BUY_CARD, DISCARD_LEADER, ACTIVATE_LEADER)),
   SHOPPING(List.of(PLACE_MARBLE, CHOOSE_WHITE_LEADER)),
   PRODUCING(List.of(PRODUCE, BASE_PRODUCE, LEADER_PRODUCE, DISCARD_LEADER, ACTIVATE_LEADER, END_TURN)),
   FINAL(List.of(DISCARD_LEADER, ACTIVATE_LEADER, END_TURN));

   private final List<ActionType> availableActionsList;

   PhaseType(ActionType a){
      availableActionsList = List.of(a);
   }

   PhaseType(List<ActionType> l){
      availableActionsList = l;
   }

   public boolean isValid(ActionType actionType) {
      return this.availableActionsList.contains(actionType);
   }

}
