package it.polimi.ingsw.model.updateContainers;

import java.util.List;

public class LeaderSetup {
   private List<Integer> leaderList;

   public LeaderSetup(List<Integer> leaderList) {
      this.leaderList = leaderList;
   }

   public List<Integer> getLeaderList() {
      return leaderList;
   }
}
