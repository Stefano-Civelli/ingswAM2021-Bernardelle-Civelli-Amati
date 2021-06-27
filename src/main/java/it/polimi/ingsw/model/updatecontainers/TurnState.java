package it.polimi.ingsw.model.updatecontainers;

import it.polimi.ingsw.model.PhaseType;

/**
 * A model update represents the new state of the game, i.e. an update happened in a {@link it.polimi.ingsw.model.TurnManager turn manager}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class TurnState implements ModelUpdate {

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
