package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.PhaseType;

public class TurnState {

    private final String player;
    private final PhaseType phase;

    public TurnState(String player, PhaseType phase) {
        this.player = player;
        this.phase = phase;
    }

    public String getPlayer() {
        return player;
    }

    public PhaseType getPhase() {
        return phase;
    }

}
