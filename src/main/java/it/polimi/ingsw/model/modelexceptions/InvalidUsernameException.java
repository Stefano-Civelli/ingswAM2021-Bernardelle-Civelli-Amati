package it.polimi.ingsw.model.modelexceptions;

public class InvalidUsernameException extends Exception{

  public InvalidUsernameException() {
    super();
  }

  public InvalidUsernameException(String message) {
    super(message);
  }

}
