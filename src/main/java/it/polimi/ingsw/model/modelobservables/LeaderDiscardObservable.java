package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;

public interface LeaderDiscardObservable {

   /**
    * Notify the model observers that a leader card has been discarded
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a leader card update
    */
   void notifyLeaderDiscard(LeaderUpdate msg);

}
