package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to add to warehouse too many special leader card's levels (try to activate too many storage leader cards)
 */
public class MaxLeaderCardLevelsException extends ModelException {

    public MaxLeaderCardLevelsException() {
        super();
    }

    public MaxLeaderCardLevelsException(String message) {
        super(message);
    }

}
