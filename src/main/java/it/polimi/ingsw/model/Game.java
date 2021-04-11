package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.utility.GSON;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Game {

   private LeaderCardDeck leadercardDeck;
   private Market market;
   private DevelopCardDeck developCardDeck;
   private List<PlayerBoard> playerBoardList;

   //mettere i due file nella classe che chiama game
   //private final File cardConfigFile = new File("src/DevelopCardConfig.json");
   //private final File leaderCardConfigFile = new File("src/LeaderCardConfig.json");
   //

   public Game(File cardConfigFile, File leaderCardConfigFile) throws IOException {
      this.leadercardDeck = GSON.leaderCardParser(leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(cardConfigFile);
      this.market = Market.getInstance();
      this.playerBoardList = new ArrayList<>();
   }

   //need also to check that the max number of players in this lobby isn't exceeded -> that's not necessary
   public void addPlayer(String username) throws IOException, InvalidUsernameException {
      List<LeaderCard> fourInitialLeaderCardsForPlayer =
              leadercardDeck.getLeaderCardList()
                            .stream()
                            .limit(4)
                            .collect(Collectors.toList());
      for(PlayerBoard x : playerBoardList)
         if(x.getUsername().equals(username))
            throw new InvalidUsernameException("This username is already taken");

      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeaderCardsForPlayer, market, developCardDeck);
      playerBoardList.add(playerBoard);
   }

   //il current player direi che non lo tiene game ma il turn manager
}
