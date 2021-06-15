package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.ChestUpdate;

public interface ChestUpdateObservable {

   void notifyChestUpdate(ChestUpdate msg);
}
