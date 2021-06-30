package it.polimi.ingsw.view;

/**
 * Class containing the possible error outputs
 */
public final class ClientStrings {

   public static final String NOT_BUYABLE = "You can't buy this card";
   public static final String INVALID_LEADERCARD = "";
   public static final String INVALID_DEVELOP_CARD = "";
   public static final String CANNOT_DISCARD_ACTIVE_LEADER = "sorry mate, you can't discard an active leader card";
   public static final String NOT_ACTIVATABLE_PRODUCTION = "sorry mate, you can't activate this production ";
   public static final String ALREADY_PRODUCED = "sorry mate, you already have already used this production in this turn";
   public static final String NOT_ENOUGH_RESOURCES = "sorry mate, you don't have enough resources to perform this action ";
   public static final String INVALID_CARD_PLACEMENT = "sorry mate, you can't place your Develop card here ";

   private ClientStrings () {
      // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
   }

}
