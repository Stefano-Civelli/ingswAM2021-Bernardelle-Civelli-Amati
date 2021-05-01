package it.polimi.ingsw.network.server;

import com.google.gson.*;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;

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
   private static final Gson gsonBuilder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();


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
               Message message = messageParser(in.readLine());
               messageReceived(message);
            }catch(JsonSyntaxException e){
               sendMessage(new Message(MessageType.ERROR, ErrorType.MALFORMED_MESSAGE));
            }

         }

      }catch(IOException e){
         if(e instanceof SocketTimeoutException)
            System.out.print("TIMEOUT, ");

         System.out.println("connection lost on client " + clientSocket.getInetAddress());
         //server.notifyClientDisconnection(this);
         handleClientDisconnection(); //TODO -----------------------------------------------------
      }
   }

   /**
    * Called when the ClientHandler receive a message from a client
    */
   private void messageReceived(Message message) {
      if((username != null && !username.equals(message.getUsername())) || message.getMessageType() == null)
         return;


      switch (message.getMessageType()) {
         //potrei fare istanceOf

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
            handleClientDisconnection();
            break;

         case ACTION: //is an action
            if(!logged)
               return; //does nothing if the sender is not who he claims to be

            //prima di darlo al turn manager trasformo in azione
            Action action = message.getAction();
            action.setUsername(username);
            Message answerMessage = server.getTurnManager().handleAction(action);
            //turn manager sends back an answere message to be forwarded to the client
            confirmationMessage(answerMessage);
            break;

            default: return;


      }
   }

   private void confirmationMessage(Message answerMessage){
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




   protected void sendMessage(Message message) {
      if(!connected)
         return;
      String jsonMessage = gsonBuilder.toJson(message);
      jsonMessage = jsonMessage.replaceAll("\n", " "); //remove all newlines before sending the message
      out.println(jsonMessage);
      out.flush();

   }


   private Message messageParser(String jsonMessage) throws JsonSyntaxException {
      Message parsedMessage = gsonBuilder.fromJson(jsonMessage, Message.class);
      if(parsedMessage == null)
         throw new JsonSyntaxException("Empty message");
      return parsedMessage;
   }


   protected void handleClientDisconnection() {
   if(!server.isGameStarted()) {
      sendMessage(new Message(MessageType.DISCONNECTED_SERVER_SIDE)); // happens when there is no space in the game
      server.notifyClientDisconnection(this); //forget about this client
   }
   connected = false;
   //TODO devo dire al controller che ol player si è disconnesso
   //close the socket
      try {
         clientSocket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

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

   private void startPinging(){
      Runnable pinger = () ->
      {
         while(connected){
            Message messageToSend = new Message(MessageType.PING);
            String jsonMessage = gsonBuilder.toJson(messageToSend);
            jsonMessage = jsonMessage.replaceAll("\n", " "); //remove all newlines before sending the message
            out.println(jsonMessage);
            out.flush();
            try {
               Thread.sleep(ConfigParameters.CLIENT_TIMEOUT);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      };
      new Thread(pinger).start();
   }



}

