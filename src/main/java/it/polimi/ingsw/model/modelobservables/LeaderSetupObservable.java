package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderSetup;

public interface LeaderSetupObservable {

   /**
    * Notify the model observers that initial leaders have been chosen
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a leader card setup containing the initial leader cards of a player
    */
   void notifyLeaderSetup(String username, LeaderSetup msg);

}
