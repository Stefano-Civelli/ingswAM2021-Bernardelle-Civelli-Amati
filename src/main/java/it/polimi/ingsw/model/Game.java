package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
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

   public Game(ModelObserver controller) throws IOException {

      this.leaderCardDeck = GSON.leaderCardParser(ConfigParameters.leaderCardConfigFile);
      this.developCardDeck = GSON.cardParser(ConfigParameters.cardConfigFile);
      this.developCardDeck.finalizeDeckSetup(controller);
      this.market = new Market(controller);
      this.playerBoards = new ArrayList<>();
      this.controller = controller;
   }

   //need also to check that the max number of players in this lobby isn't exceeded -> that's not necessary
   public void addPlayer(String username) throws IOException {
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

   public String startGame() {
      Collections.shuffle(this.playerBoards);
      this.playerBoards.stream().map(Pair::getKey).collect(Collectors.toList())
              .forEach(playerBoard -> playerBoard.getTrack().moveForward(initialFaith(playerBoard.getUsername())));
      return this.playerBoards.get(0).getKey().getUsername();
   }

   public String nextConnectedPlayer(String currentPlayer) throws InvalidUsernameException {
      for(int i = 0; i < this.playerBoards.size(); i++)
         if(this.playerBoards.get(i).getKey().getUsername().equals(currentPlayer))
            for(int j = 1; j <= this.playerBoards.size(); j++ ) {
               if (this.playerBoards.get((i + j) % this.playerBoards.size()).getValue())
                  return this.playerBoards.get((i + j) % this.playerBoards.size()).getKey().getUsername();
            }
      throw new InvalidUsernameException();
   }

   public int initialResources(String username) {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername)
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
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername)
              .collect(Collectors.toList()).indexOf(username);
      switch(index) {
         case 3 : case 4:
            return 1;
      }
      return 0;
   }

   public PlayerBoard getPlayerBoard(String username) throws InvalidUsernameException {
      try {
         return playerBoards.stream().map(Pair::getKey).filter(playerBoard -> playerBoard.getUsername().equals(username))
                 .collect(Collectors.toList()).get(0);
      } catch (IndexOutOfBoundsException e) {
         throw new InvalidUsernameException();
      }
   }

   public List<String> getOrderedPlayers() {
      return this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList());
   }

   public void disconnectPlayer(String username) throws InvalidUsernameException {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList()).indexOf(username);
      if(index < 0)
         throw new InvalidUsernameException();
      this.playerBoards.set(index, new Pair<>(this.playerBoards.get(index).getKey(), false));
   }

   public void reconnectPlayer(String username) throws InvalidUsernameException {
      int index = this.playerBoards.stream().map(Pair::getKey).map(PlayerBoard::getUsername).collect(Collectors.toList()).indexOf(username);
      if(index < 0)
         throw new InvalidUsernameException();
      this.playerBoards.set(index, new Pair<>(this.playerBoards.get(index).getKey(), true));
      // TODO fare tutti gli update per mandare al player il modello
   }

   public boolean isPlayerConnected(String username) throws InvalidUsernameException {
      return this.playerBoards.stream().filter(i -> i.getKey().getUsername().equals(username))
              .map(Pair::getValue).findFirst().orElseThrow(InvalidUsernameException::new);
   }

   public boolean isFirst(String username) {
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
