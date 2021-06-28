package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updatecontainers.TurnState;

public interface PhaseChangedObservable {
  /**
   * Notifies observers that the phase has changed into the one passed as parameter
   * @param nextPhase, update content
   */
  void notifyPhaseChanged(TurnState nextPhase);
}
