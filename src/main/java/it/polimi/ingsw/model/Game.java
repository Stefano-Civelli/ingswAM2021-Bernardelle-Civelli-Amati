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

   //----------da mettere in chi chiama game??--------------
   private final File cardConfigFile = new File("src/DevelopCardConfig.json");
   private final File leaderCardConfigFile = new File("src/LeaderCardConfig.json");
   //----------da mettere in chi chiama game??----------------


   public Game(File leaderCardConfigFile, File cardConfigFile, Market market,  List<PlayerBoard> playerBoardList) throws IOException {
      this.leadercardDeck = GSON.leaderCardParser(leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(cardConfigFile);
      this.market = market;
      this.playerBoardList = new ArrayList<>();
   }


   public void addPlayer(String username){
      List<LeaderCard> fourInitialLeadercardsForPlayer =
              leadercardDeck.getLeaderCardList()
                            .stream()
                            .limit(4)
                            .collect(Collectors.toList());
      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeadercardsForPlayer, market, developCardDeck);
      playerBoardList.add(playerBoard);
   }


   //il current player direi che non lo tiene game ma il turn managerr


}
