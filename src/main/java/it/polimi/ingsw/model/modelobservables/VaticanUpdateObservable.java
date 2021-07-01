package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.VaticanReport;

public interface VaticanUpdateObservable {

   /**
    * Notify the model observers that a pope card has been flipped
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a vatican report update
    */
   void notifyVaticanChange(VaticanReport msg);

}
