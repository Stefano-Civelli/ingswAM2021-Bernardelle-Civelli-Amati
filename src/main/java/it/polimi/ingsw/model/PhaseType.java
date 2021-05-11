package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.action.ActionType;

import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.controller.action.ActionType.*;

/**
 * class that represents possible turn phases.
 * Each phase has a list of the valid action during that phase
 */
public enum PhaseType {

   SETUP_CHOOSERESOURCES(List.of(CHOSE_RESOURCES), true),
   SETUP_DISCARDLEADER(List.of(DISCARD_LEADER), true),
   END_SETUP(true,true),
   INITIAL(List.of(PRODUCE, BASE_PRODUCE, LEADER_PRODUCE, SHOP_MARKET, BUY_CARD, DISCARD_LEADER, ACTIVATE_LEADER)),
   SHOPPING(List.of(INSERT_MARBLE)),
   SHOPPING_LEADER(List.of(CHOOSE_WHITE_LEADER)),
   PRODUCING(List.of(PRODUCE, BASE_PRODUCE, LEADER_PRODUCE, DISCARD_LEADER, ACTIVATE_LEADER, END_TURN)),
   FINAL(List.of(DISCARD_LEADER, ACTIVATE_LEADER, END_TURN)),
   END(true);

   private final boolean isTmp;
   private final List<ActionType> availableActionsList;
   private final boolean isSetup;

   PhaseType() {
      this.isTmp = false;
      this.availableActionsList = List.of();
      this.isSetup = false;
   }

   PhaseType(boolean isTmp) {
      this.isTmp = isTmp;
      this.availableActionsList = List.of();
      this.isSetup = false;
   }

   PhaseType(boolean isTmp, boolean isSetup) {
      this.isTmp = isTmp;
      this.availableActionsList = List.of();
      this.isSetup = isSetup;
   }

   PhaseType(List<ActionType> l){
      this.isTmp = false;
      this.availableActionsList = l;
      this.isSetup = false;
   }

   PhaseType(boolean isTmp, List<ActionType> l){
      this.isTmp = isTmp;
      this.availableActionsList = l;
      this.isSetup = false;
   }

   PhaseType(List<ActionType> l, boolean isSetup) {
      this.isTmp = false;
      this.availableActionsList = l;
      this.isSetup = isSetup;
   }

   PhaseType(boolean isTmp, List<ActionType> l, boolean isSetup) {
      this.isTmp = isTmp;
      this.availableActionsList = l;
      this.isSetup = isSetup;
   }

   /**
    * static factory method that constructs enum by string
    *
    * @param value string to create the enum
    * @return a new enumeration
    */
   public static PhaseType fromValue(String value) {
      for (PhaseType phaseType : values()) {
         if (phaseType.name().equals(value)) {
            return phaseType;
         }
      }
      throw new IllegalArgumentException("invalid string value passed: " + value);
   }

   /**
    * returns true if the specified action is valid in this phase
    * @param actionType the action whose validity is to be checked
    * @return true if the action is valid
    */
   public boolean isValid(ActionType actionType) {
      return this.availableActionsList.contains(actionType);
   }

   public boolean isSetup() {
      return this.isSetup;
   }

   public boolean isTmp() {
      return this.isTmp;
   }

   public List<ActionType> getAvailableActions() {
      return new ArrayList<>(this.availableActionsList);
   }

}
