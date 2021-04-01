package it.polimi.ingsw.model.modelexceptions;

public class NotEnoughSpaceException extends Exception {

    public NotEnoughSpaceException() {
        super();
    }

    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
