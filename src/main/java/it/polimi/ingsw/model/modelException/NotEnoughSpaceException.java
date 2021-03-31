package it.polimi.ingsw.model.modelException;

public class NotEnoughSpaceException extends Exception {

    public NotEnoughSpaceException() {
        super();
    }

    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
