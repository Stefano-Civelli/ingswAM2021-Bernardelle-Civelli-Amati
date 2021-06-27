package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelobservables.LeaderSetupObservable;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.model.updatecontainers.LeaderSetup;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a game
 */
public class Game implements LeaderSetupObservable, EndGameObserver {

   private boolean gameStarted = false;
   private final LeaderCardDeck leaderCardDeck;
   private final Market market;
   protected final DevelopCardDeck developCardDeck;
   protected final List<Pair<PlayerBoard, Boolean>> playerBoards;
   protected boolean endGame = false;

   protected final transient ModelObserver controller;

   /**
    * Create a new multi player game
    *
    * @param controller the observer of the model
    * @throws IOException if there are some problems loading the configuration files
    */
   public Game(ModelObserver controller) throws IOException {
      this.leaderCardDeck = GSON.leaderCardParser();
      this.developCardDeck = GSON.cardParser();
      this.developCardDeck.finalizeDeckSetup(controller);
      this.market = new Market(controller);
      this.playerBoards = new ArrayList<>();
      this.controller = controller;
   }

   /**
    * Adds a new player in this game
    *
    * @param username username of the new player
    * @throws IOException if there are some problems loading the configuration files
    * @throws MaximumNumberOfPlayersException there are already four players in this game
    */
   public void addPlayer(String username) throws IOException, MaximumNumberOfPlayersException {
      if(this.playerBoards.size() >= 4)
         throw new MaximumNumberOfPlayersException(4);
      List<LeaderCard> fourInitialLeaderCardsForPlayer = this.leaderCardDeck.drawFourCards();

      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeaderCardsForPlayer, market, developCardDeck);

      // add all observers to the list in observable classes
      for(PlayerBoard playerBoardSetObserver : playerBoards.stream().map(Pair::getKey).collect(Collectors.toList())) {
         playerBoardSetObserver.getTrack().addToVaticanReportObserverList(playerBoard.getTrack());
         playerBoard.addToMoveForwardObserverList(playerBoardSetObserver.getTrack());
         playerBoardSetObserver.addToMoveForwardObserverList(playerBoard.getTrack());
      }

      playerBoard.setController(controller);
      playerBoard.setEndGameObserver(this);
      playerBoards.add(new Pair<>(playerBoard, true));
      notifyLeaderSetup(username, new LeaderSetup( idLeaderList(fourInitialLeaderCardsForPlayer)));
   }

   /**
    * Starts this game
    *
    * @return the username of the first player
    */
   public String startGame() {
      Collections.shuffle(this.playerBoards);
      return this.playerBoards.get(0).getKey().getUsername();
   }

   public void initialMoveForward() {
         this.playerBoards.stream().map(Pair::getKey).collect(Collectors.toList())
                 .forEach(playerBoard -> playerBoard.getTrack().moveForward(initialFaith(playerBoard.getUsername())));
   }

   /**
    * Returns the player that must play after the specified player (presumably the current player)
    * @param currentPlayer username of the current player
    * @return username of next connected player
    * @throws InvalidUsernameException if the specified player doesn't exist
    */
   public String nextConnectedPlayer(String currentPlayer) throws InvalidUsernameException {

      if(endGame && currentPlayer.equals(playerBoards.get(playerBoards.size()-1).getKey().getUsername())) {
         handleEndGame();
         return  null; //TODO alex controllami
      }
      else {
         for (int i = 0; i < this.playerBoards.size(); i++)
            if (this.playerBoards.get(i).getKey().getUsername().equals(currentPlayer))
               for (int j = 1; j <= this.playerBoards.size(); j++) {
                  if (this.playerBoards.get((i + j) % this.playerBoards.size()).getValue())
                     return this.playerBoards.get((i + j) % this.playerBoards.size()).getKey().getUsername();
               }
      }
      throw new InvalidUsernameException();
   }

   /**
    * Returns the amount of the initial resources of the specified player
    *
    * @param username the username of the player
    * @return resources' number
    */
   public int initialResources(String username) {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername)
              .collect(Collectors.toList()).indexOf(username);
      switch(index) {
         case 1 : case 2:
            return 1;
         case 3:
            return 2;
      }
      return 0;
   }

   /**
    * Returns the amount of the initial faith of the specified player
    *
    * @param username the username of the player
    * @return faith' number
    */
   public int initialFaith(String username) {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername)
              .collect(Collectors.toList()).indexOf(username);
      switch(index) {
         case 2 : case 3:
            return 1;
      }
      return 0;
   }

   /**
    * Returns the player board of the specified player
    *
    * @param username the username of the player
    * @return the player board of the player
    * @throws InvalidUsernameException if the specified player doesn't exist
    */
   public PlayerBoard getPlayerBoard(String username) throws InvalidUsernameException {
      try {
         return playerBoards.stream().map(Pair::getKey).filter(playerBoard -> playerBoard.getUsername().equals(username))
                 .collect(Collectors.toList()).get(0);
      } catch (IndexOutOfBoundsException e) {
         throw new InvalidUsernameException();
      }
   }

   /**
    *
    * @return ordered usernames of all the payers in this game
    */
   public List<String> getOrderedPlayers() {
      return this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList());
   }

   /**
    * Sets the specified player as disconnected
    *
    * @param username the username of the player
    * @throws InvalidUsernameException if the specified player doesn't exist
    */
   public void disconnectPlayer(String username) throws InvalidUsernameException, NoConnectedPlayerException {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList()).indexOf(username);
      if(index < 0)
         throw new InvalidUsernameException();
      this.playerBoards.set(index, new Pair<>(this.playerBoards.get(index).getKey(), false));
      if(this.playerBoards.stream().noneMatch(Pair::getValue))
         throw new NoConnectedPlayerException();
   }

   /**
    * Sets the specified player as connected
    *
    * @param username the username of the player
    * @throws InvalidUsernameException if the specified player doesn't exist
    */
   public void reconnectPlayer(String username) throws InvalidUsernameException {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList()).indexOf(username);
      if(index < 0)
         throw new InvalidUsernameException();
      this.playerBoards.set(index, new Pair<>(this.playerBoards.get(index).getKey(), true));
      // TODO fare tutti gli phaseUpdate per mandare al player il modello
   }

   /**
    * Check if the specified player is connected or not
    *
    * @param username the username of the player
    * @return true if player is connected, false otherwise
    * @throws InvalidUsernameException if the specified player doesn't exist
    */
   public boolean isPlayerConnected(String username) throws InvalidUsernameException {
      return this.playerBoards.stream().filter(i -> i.getKey().getUsername().equals(username))
              .map(Pair::getValue).findFirst().orElseThrow(InvalidUsernameException::new);
   }

   /**
    * Check if the specified player is the first (among of connected players)
    *
    * @param username the username of the player
    * @return true if player is the first, false otherwise
    */
   public boolean isFirst(String username) {
      return this.playerBoards.stream().filter(Pair::getValue).collect(Collectors.toList())
              .get(0).getKey().getUsername().equals(username);
   }

   private List<Integer> idLeaderList(List<LeaderCard> leaderList) {
      return leaderList.stream().map(LeaderCard::getLeaderId).collect(Collectors.toList());
   }

   /**
    * Notify the game that the setup phase is finished
    */
   public void gameStarted() {
      this.gameStarted = true;
   }

   public boolean isGameStarted() {
      return this.gameStarted;
   }

   private void handleEndGame() {
      Pair<String, Integer> winnerAndScore;
      String winner = null;
      int score=0;
      int bResources=0;
      
      for(Pair<PlayerBoard, Boolean> p : playerBoards) {
         System.out.println(p.getKey().getUsername() + " " + p.getKey().returnScore());
         if (p.getKey().returnScore() > score) {
            bResources = p.getKey().getChest().totalNumberOfResources() + p.getKey().getWarehouse().totalResources();
            winner = p.getKey().getUsername();
            score = p.getKey().returnScore();
         }
         if (p.getKey().returnScore() == score) {
            int aResources = p.getKey().getChest().totalNumberOfResources() + p.getKey().getWarehouse().totalResources();
            if(aResources > bResources) {
               winner = p.getKey().getUsername();
               score = p.getKey().returnScore();
               bResources = aResources;
            }
         }
      }
      System.out.println(winner);
      winnerAndScore = new Pair<>(winner, score);
      controller.endGameUpdate(GSON.getGsonBuilder().toJson(winnerAndScore));
   }

   @Override
   public void notifyLeaderSetup(String username, LeaderSetup msg) {
      if (controller != null)
         controller.leaderSetupUpdate(username, msg);
   }

   @Override
   public void update() {
      this.endGame = true;
   }

}
