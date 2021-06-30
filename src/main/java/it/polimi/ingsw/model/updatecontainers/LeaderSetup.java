package it.polimi.ingsw.model.updatecontainers;

import java.util.List;

/**
 * A special model update used to communicate to clients or views the initial {@link it.polimi.ingsw.model.leadercard.LeaderCard leader cards} of a player.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class LeaderSetup implements ModelUpdate {

   private final List<Integer> leaderList;

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
