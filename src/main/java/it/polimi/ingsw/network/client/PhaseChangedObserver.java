package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updateContainers.TurnState;

public interface PhaseChangedObserver {
  void phaseUpdate(TurnState nextPhase);
}
