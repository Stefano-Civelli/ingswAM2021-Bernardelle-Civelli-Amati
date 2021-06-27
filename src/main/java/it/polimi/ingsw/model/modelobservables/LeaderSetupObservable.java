package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.LeaderSetup;

public interface LeaderSetupObservable {

   void notifyLeaderSetup(String username, LeaderSetup msg);

}
