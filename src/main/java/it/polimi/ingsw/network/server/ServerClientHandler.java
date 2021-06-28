package it.polimi.ingsw.network.server;

import com.google.gson.*;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.NegativeQuantityException;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;

import java.io.*;

import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Every instance of this class handles the server side connection of a specific client
 */
public class ServerClientHandler implements Runnable {
   private final Socket clientSocket;
   private final Server server;
   private BufferedReader in;
   private PrintWriter out;

   private Match match = null;
   private String username;
   private boolean connected; // Default: true
   private boolean logged; // set to true when the players logs in
   //there is no need to set it to false when player disconnects because
   // when he reconnects that will be with another socket

   ServerClientHandler(Socket client, Server server) {
      this.clientSocket = client;
      this.server = server;
      this.connected = true;
      this.logged = false;
      this.username = null;
   }


   @Override
   public void run() {
      try {
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         out = new PrintWriter(clientSocket.getOutputStream());
         startPinging();
         handleClientConnection();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void handleClientConnection() throws IOException {

      System.out.println("Connected to " + clientSocket.getInetAddress());

      try {
         int errorCounter = 0;
         while (true) {
            try {
               String receivedString = in.readLine();
               Message message = messageParser(receivedString);
               if(message.getMessageType() != MessageType.PING)
                  System.out.println("LOG match " + (this.match == null ? "" : this.match.getMatchName()) + ": Recieved: " + receivedString);
               messageReceived(message);
               errorCounter = 0;
            }catch(JsonSyntaxException e){
               errorCounter++;
               sendMessage(new Message(MessageType.ERROR, ErrorType.MALFORMED_MESSAGE));
               if(errorCounter >= ConfigParameters.MAX_CONSECUTIVE_MALFORMED_MESSAGE) {
                  System.out.println("LOG: " + this.clientSocket.getInetAddress()
                          + " sent " + ConfigParameters.MAX_CONSECUTIVE_MALFORMED_MESSAGE
                          + " consecutive malformed message. This socket will be closed. ");
                  if(this.match != null) {
                     match.handleClientDisconnection(this);
                  } else {
                     this.clientSocket.close();
                     this.connected = false;
                  }
                  return;
               }
            }
         }//while

      } catch(IOException e) { //client crashes or timeout runs out
         if(e instanceof SocketTimeoutException)
            System.out.print("TIMEOUT: ");
         System.out.println("connection lost on client: " + clientSocket.getInetAddress() + "  --username: " + username);
         if(this.match != null)
            match.handleClientDisconnection(this);
         else {
            this.clientSocket.close();
            this.connected = false;
         }
      }
   }


   //Called when the ClientHandler receive a message from a client
   private void messageReceived(Message message) {
      if((username != null && !username.equals(message.getUsername())) || message.getMessageType() == null)
         return;

      switch (message.getMessageType()) {

         case CREATE_MATCH:
            if(server.matchIdPresent(message.getPayloadByType(String.class)))
               sendMessage(new Message(MessageType.ERROR, ErrorType.MATCH_ALREADY_EXISTS));
            else {
               this.match = server.createNewMatch(message.getPayloadByType(String.class));
               this.match.addClient(this);
               this.match.handleLogin(message, this);
            }
            break;

         case JOIN_MATCH:
            if(logged)
               return;
            if(server.matchIdPresent(message.getPayloadByType(String.class)) && this.match == null) {
               this.match = server.assignToMatch(message.getPayloadByType(String.class));
               if(this.match != null) {
                  this.match.addClient(this);
                  this.match.handleLogin(message, this);
               }
               else
                  sendMessage(new Message(MessageType.WAIT_FOR_LOBBY_CREATION, "A player is creating the lobby, try again in a few seconds"));
            }
            else
               sendMessage(new Message(MessageType.ERROR, ErrorType.CANNOT_JOIN_MATCH));
            break;

         case NUMBER_OF_PLAYERS:
            if(!logged)
               return;
            match.lobbySetup(message);
            break;

         case PING:
            break;

         case QUIT:
            match.handleClientDisconnection(this);
            break;

         case ACTION:
            if(!match.isGameRunning())
               return;
            Action action = message.getAction();
            try {
               Message errorOrEndTurn = match.getTurnManager().handleAction(action);
               if(errorOrEndTurn != null)
                  actionAnswereMessage(errorOrEndTurn);
            } catch (NoConnectedPlayerException e) {
               //This code should never be executed
               e.printStackTrace();
            }
            break;
         case CHEAT:
            if(!ConfigParameters.TESTING)
               return;
            activateCheats();
            break;
         default:

      }//switch
   }

   private void activateCheats(){
      try {
         Chest tempChest = match.getTurnManager().getGame().getPlayerBoard(match.getTurnManager().getCurrentPlayer()).getChest();
         tempChest.addResources(ResourceType.SHIELD, 50);
         tempChest.addResources(ResourceType.STONE, 50);
         tempChest.addResources(ResourceType.SERVANT, 50);
         tempChest.addResources(ResourceType.GOLD, 50);
         tempChest.endOfTurnMapsMerge();
         match.getTurnManager().getGame().getPlayerBoard(match.getTurnManager().getCurrentPlayer()).getTrack().moveForward(23);
      } catch (InvalidUsernameException | NegativeQuantityException | AbuseOfFaithException e) {e.printStackTrace();}
   }

   /**
    * this method should be called to handle the message returned
    * by the handleAction method in turnManager class
    *
    * @param answerMessage the message returned by the handleAction method in turnManager
    */
   public void actionAnswereMessage(Message answerMessage){
      MessageType messageType = answerMessage.getMessageType();

      switch (messageType){
         case ERROR:
            sendMessage(answerMessage);
            break;
         case NEXT_TURN_STATE: //questa cosa non serve dirla al client, basta dirgli tipo turno finito a tutti e basta, poi loro con la logica capiscono cosa fare
            match.sendBroadcast(answerMessage);
            break;
      }
   }

   /**
    * Sends the message to the client associated with
    * this ClientHandler trough the socket
    *
    * @param message the message to be sent
    */
   protected synchronized void sendMessage(Message message) {
      if(!connected)
         return;
      String jsonMessage = GSON.getGsonBuilder().toJson(message);
      if(message.getMessageType() != MessageType.PING)
         System.out.println("LOG match " + (this.match == null ? "" : this.match.getMatchName()) + ": Sent: " + jsonMessage);
      jsonMessage = jsonMessage.replaceAll("\n", " "); //remove all newlines before sending the message
      out.println(jsonMessage);
      out.flush();
   }


   private Message messageParser(String jsonMessage) throws JsonSyntaxException {

      Message parsedMessage = GSON.getGsonBuilder().fromJson(jsonMessage, Message.class);
      if(parsedMessage == null)
         throw new JsonSyntaxException("Empty message");

      return parsedMessage;
   }

   private void startPinging(){
      Runnable pinger = () ->
      {
         while(connected){
            Message messageToSend = new Message(MessageType.PING);
            sendMessage(messageToSend);
            try {
               Thread.sleep(ConfigParameters.CLIENT_TIMEOUT);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      };
      new Thread(pinger).start();
   }

   /**
    * closes this client's socket
    */
   public void closeSocket() {
      try {
         this.connected = false;
         clientSocket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // ------------------------------------------- GETTERS AND SETTERS --------------------------------------------------

   public boolean isConnected() {
      return connected;
   }

   public boolean isLogged() {
      return logged;
   }

   public void setLogged(boolean logged) {
      this.logged = logged;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setConnected(boolean connected) {
      this.connected = connected;
   }
}