package it.polimi.ingsw.model.modelexceptions;

public class NegativeQuantityException extends Exception {

   public NegativeQuantityException() {
      super();
   }

   public NegativeQuantityException(String message) {
      super(message);
   }
}
