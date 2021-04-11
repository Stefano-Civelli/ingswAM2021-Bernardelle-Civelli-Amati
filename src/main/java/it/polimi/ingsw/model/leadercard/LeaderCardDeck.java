package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderCardDeck {

  private ArrayList<LeaderCard> leaderCardList;

  public LeaderCardDeck(){
  }

  //TODO metodo per dare la carta al player

  public LeaderCard removeCard(){
    return leaderCardList.remove(leaderCardList.size()-1);
  }

  public void shuffleLeaderList(){
    Collections.shuffle(leaderCardList);
  }

  public ArrayList<LeaderCard> getLeaderCardList() {
    return new ArrayList<>(leaderCardList);
  }
}