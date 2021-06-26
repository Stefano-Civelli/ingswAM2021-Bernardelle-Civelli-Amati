package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to buy a develop card that can't be bought by them
 */
public class NotBuyableException extends ModelException {

   public NotBuyableException() {
      super();
   }

   public NotBuyableException(String message) {
      super(message);
   }

}
