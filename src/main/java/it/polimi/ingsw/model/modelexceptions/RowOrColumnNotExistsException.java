package it.polimi.ingsw.model.modelexceptions;

public class RowOrColumnNotExistsException extends Exception {

    public RowOrColumnNotExistsException() {
        super();
    }

    public RowOrColumnNotExistsException(String message) {
        super(message);
    }

}
