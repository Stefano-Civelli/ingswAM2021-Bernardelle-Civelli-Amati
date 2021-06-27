package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.ChestUpdate;

public interface TempChestObservable {

   void notifyTempChestChange(ChestUpdate msg);

}
