package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.WarehouseUpdate;

public interface WarehouseUpdateObservable {

   /**
    * notify the model observers that Warehouse state has changed
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyWarehouseUpdate(WarehouseUpdate msg);

}
