package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified quantity is negative
 */
public class NegativeQuantityException extends ModelException {

   public NegativeQuantityException() {
      super();
   }

   public NegativeQuantityException(String message) {
      super(message);
   }
}
