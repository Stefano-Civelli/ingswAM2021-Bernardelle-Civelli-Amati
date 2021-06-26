package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to add into warehouse a white marble having more than one leader card that can convert it without specify which of them must be used
 */
public class MoreWhiteLeaderCardsException extends ModelException {

    public MoreWhiteLeaderCardsException() {
        super();
    }

    public MoreWhiteLeaderCardsException(String message) {
        super(message);
    }

}
