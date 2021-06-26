package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to produce a null resource
 */
public class NeedAResourceToAddException extends ModelException {

   public NeedAResourceToAddException() {
      super();
   }

   public NeedAResourceToAddException(String message) {
      super(message);
   }

}
