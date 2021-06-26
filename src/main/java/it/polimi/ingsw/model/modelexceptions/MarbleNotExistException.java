package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified marble doesn't exist
 */
public class MarbleNotExistException extends ModelException {

    public MarbleNotExistException() {
        super();
    }

    public MarbleNotExistException(String message) {
        super(message);
    }

}
