package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updatecontainers.TurnState;

public interface PhaseChangedObserver {
  /**
   * Updates the nextPhase in ClientTurnManager class
   * @param nextPhase, update content
   */
  void phaseUpdate(TurnState nextPhase);

}
