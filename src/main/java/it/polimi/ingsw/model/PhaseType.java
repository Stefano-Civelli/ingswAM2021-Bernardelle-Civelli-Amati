package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.action.ActionType;

import java.util.Collections;
import java.util.List;
import static it.polimi.ingsw.controller.action.ActionType.*;

/**
 * Represents all the possible turn phases.
 * Each phase has a list of the valid action during that phase
 */
public enum PhaseType {

   SETUP_CHOOSING_RESOURCES(List.of(SETUP_CHOOSE_RESOURCES), true),
   SETUP_DISCARDING_LEADERS(List.of(SETUP_DISCARD_LEADERS), true),
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
    * Returns true if the specified action is valid in this phase
    *
    * @param actionType the action whose validity is to be checked
    * @return true if the action is valid
    */
   public boolean isValid(ActionType actionType) {
      return this.availableActionsList.contains(actionType);
   }

   /**
    * Check if the Phase is a initial setup phase (in the first turn of the player)
    *
    * @return true if it's a setup phase
    */
   public boolean isSetup() {
      return this.isSetup;
   }

   /**
    * Check if the Phase is a temporary phase existing only during some model updates (but never before or after it)
    *
    * @return true if it's temporary
    */
   public boolean isTmp() {
      return this.isTmp;
   }

   /**
    *
    * @return an unmodifiable list of all the valid actions during this phase
    */
   public List<ActionType> getAvailableActions() {
      return Collections.unmodifiableList(this.availableActionsList);
   }

}
