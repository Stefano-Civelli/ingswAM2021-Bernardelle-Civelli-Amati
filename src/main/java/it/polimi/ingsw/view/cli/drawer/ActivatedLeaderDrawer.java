package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

import java.util.List;

public class ActivatedLeaderDrawer implements Buildable, Fillable{
  @Override
  public String[][] build() {
    String[][] activatedSkeleton = new String[10][13];
    int col=0;

    for(int i=0; i<activatedSkeleton.length; i++)
      for(int j=0; j<activatedSkeleton[0].length; j++)
        activatedSkeleton[i][j] = " ";

    for (char c : "ACTIVATED".toCharArray()) {
      activatedSkeleton[0][col] = Character.toString(c);
      col++;
    }

    col=0;
    for (char c : "LEADERS".toCharArray()) {
      activatedSkeleton[1][col] = Character.toString(c);
      col++;
    }

    activatedSkeleton[2][0] = "1";
    activatedSkeleton[6][0] = "2";
    activatedSkeleton[2][1] = ".";
    activatedSkeleton[6][1] = ".";

    return activatedSkeleton;
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {
    List<Integer> leaderCardsID = playerState.getActiveLeaderCards();
    int a=2, b=2;

    for(Integer id : leaderCardsID) {
      String[][] leader =  LeaderConstructor.constructLeaderFromId(id);
      for(int i=0, f=a; i<leader.length; i++, f++)
        for(int j=0, c=b; j<leader[0].length; j++, c++)
          fillMe[f][c] = leader[i][j];

      try {
        int c=7;
        LeaderCard leaderCard = LeaderConstructor.getLeaderCardFromId(id);
        if(leaderCard.getResToStore()!=null){
          ResourceType resource = leaderCard.getResToStore();
          for(Pair<ResourceType, Integer> pair : playerState.getLeaderLevels())
            if(resource.equals(pair.getKey())) {
              int quantity = pair.getValue();
              System.out.println(pair.getValue());
              while(quantity > 0) {
                fillMe[a+2][c] = resource.toString();
                c+=2;
                quantity--;
              }
            }

        }
      } catch (InvalidCardException e) {}

      a=a+4;
      b=2;
    }
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {}
}
