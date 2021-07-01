package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.ChestUpdate;

public interface ChestUpdateObservable {

   /**
    * Notify the model observers that an element has been removed from the chest
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a chest update
    */
   void notifyChestUpdate(ChestUpdate msg);

}
