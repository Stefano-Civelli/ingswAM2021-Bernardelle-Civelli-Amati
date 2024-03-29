package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.ChestUpdate;

public interface TempChestObservable {

   /**
    * Notify the model observers that an element has been added to the temporary chest
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a chest update
    */
   void notifyTempChestChange(ChestUpdate msg);

}
