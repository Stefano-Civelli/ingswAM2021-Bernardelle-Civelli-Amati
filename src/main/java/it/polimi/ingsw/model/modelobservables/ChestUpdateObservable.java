package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.ChestUpdate;

public interface ChestUpdateObservable {

   /**
    * notify the model observers that an element has been removed from the chest
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyChestUpdate(ChestUpdate msg);

}
