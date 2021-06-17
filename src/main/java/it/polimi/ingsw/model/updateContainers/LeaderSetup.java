package it.polimi.ingsw.model.updateContainers;

import java.util.List;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class LeaderSetup {
   private List<Integer> leaderList;

   public LeaderSetup(List<Integer> leaderList) {
      this.leaderList = leaderList;
   }

   /**
    * returns the ID list of the 4 leader cards selected fot that specific player
    * @return the ID list of the 4 leader cards selected fot that specific player
    */
   public List<Integer> getLeaderList() {
      return leaderList;
   }
}
