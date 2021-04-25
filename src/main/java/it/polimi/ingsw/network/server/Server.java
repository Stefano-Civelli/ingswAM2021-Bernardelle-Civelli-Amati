package it.polimi.ingsw.network.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

/**
 * This class accepts connection to a client and assign the client handling
 * to the ServerClientHandler class via Thread.
 */
public class Server implements ModelObserver {

   public static final int MIN_PORT_NUMBER = 1024;
   public static final int MAX_PORT_NUMBER = 65535;
   private static final int SINGLE_PLAYER_NUMBER = 1;
   private static final int MAX_MULTIPLAYER_NUMBER = 4;
   private Map<String, ServerClientHandler> usernameToClientHandler = new HashMap<>();
   private List<ServerClientHandler> connectedClients = new ArrayList<>(); // utente aggiunto a questa lista appena viene creato il socket, non ha ancora un username assegnato
   // contains all the connected clients, no matter if they are logged (comodo usarla per pingare)
   private TurnManager turnManager;
   private int playersNumber = 0;
   private List<String> loggedPlayers; //si sono già anche loggati oltre che connessi (cioè hanno inserito l'username)


   public static void main(String[] args) {
      int serverPortNumber;


      if (args.length == 1) {
         serverPortNumber = Integer.parseInt(args[0]);
      } else {
         Scanner in = new Scanner(System.in);
         //can create a debug configuration
         System.out.println("Set server port number on which to accept communication");
         serverPortNumber = Integer.parseInt(in.nextLine());
                 //integerImputValidation(stdin, MIN_PORT_NUMBER, MAX_PORT_NUMBER);
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
            //clientSocket.setSoTimeout(ConfigParameters.SERVER_TIMEOUT * 1000); // NEEDED TO REALIZE THAT A CLIENT HAS DISCONNECTED UNCLEANLY
            ServerClientHandler clientHandler = new ServerClientHandler(clientSocket, server);
            new Thread(clientHandler).start();
         } catch (IOException e) {
            System.out.println("ERROR: Connection dropped");
         }
      }
   }


   //manages other players connection
   public synchronized void lobbySetup(Message message){ //TODO gestire lobby piena
      String username = message.getUsername();

      if(playersNumber == 0){ //executed only for the first player to connect
         int tempPlayerNum = Integer.parseInt(message.getPayload());
         if (tempPlayerNum < SINGLE_PLAYER_NUMBER || tempPlayerNum > MAX_MULTIPLAYER_NUMBER) {
            sendToClient(new Message(MessageType.NUMBER_OF_PLAYERS_FAILED, username, "You need to select a valid number of players, It must be between " + SINGLE_PLAYER_NUMBER + " and " + MAX_MULTIPLAYER_NUMBER));
            return;
         }
         playersNumber = tempPlayerNum;
         sendToClient(new Message(MessageType.LOBBY_CREATED, username));
      }
      else
      {
         List<String> tmpConnectdPlyrs = new ArrayList<>(loggedPlayers);
         tmpConnectdPlyrs.remove(username); // to tell everybody except the player that connected
         for(String user: tmpConnectdPlyrs)
            sendToClient(new Message(MessageType.OTHER_USER_JOINED, user, Integer.toString(loggedPlayers.size() - playersNumber)));

         sendToClient(new Message(MessageType.YOU_JOINED, username, Integer.toString(loggedPlayers.size() - playersNumber))); //sent to the player that joined

         if (loggedPlayers.size() == playersNumber) {
            start();
         }
      }
   }

   public synchronized void handleLogin(Message message, ServerClientHandler serverClientHandler){ //TODO pensare se metterlo nell' handler
      String username = message.getUsername();

      if(playersNumber != 0 && NumberOfRemainingLobbySlots() == 0){ // non viene mai fatto sul primo player
         serverClientHandler.sendMessage(new Message(MessageType.LOGIN_FAILED, "ERROR: lobby is full"));
         return;
      }

      if(username == null || username.equals("")) {
         serverClientHandler.sendMessage(new Message(MessageType.LOGIN_FAILED, "ERROR: you need to enter a username"));
         return;
      }

      if (isTaken(username))
         serverClientHandler.sendMessage(new Message(MessageType.LOGIN_FAILED, "ERROR: this username is already taken"));
      else{
         if(isFirst()) {
            succesfulLogin(username, serverClientHandler);
            serverClientHandler.sendMessage(new Message(MessageType.NUMBER_OF_PLAYERS, "How many players do you want to play with?"));
         } else if(playersNumber == 0) { //il player non è il primo a loggarsi ma il numero di giocatori è ancora 0 -> significa che qualcuno sta creando la lobby
            //mando messaggio che dice di aspettare che venga creata la lobby
            serverClientHandler.sendMessage(new Message(MessageType.WAIT_FOR_LOBBY_CREATION, "A player is creating the lobby, wait a few seconds"));
         } else { //fatto quando il player non è il primo e la lobby esiste già
            succesfulLogin(username, serverClientHandler);
            lobbySetup(message);
         }
      }
   }

   private void succesfulLogin(String username, ServerClientHandler serverClientHandler) { //TODO serve sincronizzare anche questo?
      serverClientHandler.setUsername(username);
      usernameToClientHandler.put(username, serverClientHandler);
      loggedPlayers.add(username);
      serverClientHandler.sendMessage(new Message(MessageType.LOGIN_SUCCESSFUL));
      serverClientHandler.setLogged(true);
   }

   private int NumberOfRemainingLobbySlots() {
      return playersNumber - loggedPlayers.size();
   }

   private void start() {
      Game game = null;
      try {
         if(playersNumber == 1)
            game = new SinglePlayer();
         if(playersNumber > 1)
            game = new Game();
      }catch(IOException | JsonSyntaxException e){ //TODO controllare se viene lanciata la JsonSyntaxException
         //TODO bisogna chiudere la partita (disconnetto tutti i client 1 per volta dicendo Errore nei file di configurazione del gioco)
         sendToClient(new Message(MessageType.GENERIC_MESSAGE));
      }

      try {
         turnManager = new TurnManager(game, loggedPlayers);
      }catch (IOException e) {
         //TODO sistemare il costruttore di playerboard
         e.printStackTrace();
      }
      sendToClient(new Message(MessageType.GAME_STARTED));
   }


   @Override
   public void singleUpdate(Message message){
      message.setUsername(turnManager.getCurrentPlayer());
      sendToClient(message);
   }

   @Override
   public void broadcastUpdate(Message message){
      message.setUsername(turnManager.getCurrentPlayer());
      sendBroadcast(message);
   }



   /**
    * Adds a client to the connectedClients list
    *
    * @param client the {@link ServerClientHandler} to be added
    */
   public synchronized void addClient(ServerClientHandler client){ //TODO per scrivere devo sincronizzare?

      connectedClients.add(client);
   }


   public boolean isTaken(String username){
      for(ServerClientHandler s : connectedClients)
         if(s.getUsername().equals(username))
            return true;

      return false;
   }




   public void sendToClient(Message message) { //quando entro qua i messaggi a cui serve l'username lo hanno, gli altri no

      String username = message.getUsername();
      if(username == null){              //messaggi senza username vengono inviati a tutti
         for(ServerClientHandler s : connectedClients)
            s.sendMessage(message);
         return;
      }
      else
         usernameToClientHandler.get(username).sendMessage(message);

   }

   public void sendBroadcast(Message message) {
      for(ServerClientHandler s : connectedClients)
         s.sendMessage(message);
   }

   //TODO validazione input
   private static int integerInputValidation(Scanner stdin, int minPortNumber, int maxPortNumber) {
      return 0;
   }

   public TurnManager getTurnManager() {
      return turnManager;
   }

   public synchronized boolean isFirst() {
      return loggedPlayers.isEmpty();
   }


}
