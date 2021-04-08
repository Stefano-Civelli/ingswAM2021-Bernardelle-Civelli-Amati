package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.List;

public class LeaderCardDeck {
  private List<LeaderCard> leaderCardList;

  public LeaderCardDeck(){
  }

  public LeaderCard removeCard(){
    return leaderCardList.remove(leaderCardList.size()-1);
  }

  public void shuffleLeaderList(){
    Collections.shuffle(leaderCardList);
  }
}
