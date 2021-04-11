package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.utility.GSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

   private LeaderCardDeck leadercardDeck;
   private Market market;
   private DevelopCardDeck developCardDeck;
   private List<PlayerBoard> playerBoardList;
   private final File cardConfigFile = new File("src/DevelopCardConfig.json");
   private final File leaderCardConfigFile = new File("src/LeaderCardConfig.json");

   public Game() throws IOException {
      this.leadercardDeck = GSON.leaderCardParser(leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(cardConfigFile);
      this.market = Market.getInstance();
      this.playerBoardList = new ArrayList<>();
   }


   public void addPlayer(String username) throws IOException {
      //need to add a check because we don't need to add a player that is already in the list
      //need also to check that the max number of players in this lobby isn't exceeded -> that's not necessary
      List<LeaderCard> fourInitialLeaderCardsForPlayer =
              leadercardDeck.getLeaderCardList()
                            .stream()
                            .limit(4)
                            .collect(Collectors.toList());

      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeaderCardsForPlayer, market, developCardDeck);
      playerBoardList.add(playerBoard);
   }


   //il current player direi che non lo tiene game ma il turn manager
}
