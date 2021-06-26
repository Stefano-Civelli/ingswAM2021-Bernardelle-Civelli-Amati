package it.polimi.ingsw.controller.controllerexception;

/**
 * Thrown when one try to perform an action with not all the required field correctly set
 */
public class InvalidActionException extends ControllerException {

    public InvalidActionException() {
        super();
    }

    public InvalidActionException(String message) {
        super(message);
    }

}
