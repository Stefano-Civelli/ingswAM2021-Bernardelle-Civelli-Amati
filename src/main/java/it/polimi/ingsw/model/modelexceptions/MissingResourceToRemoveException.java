package it.polimi.ingsw.model.modelexceptions;

public class MissingResourceToRemoveException extends Exception {

  public MissingResourceToRemoveException() {
    super();
  }

  public MissingResourceToRemoveException(String message) {
    super(message);
  }
}