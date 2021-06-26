package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to perform a production on something whose production has already been activated during the same turn
 */
public class AlreadyProducedException extends ModelException {

    public AlreadyProducedException() {
        super();
    }

    public AlreadyProducedException(String message) {
        super(message);
    }

}
