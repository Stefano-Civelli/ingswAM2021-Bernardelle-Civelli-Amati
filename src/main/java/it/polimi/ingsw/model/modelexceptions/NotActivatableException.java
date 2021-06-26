package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to perform a not activatable production
 */
public class NotActivatableException extends ModelException {

   public NotActivatableException() {
      super();
   }

   public NotActivatableException(String message) {
      super(message);
   }

}
