package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.WarehouseUpdate;

public interface WarehouseUpdateObservable {

   void notifyModelChange(WarehouseUpdate msg);
}
