package it.polimi.ingsw.model.modelexceptions;

public class IncorrectResourceTypeException extends Exception {

    public IncorrectResourceTypeException() {
        super();
    }

    public IncorrectResourceTypeException(String message) {
        super(message);
    }
}