package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.TurnManager;

public interface PhaseChangedObservable {

  void notifyPhaseChanged(TurnManager.TurnState nextPhase);

}
