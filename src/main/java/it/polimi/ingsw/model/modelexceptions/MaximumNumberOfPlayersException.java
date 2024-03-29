package it.polimi.ingsw.model.modelexceptions;

/**
 * Thrown when one try to start a game with too many players
 */
public class MaximumNumberOfPlayersException extends ModelException {

    private Integer maxPlayer = null;

    public MaximumNumberOfPlayersException() {
        super();
    }

    public MaximumNumberOfPlayersException(String message) {
        super(message);
    }

    public MaximumNumberOfPlayersException(int maxPlayer) {
        super();
        this.maxPlayer = maxPlayer;
    }

    public MaximumNumberOfPlayersException(String message, int maxPlayer) {
        super(message);
        this.maxPlayer = maxPlayer;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

}
