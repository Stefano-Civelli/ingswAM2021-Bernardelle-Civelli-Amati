package it.polimi.ingsw.model.modelException;

public class InvalidCardPlacementException extends Exception {

    public InvalidCardPlacementException() {
        super();
    }

    public InvalidCardPlacementException(String message) {
        super(message);
    }
}
