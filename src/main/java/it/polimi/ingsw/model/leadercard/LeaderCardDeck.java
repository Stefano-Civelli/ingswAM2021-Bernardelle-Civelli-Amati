package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;

public class LeaderCardDeck {

   ArrayList<LeaderCard> leaderCardList;

   public ArrayList<LeaderCard> getLeaderCardList() {
      return new ArrayList<>(leaderCardList);
   }
}
