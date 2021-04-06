package it.polimi.ingsw.model.modelexceptions;

public class NotEnoughResourcesException extends Exception {

    public NotEnoughResourcesException() {
        super();
    }

    public NotEnoughResourcesException(String message) {
        super(message);
    }
}
