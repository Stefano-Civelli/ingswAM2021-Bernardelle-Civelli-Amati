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


public class ServerClientHandler implements Runnable {

   private final Socket clientSocket;
   private final Server server;
   private BufferedReader in;
   private PrintWriter out;

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
         handleClientConnection(); // sta qua dentro finchè la connessione è aperta
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void handleClientConnection() throws IOException {

      System.out.println("Connected to " + clientSocket.getInetAddress());
      server.addClient(this); // probabilmente serve aggiungerlo ora perchè così so che non posso far connettere + player di quanti sono rischiesti

      try {

         while (true) {
            try {
               String receivedString = in.readLine();
               Message message = messageParser(receivedString);
               if(ConfigParameters.TESTING)
                  System.out.println(receivedString);
               messageReceived(message);
            }catch(JsonSyntaxException e){
               e.printStackTrace();
               sendMessage(new Message(MessageType.ERROR, ErrorType.MALFORMED_MESSAGE));
            }
         }//while

      }catch(IOException e){ //client crashes or timeout runs out
         if(e instanceof SocketTimeoutException)
            System.out.print("TIMEOUT, ");
         System.out.println("connection lost on client: " + clientSocket.getInetAddress() + "  --username: " + username);
         server.handleClientDisconnection(this);
      }
   }

   /**
    * Called when the ClientHandler receive a message from a client
    */
   private void messageReceived(Message message) {
      if((username != null && !username.equals(message.getUsername())) || message.getMessageType() == null)
         return;

      switch (message.getMessageType()) {

         case LOGIN:
            if(logged)
               return;
            server.handleLogin(message, this);
            break;

         case NUMBER_OF_PLAYERS:
            if(!logged)
               return;
            server.lobbySetup(message);
            break;

         case PING:
            break;

         case QUIT:
            server.handleClientDisconnection(this);
            break;

         case ACTION:
            if(!server.isGameRunning())
               return;
            Action action = message.getAction();
            try {
               Message errorOrEndTurn = server.getTurnManager().handleAction(action);
               actionAnswereMessage(errorOrEndTurn);
            } catch (NoConnectedPlayerException e) {
               //This code should never be executed
               e.printStackTrace();
            }
            break;
         case CHEAT:
            if(!ConfigParameters.TESTING)
               return;
            try {
               Chest tempChest = server.getTurnManager().getGame().getPlayerBoard(server.getTurnManager().getCurrentPlayer()).getChest();
               tempChest.addResources(ResourceType.SHIELD, 50);
               tempChest.addResources(ResourceType.STONE, 50);
               tempChest.addResources(ResourceType.SERVANT, 50);
               tempChest.addResources(ResourceType.GOLD, 50);
               tempChest.endOfTurnMapsMerge();
               server.getTurnManager().getGame().getPlayerBoard(server.getTurnManager().getCurrentPlayer()).getTrack().moveForward(19);
            } catch (InvalidUsernameException | NegativeQuantityException | AbuseOfFaithException e) {e.printStackTrace();}
            break;
         default:

      }//switch
   }

   public void actionAnswereMessage(Message answerMessage){
      MessageType messageType = answerMessage.getMessageType();
      answerMessage.setUsername(this.username);

      switch (messageType){
         case ERROR:
            sendMessage(answerMessage);
            break;
         case NEXT_TURN_STATE: //questa cosa non serve dirla al client, basta dirgli tipo turno finito a tutti e basta, poi loro con la logica capiscono cosa fare
            server.sendBroadcast(answerMessage);
            break;
      }
   }

   /**
    * Sends the message trough the socket
    *
    * @param message message to be sent
    */
   protected synchronized void sendMessage(Message message) {
      if(!connected)
         return;
      String jsonMessage = GSON.getGsonBuilder().toJson(message);
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
//            String jsonMessage = GSON.getGsonBuilder().toJson(messageToSend);
//            jsonMessage = jsonMessage.replaceAll("\n", " "); //remove all newlines before sending the message
            sendMessage(messageToSend);
//            out.println(jsonMessage);
//            out.flush();
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
   public void closeSocket(){
      try {
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