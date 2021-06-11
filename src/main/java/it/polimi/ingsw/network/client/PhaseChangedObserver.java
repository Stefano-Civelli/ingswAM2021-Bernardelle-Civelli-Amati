package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.TurnManager;

public interface PhaseChangedObserver {
  void phaseUpdate(TurnManager.TurnState nextPhase);
}
