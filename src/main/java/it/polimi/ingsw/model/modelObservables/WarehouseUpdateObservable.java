package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.Warehouse;

public interface WarehouseUpdateObservable {

   void notifyModelChange(Warehouse.WarehouseUpdate msg);
}
