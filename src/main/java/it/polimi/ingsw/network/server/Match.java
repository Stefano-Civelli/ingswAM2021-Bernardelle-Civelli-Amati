package it.polimi.ingsw.network.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.controller.NetworkVirtualView;
import it.polimi.ingsw.controller.action.PlayerDisconnectionAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class contains reference to all the clientHandlers in order to manage at an high level of abstraction
 * the message sending process and the client disconnections inside of a match.
 * A match is a game room that can be created or joined by players using it's unique Id. Once the game has started
 * the match is no longer accessible by players. If a player disconnects from a match others can still play
 */
public class Match {

   private static final int SINGLE_PLAYER_NUMBER = 1;
   private static final int MAX_MULTIPLAYER_NUMBER = 4;

   private final String matchName;
   private int playersNumber = 0;
   private final Map<String, ServerClientHandler> usernameToClientHandler = new HashMap<>();
   private final List<ServerClientHandler> clients = new ArrayList<>();
   private boolean gameRunning = false;
   private TurnManager turnManager;
   private final Server server;

   public Match(String matchName, Server server) {
      this.matchName = matchName;
      this.server = server;
   }

   public int getPlayersNumber() {
      return playersNumber;
   }

   public String getMatchName() {
      return matchName;
   }

   /**
    * manages client interaction in the lobby creation phase
    * if the message sender is the first player send him a message
    * if he isn't the first player this methods tells all the other clients that a new player connected
    * if all the players required are logged this method will start the match
    *
    * @param message should be {@code MessageType.NUMBER_OF_PLAYERS || MessageType.LOGIN}
    */
   public synchronized void lobbySetup(Message message){
      MessageType messageType = message.getMessageType();
      String username = message.getUsername();

      if(playersNumber == 0 && messageType == MessageType.NUMBER_OF_PLAYERS){ //executed only for the first player to connect
         if(message.getPayload() == null)
            return; // pensare se mandare messaggio di errore
         int tempPlayerNum = Integer.parseInt(message.getPayload());
         if (tempPlayerNum < SINGLE_PLAYER_NUMBER || tempPlayerNum > MAX_MULTIPLAYER_NUMBER) {
            sendToClient(new Message(username, MessageType.ERROR, ErrorType.INVALID_NUMBER_OF_PLAYERS));
            return;
         }
         this.playersNumber = tempPlayerNum;
         sendToClient(new Message(username, MessageType.LOBBY_CREATED));
      }
      else if (messageType == MessageType.JOIN_MATCH || messageType == MessageType.CREATE_MATCH ) {
         List<String> tmpConnectdPlyrs = loggedPlayers();
         tmpConnectdPlyrs.remove(username); // to tell everybody except the player that connected
         for(String user: tmpConnectdPlyrs)
            sendToClient(new Message(user, MessageType.OTHER_USER_JOINED, Integer.toString(NumberOfRemainingLobbySlots())));

         sendToClient(new Message(username, MessageType.YOU_JOINED, Integer.toString(NumberOfRemainingLobbySlots()))); //sent to the player that joined
      }

      if (usernameToClientHandler.size() == playersNumber)
         start();
   }

   /**
    * handles the login message registering the player to the server.
    * Sends a message to the client with feedback on the login procedure (E.g. login successful or login error).
    * Also handles reconnection if the login message is sent by a player that has disconnected during the game
    *
    * @param message login message sent by the client that wants to login
    * @param clientHandler that wants to login
    */
   public synchronized void handleLogin(Message message, ServerClientHandler clientHandler){
      String username = message.getUsername();

      if(gameRunning){
         clientHandler.sendMessage(new Message(MessageType.ERROR, ErrorType.GAME_ALREADY_STARTED));
         handleClientDisconnection(clientHandler); //TODO probabilmente andrà modificata un pochino
         return;
      }

      if(username == null || username.equals("")) {
         clientHandler.sendMessage(new Message(MessageType.ERROR, ErrorType.INVALID_LOGIN_USERNAME));
         return;
      }

      if (isTaken(username))
         clientHandler.sendMessage(new Message(MessageType.ERROR, ErrorType.INVALID_LOGIN_USERNAME));
      else{
         if(isFirst()) {
            succesfulLogin(username, clientHandler);
            clientHandler.sendMessage(new Message(MessageType.NUMBER_OF_PLAYERS));
         } else if(playersNumber == 0) {        //il player non è il primo a loggarsi ma il numero di giocatori è ancora 0 -> significa che qualcuno sta creando la lobby
            clientHandler.sendMessage(new Message(MessageType.WAIT_FOR_LOBBY_CREATION, "A player is creating the lobby, try again in a few seconds"));
         } else {
            succesfulLogin(username, clientHandler);
            lobbySetup(message);
         }
      }

   }



   private void succesfulLogin(String username, ServerClientHandler clientHandler) {
      addClient(clientHandler);
      clientHandler.setUsername(username);
      clientHandler.setLogged(true);
      usernameToClientHandler.put(username, clientHandler);
      clientHandler.sendMessage(new Message(username, MessageType.LOGIN_SUCCESSFUL));
   }


   private int NumberOfRemainingLobbySlots() {
      return playersNumber - loggedPlayers().size();
   }

   private void start() {
      sendToClient(new Message(MessageType.STARTING_GAME_SETUP));
      Game game = null; //TODO sarebbe merglio avere solo controller qua
      List<String> playersInOrder = null;
      boolean singlePlayer = (playersNumber == 1);
      ModelObserver virtualView = new NetworkVirtualView(this);

      // problema: al game server turnmanager per fare gli phaseUpdate e a TurnManager serve game
      // altro problema: mando gli phaseUpdate del modello prima del messaggio startGame
      try {
         if(singlePlayer)
            game = new SinglePlayer(virtualView);
         else
            game = new Game(virtualView);
      }catch(IOException | JsonSyntaxException e){ //TODO controllare se viene lanciata la JsonSyntaxException
         //TODO bisogna chiudere la partita (disconnetto tutti i client 1 per volta dicendo "Errore nei file di configurazione del gioco")
         sendToClient(new Message(MessageType.GENERIC_MESSAGE, e.getStackTrace()));
         return; //TODO è un po alla cazzo per non far sottoilineare initialMoveForward
      }

      try {
         this.turnManager = new TurnManager(game, loggedPlayers());
         playersInOrder = turnManager.startGame();
      }catch (IOException e) {
         System.out.println("playerboard constructor probably has a problem");
         e.printStackTrace();
      } catch (MaximumNumberOfPlayersException e) {
         System.out.println("added more than 1 player in singleplayer"); //TODO dovrei fare altro quando ho un errore così grave ?
         e.printStackTrace();
      }
      sendToClient(new Message(MessageType.GAME_STARTED, playersInOrder));
      game.initialMoveForward();
      gameRunning = true;
   }


   /**
    * sends phaseUpdate message only to the current player
    *
    * @param message the phaseUpdate message (username can be null)
    */
   public void matchSingleUpdate(Message message){
      sendToClient(message);
   }

   /**
    * sends broadcast phaseUpdate message
    *
    * @param message the phaseUpdate message (username can be null)
    */
   public void matchBroadcastUpdate(Message message){
      boolean setupMessage = message.getMessageType().equals(MessageType.DECK_SETUP) || message.getMessageType().equals(MessageType.MARKET_SETUP);
      if(!setupMessage && message.getUsername() == null) {
         message.setUsername(turnManager.getCurrentPlayer());
      }
      sendBroadcast(message);
   }

   /**
    * Adds a clientHandler to the clients list
    *
    * @param client the {@link ServerClientHandler} to be added
    */
   public synchronized void addClient(ServerClientHandler client){
      clients.add(client);
   }

   /**
    * returns true if the the specified username is already taken by another client
    *
    * @param username the username whose availability is to be checked
    * @return true if the username is already taken
    */
   public boolean isTaken(String username){
      for(ServerClientHandler s : clients)
         if(username.equals(s.getUsername()))
            return true;

      return false;
   }

   /**
    * Sends the specified message.
    * If username is null sends broadcast, otherwise sends only to the client associated with the username
    *
    * @param message the message to be sent
    */
   public void sendToClient(Message message) { //quando entro qua i messaggi a cui serve l'username lo hanno, gli altri no

      String username = message.getUsername();
      if(username == null){              //messaggi senza username vengono inviati a tutti
         for(ServerClientHandler s : clients)
            s.sendMessage(message);
         return;
      }
      else
         usernameToClientHandler.get(username).sendMessage(message);

   }

   /**
    * Sends the specified message to every connected client
    *
    * @param message message to send
    */
   public void sendBroadcast(Message message) {
      for(ServerClientHandler s : clients)
         s.sendMessage(message);
   }




   private boolean isFirst() {
      return loggedPlayers().isEmpty();
   }


   /**
    * Removes the specified client from the clients list and the clients Map
    *
    * @param client the clientHandler to be removed
    */
   public synchronized void deleteClient(ServerClientHandler client) {
      clients.remove(client);
      usernameToClientHandler.remove(client.getUsername());
   }

   /**
    * Returns a list of the logged players in the match
    * NOTE: if a player disconnects he is considered still logged if he actually logged in successfully
    *
    * @return the logged players list
    */
   public synchronized List<String> loggedPlayers(){
      return clients.stream()
              .filter(ServerClientHandler::isLogged)
              .map(ServerClientHandler::getUsername)
              .collect(Collectors.toList());
   }


   public TurnManager getTurnManager() {
      return turnManager;
   }

   /**
    * returns true if the game is running
    * @return true if the game is running
    */
   public boolean isGameRunning() {
      return gameRunning;
   }

   /**
    * If the game is not running this method deletes the client
    * Sends to Client a disconnected message if the disconnection is caused by the server
    *
    * @param disconnectedClient the clientHandler that disconnected or that needs to be disconnected
    */
   protected void handleClientDisconnection(ServerClientHandler disconnectedClient) {
      if(!gameRunning) { //if the game isn't started delete the player from the list and forget about him
         deleteClient(disconnectedClient);
      }
      try {
         if (disconnectedClient.isLogged()) {
            Message errorOrEndTurn = null;
            if(turnManager != null) // it's null if the game isn't started
               errorOrEndTurn = turnManager.handleAction(new PlayerDisconnectionAction(disconnectedClient.getUsername()));
            if(errorOrEndTurn != null)
               disconnectedClient.actionAnswereMessage(errorOrEndTurn);
            sendBroadcast(new Message(disconnectedClient.getUsername(), MessageType.DISCONNECTED));
         }
      } catch (NoConnectedPlayerException ignored) {
         // Don't print stack trace
      }
      finally {
         disconnectedClient.closeSocket();
      }
      if(this.clients.stream().noneMatch(ServerClientHandler::isConnected)) {
         System.out.println("NO CONNECTED PLAYERS");
         this.deleteMatch();
      }
   }

   /**
    * Delete the match closing all sockets and deleting it from the server reference list
    */
   public void deleteMatch() {
      this.clients.forEach(ServerClientHandler::closeSocket);
//      this.usernameToClientHandler.clear();
//      this.clients.clear();
//      this.gameRunning = false;
//      this.playersNumber = 0;
//      this.turnManager = null;
      server.deleteMatch(this.matchName);
      System.out.println("GAME FINISHED.");
   }

}
