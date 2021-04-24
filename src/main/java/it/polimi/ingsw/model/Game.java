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

   private final LeaderCardDeck leaderCardDeck;
   private final Market market;
   private final DevelopCardDeck developCardDeck;
   private final List<PlayerBoard> playerBoardList;

   //mettere i due file nella classe che chiama il costruttore game, oppure mettere direttamente nel main e propaghiamo che é meglio
   //private final File cardConfigFile = new File("src/DevelopCardConfig.json");
   //private final File leaderCardConfigFile = new File("src/LeaderCardConfig.json");

   public Game(File cardConfigFile, File leaderCardConfigFile) throws IOException {
      this.leaderCardDeck = GSON.leaderCardParser(leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(cardConfigFile);
      this.market = new Market();
      this.playerBoardList = new ArrayList<>();
   }

   //need also to check that the max number of players in this lobby isn't exceeded -> that's not necessary
   public void addPlayer(String username) throws IOException, InvalidUsernameException {
      List<LeaderCard> fourInitialLeaderCardsForPlayer = leaderCardDeck.drawFourCards();
      for(PlayerBoard x : playerBoardList)
         if(x.getUsername().equals(username))
            throw new InvalidUsernameException("This username is already taken");
      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeaderCardsForPlayer, market, developCardDeck);
      playerBoardList.add(playerBoard);
   }

   public String startGame() {
      Collections.shuffle(this.playerBoardList);
      return this.playerBoardList.get(0).getUsername();
   }

   public String nextPlayer(String currentPlayer) throws InvalidUsernameException {
      for(int i = 0; i < this.playerBoardList.size(); i++)
         if(this.playerBoardList.get(i).getUsername().equals(currentPlayer))
            return i + 1 < this.playerBoardList.size()
                    ? this.playerBoardList.get(i + 1).getUsername()
                    : this.playerBoardList.get(0).getUsername();
      throw new InvalidUsernameException();
   }

   public PlayerBoard getPlayerBoard(String username) throws InvalidUsernameException {
      try {
         return playerBoardList.stream().filter(playerBoard -> playerBoard.getUsername().equals(username))
                 .collect(Collectors.toList()).get(0);
      } catch (IndexOutOfBoundsException e) {
         throw new InvalidUsernameException();
      }
   }

}
