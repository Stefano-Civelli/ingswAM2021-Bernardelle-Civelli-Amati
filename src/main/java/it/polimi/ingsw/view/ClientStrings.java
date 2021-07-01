package it.polimi.ingsw.view;

/**
 * Class containing the possible error outputs
 */
public final class ClientStrings {

   public static final String NOT_BUYABLE = "You can't buy this card";
   public static final String INVALID_LEADERCARD = "";
   public static final String INVALID_DEVELOP_CARD = "";
   public static final String CANNOT_DISCARD_ACTIVE_LEADER = "sorry, you can't discard an active leader card";
   public static final String NOT_ACTIVATABLE_PRODUCTION = "sorry, you can't activate this production ";
   public static final String ALREADY_PRODUCED = "sorry, you already have already used this production in this turn";
   public static final String NOT_ENOUGH_RESOURCES = "sorry, you don't have enough resources to perform this action ";
   public static final String INVALID_CARD_PLACEMENT = "sorry, you can't place your Develop card here ";
   public static final String MATCH_ALREADY_EXISTS = "unfortunately you can't create a match with this name because it already Exists ";
   public static final String CANNOT_JOIN_MATCH = "sorry, this match doesn't exist ";

   private ClientStrings () {
      // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
   }

}
