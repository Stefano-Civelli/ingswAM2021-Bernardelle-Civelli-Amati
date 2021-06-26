package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified player doesn't exist
 */
public class InvalidUsernameException extends ModelException {

  public InvalidUsernameException() {
    super();
  }

  public InvalidUsernameException(String message) {
    super(message);
  }

}
