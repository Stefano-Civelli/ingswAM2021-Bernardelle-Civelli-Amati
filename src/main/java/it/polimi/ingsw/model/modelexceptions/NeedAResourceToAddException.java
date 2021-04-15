package it.polimi.ingsw.model.modelexceptions;

public class NeedAResourceToAddException extends Exception{

   public NeedAResourceToAddException() {
      super();
   }

   public NeedAResourceToAddException(String message) {
      super(message);
   }

}
