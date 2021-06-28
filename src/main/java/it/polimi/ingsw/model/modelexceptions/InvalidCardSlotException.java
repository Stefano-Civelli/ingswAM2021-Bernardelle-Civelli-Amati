package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a card placement (e.g. a card slot) is full/empty, doesn't exist or a card can't be placed into it
 */
public class InvalidCardSlotException extends ModelException {

  public InvalidCardSlotException() {
    super();
  }

  public InvalidCardSlotException(String message) {
    super(message);
  }

}
