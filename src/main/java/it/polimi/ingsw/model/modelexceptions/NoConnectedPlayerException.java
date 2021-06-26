package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when, after a player disconnection, there aren't connected player to continue the game
 */
public class NoConnectedPlayerException extends ModelException {

    public NoConnectedPlayerException() {
        super();
    }

    public NoConnectedPlayerException(String message) {
        super(message);
    }

}
