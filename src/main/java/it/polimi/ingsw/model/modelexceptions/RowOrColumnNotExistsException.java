package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified market row or column is out of bound
 */
public class RowOrColumnNotExistsException extends ModelException {

    public RowOrColumnNotExistsException() {
        super();
    }

    public RowOrColumnNotExistsException(String message) {
        super(message);
    }

}
