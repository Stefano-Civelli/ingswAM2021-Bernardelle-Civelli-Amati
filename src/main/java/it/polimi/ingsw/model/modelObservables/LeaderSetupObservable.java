package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.Game;

public interface LeaderSetupObservable {

   void notifyLeaderSetup(String username, Game.LeaderSetup msg);

}
