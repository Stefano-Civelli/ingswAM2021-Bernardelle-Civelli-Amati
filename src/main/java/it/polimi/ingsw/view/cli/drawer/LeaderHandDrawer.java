package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

import java.util.List;

public class LeaderHandDrawer implements Buildable, Fillable{
  private static final int MAX_LEADER_LENGTH = 44;
  private static final int MAX_LEADER_HEIGHT = 4;

  @Override
  public String[][] build() {
    String[][] leadersSkeleton = new String[MAX_LEADER_HEIGHT][MAX_LEADER_LENGTH];

    for(int i=0; i< leadersSkeleton.length; i++)
      for(int j=0; j< leadersSkeleton[0].length; j++)
        leadersSkeleton[i][j] = " ";
    return leadersSkeleton;
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {
    List<Integer> leaderCardsID = playerState.getNotActiveLeaderCards();
    int a=0, b=0;

    for(Integer id : leaderCardsID) {
      String[][] leader =  LeaderConstructor.constructLeaderFromId(id);
      for(int i=0; i<leader.length; i++, a++)
        for(int j=0, c=b; j<leader[0].length; j++, c++)
          fillMe[a][c] = leader[i][j];

      a=0;
      b=b+11;
    }
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {}
}
