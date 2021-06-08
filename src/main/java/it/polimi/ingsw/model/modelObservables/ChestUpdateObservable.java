package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.Chest;

public interface ChestUpdateObservable {

   void notifyChestUpdate(Chest.ChestUpdate msg);
}
