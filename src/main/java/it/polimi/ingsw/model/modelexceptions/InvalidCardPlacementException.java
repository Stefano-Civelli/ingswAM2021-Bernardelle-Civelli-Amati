package it.polimi.ingsw.model.modelexceptions;

public class InvalidCardPlacementException extends Exception {

  public InvalidCardPlacementException() {
    super();
  }

  public InvalidCardPlacementException(String message) {
    super(message);
  }
}