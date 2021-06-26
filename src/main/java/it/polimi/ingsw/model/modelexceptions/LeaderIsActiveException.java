package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to perform something that must be performed on an inactive leader card on an active leader card:
 * e.g. try to discard an active leader card
 */
public class LeaderIsActiveException extends ModelException {

    public LeaderIsActiveException() {
        super();
    }

    public LeaderIsActiveException(String message) {
        super(message);
    }

}
