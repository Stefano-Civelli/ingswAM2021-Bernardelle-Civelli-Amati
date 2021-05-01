package it.polimi.ingsw.controller.action;

public class InvalidActionException extends Exception {

    public InvalidActionException() {
        super();
    }

    public InvalidActionException(String message) {
        super(message);
    }

}
