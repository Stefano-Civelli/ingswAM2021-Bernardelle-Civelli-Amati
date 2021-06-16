package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updateContainers.TurnState;

public interface PhaseChangedObservable {

  void notifyPhaseChanged(TurnState nextPhase);

}
