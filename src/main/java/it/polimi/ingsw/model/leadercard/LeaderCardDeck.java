package it.polimi.ingsw.model.leadercard;


import it.polimi.ingsw.model.modelexceptions.InvalidCardException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderCardDeck {

  //assigned via JSON parsing
  private List<LeaderCard> leaderCardList;

  public LeaderCardDeck(){
  }

  /**
   * Randomly permutes leaderCards in the deck
   */
  public void shuffleLeaderList(){
    Collections.shuffle(leaderCardList);
  }

  /**
   * draws 4 cards from the deck and returns them as a list
   *
   * @return the 4 drawn cards
   */
  public List<LeaderCard> drawFourCards(){
    List<LeaderCard> fourCards = new ArrayList<>();
    for(int i=0; i<4; i++){
      fourCards.add(leaderCardList.get(0));
      leaderCardList.remove(0);
    }
    return fourCards;
  }

  /**
   * get the leaderCard corresponding to the specified Id
   *
   * @param id Id of the leaderCard to get
   * @return the the leaderCard corresponding to the specified Id
   * @throws InvalidCardException if there isn't a card with the specified id in the deck
   */
   public LeaderCard getCardFromId(int id) throws InvalidCardException {
     for(LeaderCard d : leaderCardList)
       if(d.getLeaderId() == id)
         return d;
     throw new InvalidCardException("there isn't a card with this id in the deck");
   }
}