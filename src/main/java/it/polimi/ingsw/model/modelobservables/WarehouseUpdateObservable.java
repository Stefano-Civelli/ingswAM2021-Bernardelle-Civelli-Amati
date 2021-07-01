package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.WarehouseUpdate;

public interface WarehouseUpdateObservable {

   /**
    * Notify the model observers that Warehouse state has changed
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a warehouse update
    */
   void notifyWarehouseUpdate(WarehouseUpdate msg);

}
