package it.polimi.ingsw.network.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.controller.LocalVirtualView;
import it.polimi.ingsw.controller.NetworkVirtualView;
import it.polimi.ingsw.controller.action.PlayerDisconnectionAction;
import it.polimi.ingsw.controller.action.PlayerReconnectionAction;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class accepts connection to a client and assign the client handling to the ServerClientHandler class via Thread.
 * Server class also contains reference to all the clientHandlers in order to manage at an high level of abstraction
 * the message sending process and the client disconnections.
 */
public class Server {

   public static final int MIN_PORT_NUMBER = 1024;
   public static final int MAX_PORT_NUMBER = 65535;
   private static final int SINGLE_PLAYER_NUMBER = 1;
   private static final int MAX_MULTIPLAYER_NUMBER = 4;
   private final Map<String, ServerClientHandler> usernameToClientHandler = new HashMap<>(); //l'opposto non serve perchè ho la get username sul clientHandler
   private final List<ServerClientHandler> clients = new ArrayList<>(); //contiene tutti i client che si sono connessi al server, se uno lascia prima che la paritta inizia lo tolgo
   //private final List<String> connectedAndDisconnectedClients = new ArrayList<>();// utente aggiunto a questa lista appena viene creato il socket, non ha ancora un username assegnato
   // contains all the clients, no matter if they are connected
   private boolean gameRunning = false;
   private int playersNumber = 0;
   private TurnManager turnManager;
   // loggedplayers() e clients sono diverse solo se qualcuno levva durante la partita


   public static void main(String[] args) {
      int serverPortNumber;

      if (args.length == 1) {
         serverPortNumber = Integer.parseInt(args[0]);
         System.out.println("Server started on port: " + serverPortNumber);
      } else if(ConfigParameters.TESTING) {
         serverPortNumber = 6754;
         System.out.println("starting server in TESTING configuration on port " + serverPortNumber);
      }
      else{
         Scanner in = new Scanner(System.in);
         System.out.print("Set server port number: ");
         serverPortNumber = integerInputValidation(in, MIN_PORT_NUMBER, MAX_PORT_NUMBER);
      }

      ServerSocket socket;
      try {
         socket = new ServerSocket(serverPortNumber);
         System.out.println("Waiting for players to connect...");
      } catch (IOException e) {
         System.out.println("ERROR: Impossible to open server socket");
         System.exit(1);
         return;
      }

      Server server = new Server();
      while (true) {
         try {
            /* accepts connections; for every connection accepted,
             * create a new Thread executing a ClientHandler */
            Socket clientSocket = socket.accept();
            clientSocket.setSoTimeout(ConfigParameters.SERVER_TIMEOUT); // NEEDED TO REALIZE THAT A CLIENT HAS DISCONNECTED UNCLEANLY
            ServerClientHandler clientHandler = new ServerClientHandler(clientSocket, server);
            new Thread(clientHandler).start();
         } catch (IOException e) {
            System.out.println("ERROR: Connection dropped");
         }
      }
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
         playersNumber = tempPlayerNum;
         sendToClient(new Message(username, MessageType.LOBBY_CREATED));
      }
      else if (messageType == MessageType.LOGIN) {
         List<String> tmpConnectdPlyrs = loggedPlayers();
         tmpConnectdPlyrs.remove(username); // to tell everybody except the player that connected
         for(String user: tmpConnectdPlyrs)
            sendToClient(new Message(user, MessageType.OTHER_USER_JOINED, Integer.toString(NumberOfRemainingLobbySlots())));

         sendToClient(new Message(username, MessageType.YOU_JOINED, Integer.toString(NumberOfRemainingLobbySlots()))); //sent to the player that joined
      }

      if (loggedPlayers().size() == playersNumber)
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
      boolean isReconnecting = isTaken(username) && gameRunning &&
              !usernameToClientHandler.get(message.getUsername()).isConnected();

      if(isReconnecting) {
         handleClientReconnection(message, clientHandler);
         return;
      }

      if(gameRunning){
         clientHandler.sendMessage(new Message(MessageType.ERROR, ErrorType.GAME_ALREADY_STARTED));
         handleClientDisconnection(clientHandler);
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
      clientHandler.setUsername(username);
      clientHandler.setLogged(true);
      usernameToClientHandler.put(username, clientHandler);
      clientHandler.sendMessage(new Message(username, MessageType.LOGIN_SUCCESSFUL));
   }

   private void handleClientReconnection(Message message,ServerClientHandler newClientHandler) {
      ServerClientHandler oldClientHandler = usernameToClientHandler.get(message.getUsername());
      deleteClient(oldClientHandler);
      addClient(newClientHandler);
      newClientHandler.setUsername(message.getUsername());
      usernameToClientHandler.put(newClientHandler.getUsername(), newClientHandler);
      newClientHandler.sendMessage(new Message(MessageType.RECONNECTED));
      try {
         turnManager.handleAction(new PlayerReconnectionAction(newClientHandler.getUsername()));
      } catch (NoConnectedPlayerException e) {
         e.printStackTrace();
      }
   }

   private int NumberOfRemainingLobbySlots() {
      return playersNumber - loggedPlayers().size();
   }

   private void start() {
      sendToClient(new Message(MessageType.STARTING_GAME_SETUP));
      Game game = null; //TODO sarebbe merglio avere solo controller qua
      List<String> playersInOrder = null;
      boolean singlePlayer = (playersNumber == 1);
      //if(local)
      ModelObserver virtualView = new NetworkVirtualView(this);
      //else TODO fare per il local
         //ModelObserver virtualView = new LocalVirtualView(this, loggedPlayers().get(0));

      // problema: al game server turnmanager per fare gli update e a TurnManager serve game
      // altro problema: mando gli update del modello prima del messaggio startGame
      try {
         if(singlePlayer)
            game = new SinglePlayer(virtualView);
         else
            game = new Game(virtualView);
      }catch(IOException | JsonSyntaxException e){ //TODO controllare se viene lanciata la JsonSyntaxException
         //TODO bisogna chiudere la partita (disconnetto tutti i client 1 per volta dicendo Errore nei file di configurazione del gioco)
         sendToClient(new Message(MessageType.GENERIC_MESSAGE));
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
    * sends update message only to the current player
    *
    * @param message the update message (username can be null)
    */
   public void serverSingleUpdate(Message message){
      sendToClient(message);
   }

   /**
    * sends broadcast update message
    *
    * @param message the update message (username can be null)
    */
   public void serverBroadcastUpdate(Message message){
      boolean setupMessage = message.getMessageType().equals(MessageType.DECK_SETUP) || message.getMessageType().equals(MessageType.MARKET_SETUP);
      if(!setupMessage && message.getUsername() == null)
         message.setUsername(turnManager.getCurrentPlayer());
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


   private static int integerInputValidation(Scanner in, int minPortNumber, int maxPortNumber) {
      boolean error = false;
      int input = 0;
      try {
         input = Integer.parseInt(in.nextLine());
      }catch(NumberFormatException e){
         error = true;
      }

      while(error || input < minPortNumber || input > maxPortNumber){
         error = false;
         System.out.print("input must be between " + minPortNumber + " and " + maxPortNumber + ". try again: ");
         try {
            input = Integer.parseInt(in.nextLine());
         }catch(NumberFormatException e){
            error = true;
         }
      }
      return input;
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

   //TODO probabilmente va usata
   public synchronized List<ServerClientHandler> connectedPlayers(){
      return clients.stream()
              .filter(ServerClientHandler::isConnected)
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
      if(!gameRunning) //if the game isn't started delete the player from the list and forget about him
         deleteClient(disconnectedClient);

      try {
         if (disconnectedClient.isLogged()) {
            sendBroadcast(new Message(disconnectedClient.getUsername(), MessageType.DISCONNECTED));
            Message errorOrEndTurn = turnManager.handleAction(new PlayerDisconnectionAction(disconnectedClient.getUsername()));
            disconnectedClient.actionAnswereMessage(errorOrEndTurn);
         }
      } catch (NoConnectedPlayerException e) {
         System.out.println("NO CONNECTED PLAYERS");
         this.resetServer();
      }
      finally {
         disconnectedClient.closeSocket();
         disconnectedClient.setConnected(false); //needed to handle reconnection
      }
   }

   public void resetServer() {
      this.clients.forEach(ServerClientHandler::closeSocket);
      this.usernameToClientHandler.clear();
      this.clients.clear();
      this.gameRunning = false;
      this.playersNumber = 0;
      this.turnManager = null;
      System.out.println("GAME FINISHED.");
   }

}
