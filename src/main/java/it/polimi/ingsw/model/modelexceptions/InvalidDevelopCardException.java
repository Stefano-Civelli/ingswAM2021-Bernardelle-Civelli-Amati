package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when a specified develop card doesn't exist or isn't available to perform the specific action
 */
public class InvalidDevelopCardException extends ModelException {

    public InvalidDevelopCardException() {
        super();
    }

    public InvalidDevelopCardException(String message) {
        super(message);
    }

}
