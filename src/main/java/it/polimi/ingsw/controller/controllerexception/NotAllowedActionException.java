package it.polimi.ingsw.controller.controllerexception;

/**
 * Thrown when an one try to perform an action that could not be performed during that phase of the game
 */
public class NotAllowedActionException extends ControllerException {

    public NotAllowedActionException() {
        super();
    }

    public NotAllowedActionException(String message) {
        super(message);
    }

}
