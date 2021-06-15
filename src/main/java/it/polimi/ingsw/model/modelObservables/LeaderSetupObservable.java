package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.updateContainers.LeaderSetup;

public interface LeaderSetupObservable {

   void notifyLeaderSetup(String username, LeaderSetup msg);

}
