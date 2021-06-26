package it.polimi.ingsw.controller.controllerexception;

/**
 * Thrown when one try to perform an action for the wrong player: e.g. for a player that isn't the current player of for a player that doesn't exist
 */
public class WrongPlayerException extends ControllerException {

    public WrongPlayerException() {
        super();
    }

    public WrongPlayerException(String message) {
        super(message);
    }

}
