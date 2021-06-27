package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updatecontainers.TurnState;

public interface PhaseChangedObserver {
  void phaseUpdate(TurnState nextPhase);
}
