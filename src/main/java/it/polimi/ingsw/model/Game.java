package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Game {

   private final LeaderCardDeck leaderCardDeck;
   private final Market market;
   protected final DevelopCardDeck developCardDeck;
   private final List<PlayerBoard> playerBoardList;
   Controller controller;

   public Game(Controller controller) throws IOException {
      this.leaderCardDeck = GSON.leaderCardParser(ConfigParameters.leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(ConfigParameters.cardConfigFile);
      this.market = new Market();
      this.playerBoardList = new ArrayList<>();
      this.controller = controller;

      this.market.setController(controller);
      this.developCardDeck.setController(controller);
   }

   //need also to check that the max number of players in this lobby isn't exceeded -> that's not necessary
   public void addPlayer(String username) throws IOException {
      List<LeaderCard> fourInitialLeaderCardsForPlayer = this.leaderCardDeck.drawFourCards();

      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeaderCardsForPlayer, market, developCardDeck);

      // add all observers to the list in observable classes
      for(PlayerBoard playerBoardSetObserver : playerBoardList) {
         playerBoardSetObserver.getTrack().addToVaticanReportObserverList(playerBoard.getTrack());
         playerBoard.addToMoveForwardObserverList(playerBoardSetObserver.getTrack());
         playerBoardSetObserver.addToMoveForwardObserverList(playerBoard.getTrack());
      }

      playerBoard.setController(controller);
      playerBoardList.add(playerBoard);
   }

   public String startGame() {
      Collections.shuffle(this.playerBoardList);
      this.playerBoardList
              .forEach(playerBoard -> playerBoard.getTrack().moveForward(initialFaith(playerBoard.getUsername())));
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

   public int initialResources(String username) {
      int index = this.playerBoardList.stream().map(PlayerBoard::getUsername)
              .collect(Collectors.toList()).indexOf(username);
      switch(index) {
         case 2 : case 3:
            return 1;
         case 4:
            return 2;
      }
      return 0;
   }

   public int initialFaith(String username) {
      int index = this.playerBoardList.stream().map(PlayerBoard::getUsername)
              .collect(Collectors.toList()).indexOf(username);
      switch(index) {
         case 3 : case 4:
            return 1;
      }
      return 0;
   }

   public PlayerBoard getPlayerBoard(String username) throws InvalidUsernameException {
      try {
         return playerBoardList.stream().filter(playerBoard -> playerBoard.getUsername().equals(username))
                 .collect(Collectors.toList()).get(0);
      } catch (IndexOutOfBoundsException e) {
         throw new InvalidUsernameException();
      }
   }

   public List<String> getOrderedPlayers() {
      return this.playerBoardList.stream().map(PlayerBoard::getUsername).collect(Collectors.toList());
   }

}
