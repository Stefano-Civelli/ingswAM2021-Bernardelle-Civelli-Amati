package it.polimi.ingsw.model.leadercard;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;

import java.util.ArrayList;
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

  public List<LeaderCard> getLeaderCardList() {
    return new ArrayList<>(leaderCardList);
  }

  public List<LeaderCard> drawFourCards(){
    List<LeaderCard> fourCards = new ArrayList<>();
    for(int i=0; i<4; i++){
      fourCards.add(leaderCardList.get(0));
      leaderCardList.remove(0);
    }
    return fourCards;
  }

   public LeaderCard getCardFromId(int id) throws InvalidCardException {
     for(LeaderCard d : leaderCardList)
       if(d.getLeaderId() == id)
         return d;
     throw new InvalidCardException("there isn't a card with this id in the deck");
   }
}