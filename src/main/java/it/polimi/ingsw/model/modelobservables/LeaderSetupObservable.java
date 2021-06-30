package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.LeaderSetup;

public interface LeaderSetupObservable {

   /**
    * notify the model observers that initial leaders have been chosen
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyLeaderSetup(String username, LeaderSetup msg);

}
