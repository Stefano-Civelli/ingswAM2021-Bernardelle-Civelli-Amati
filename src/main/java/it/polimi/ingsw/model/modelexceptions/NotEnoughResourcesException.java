package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to perform something without having the required resources
 */
public class NotEnoughResourcesException extends ModelException {

    public NotEnoughResourcesException() {
        super();
    }

    public NotEnoughResourcesException(String message) {
        super(message);
    }
}
