package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.PhaseType;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class TurnState {

    private final String player;
    private final PhaseType phase;

    public TurnState(String player, PhaseType phase) {
        this.player = player;
        this.phase = phase;
    }

    /**
     * returns the current player's username
     * @return current player's username
     */
    public String getPlayer() {
        return player;
    }

    /**
     * returns the current gamePhase
     * @return current gamePhase
     */
    public PhaseType getPhase() {
        return phase;
    }
}
