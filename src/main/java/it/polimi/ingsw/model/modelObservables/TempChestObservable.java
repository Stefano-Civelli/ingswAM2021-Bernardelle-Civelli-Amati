package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.Chest;

public interface TempChestObservable {

   void notifyTempChestChange(Chest.ChestUpdate msg);

}
