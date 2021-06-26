package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown one try to use a leader card of the wrong type to do something
 */
public class WrongLeaderCardException extends ModelException {

    public WrongLeaderCardException() {
        super();
    }

    public WrongLeaderCardException(String message) {
        super(message);
    }

}
