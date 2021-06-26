package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to add into warehouse a resources without having a place in which put it
 */
public class NotEnoughSpaceException extends ModelException {

    public NotEnoughSpaceException() {
        super();
    }

    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
