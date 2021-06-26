package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified develop card doesn't exist or isn't available to perform the specific action
 */
public class InvalidCardException extends ModelException {

   public InvalidCardException() {
      super();
   }

   public InvalidCardException(String message) {
      super(message);
   }

}
