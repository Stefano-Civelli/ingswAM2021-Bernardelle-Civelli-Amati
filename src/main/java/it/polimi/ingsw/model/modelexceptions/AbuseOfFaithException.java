package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to use the faith resource type in the wrong way (but permitted with other resource type): e.g. try to add it into chest or warehouse
 */
public class AbuseOfFaithException extends ModelException {

  public AbuseOfFaithException() {
    super();
  }

  public AbuseOfFaithException(String message) {
    super(message);
  }

}
