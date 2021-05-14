package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Game implements ModelObservable {

   private final LeaderCardDeck leaderCardDeck;
   private final Market market;
   protected final DevelopCardDeck developCardDeck;
   private final List<Pair<PlayerBoard, Boolean>> playerBoards;

   private final transient ModelObserver controller;

   /**
    * Create a new multi player game
    *
    * @param controller the observer of the model
    * @throws IOException if there are some problems loading the configuration files
    */
   public Game(ModelObserver controller) throws IOException {

      this.leaderCardDeck = GSON.leaderCardParser(ConfigParameters.leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(ConfigParameters.cardConfigFile);
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
      // TODO need also to check that the max number of players in this lobby isn't exceeded -> that's not necessary
      List<LeaderCard> fourInitialLeaderCardsForPlayer = this.leaderCardDeck.drawFourCards();

      PlayerBoard playerBoard = new PlayerBoard(username, fourInitialLeaderCardsForPlayer, market, developCardDeck);

      // add all observers to the list in observable classes
      for(PlayerBoard playerBoardSetObserver : playerBoards.stream().map(Pair::getKey).collect(Collectors.toList())) {
         playerBoardSetObserver.getTrack().addToVaticanReportObserverList(playerBoard.getTrack());
         playerBoard.addToMoveForwardObserverList(playerBoardSetObserver.getTrack());
         playerBoardSetObserver.addToMoveForwardObserverList(playerBoard.getTrack());
      }

      playerBoard.setController(controller);
      playerBoards.add(new Pair<>(playerBoard, true));
      notifyModelChange(new Message(username, MessageType.LEADERCARD_SETUP, idLeaderList(fourInitialLeaderCardsForPlayer)));
   }

   /**
    * Starts this game
    *
    * @return the username of the first player
    */
   public String startGame() {
      Collections.shuffle(this.playerBoards);
      this.playerBoards.stream().map(Pair::getKey).collect(Collectors.toList())
              .forEach(playerBoard -> playerBoard.getTrack().moveForward(initialFaith(playerBoard.getUsername())));
      return this.playerBoards.get(0).getKey().getUsername();
   }

   /**
    * Returns the player that must play after the specified player (presumably the current player)
    * @param currentPlayer username of the current player
    * @return username of next connected player
    * @throws InvalidUsernameException if the specified player doesn't exist
    */
   public String nextConnectedPlayer(String currentPlayer) throws InvalidUsernameException {
      for(int i = 0; i < this.playerBoards.size(); i++)
         if(this.playerBoards.get(i).getKey().getUsername().equals(currentPlayer))
            for(int j = 1; j <= this.playerBoards.size(); j++ ) {
               if (this.playerBoards.get((i + j) % this.playerBoards.size()).getValue())
                  return this.playerBoards.get((i + j) % this.playerBoards.size()).getKey().getUsername();
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
         case 3 : case 4:
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
   public void disconnectPlayer(String username) throws InvalidUsernameException {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList()).indexOf(username);
      if(index < 0)
         throw new InvalidUsernameException();
      this.playerBoards.set(index, new Pair<>(this.playerBoards.get(index).getKey(), false));
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
      // TODO fare tutti gli update per mandare al player il modello
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
    * Check if the specified player is the first
    *
    * @param username the username of the player
    * @return true if player is the first, false otherwise
    */
   public boolean isFirst(String username) {
      // FIXME meglio controllare se è il primo CONNESSO?
      return this.playerBoards.get(0).getKey().getUsername().equals(username);
   }

   private List<Integer> idLeaderList(List<LeaderCard> leaderList){
      return leaderList.stream().map(LeaderCard::getLeaderId).collect(Collectors.toList());
   }

   @Override
   public void notifyModelChange(Message msg) {
      if (controller != null)
         controller.singleUpdate(msg);
   }

}
