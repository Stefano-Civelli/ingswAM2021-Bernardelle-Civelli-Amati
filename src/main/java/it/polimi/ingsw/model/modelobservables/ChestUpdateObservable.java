package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.ChestUpdate;

public interface ChestUpdateObservable {

   void notifyChestUpdate(ChestUpdate msg);
}
