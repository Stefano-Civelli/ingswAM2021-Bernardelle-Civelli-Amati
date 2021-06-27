package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.WarehouseUpdate;

public interface WarehouseUpdateObservable {

   void notifyModelChange(WarehouseUpdate msg);
}
