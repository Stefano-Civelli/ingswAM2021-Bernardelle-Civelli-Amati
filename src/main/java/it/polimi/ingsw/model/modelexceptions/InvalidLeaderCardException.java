package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified leader card doesn't exist or isn't available to perform the specific action
 */
public class InvalidLeaderCardException extends ModelException {

  public InvalidLeaderCardException() {
    super();
  }

  public InvalidLeaderCardException(String message) {
    super(message);
  }

}