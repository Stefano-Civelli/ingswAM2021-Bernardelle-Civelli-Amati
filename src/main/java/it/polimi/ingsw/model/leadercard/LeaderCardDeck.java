package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

  public List<LeaderCard> getLeaderCardList() {
    return new ArrayList<>(leaderCardList);
  }

  public List<LeaderCard> drawFourCards(){
    List<LeaderCard> fourCards = new ArrayList<>();
    for(int i=0; i<4; i++){
      fourCards.add(leaderCardList.get(i));
      leaderCardList.remove(i);
    }
    return fourCards;
  }

}