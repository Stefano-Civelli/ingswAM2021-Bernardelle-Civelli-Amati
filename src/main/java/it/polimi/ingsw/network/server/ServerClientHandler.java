package it.polimi.ingsw.network.server;

import com.google.gson.*;
import it.polimi.ingsw.network.action.Action;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

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
   private boolean forcedToDisconnect; //true only if the player disconnection is caused by the server
   private boolean Logged; // set to true when the players logs in
   private static final Gson gsonBuilder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();


   ServerClientHandler(Socket client, Server server) {
      this.clientSocket = client;
      this.server = server;
      this.connected = true;
      this.Logged = false;
      this.forcedToDisconnect = false;
      this.username = null;
   }


   @Override
   public void run() {
      try {
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         out = new PrintWriter(clientSocket.getOutputStream());
         handleClientConnection(); // sta qua dentro finchè la connessione è aperta
      } catch (IOException e) {
         //TODO gestire disconnessione per scadenza timeout del socket
         e.printStackTrace();
      }
   }

   private void handleClientConnection() throws IOException {

      System.out.println("Connected to " + clientSocket.getInetAddress());
      server.addClient(this); // probabilmente serve aggiungerlo ora perchè così so che non posso far connettere + player di quanti sono rischiesti

      try {
         while (true) {
            Message message = messageParser(in.readLine());
            messageReceived(message);
         }
      }catch(IOException e){
         if(e instanceof SocketTimeoutException)
            System.out.print("TIMEOUT, ");

         System.out.println("connection lost on client " + clientSocket.getInetAddress());
         connected = false;
         server.notifyClientDisconnection(this);
         //handleClientDisconnection(); //TODO -----------------------------------------------------
         clientSocket.close();
      }
   }

   /**
    * Called when the ClientHandler receive a message from a client
    */
   private void messageReceived(Message message) {
//      if (message.getMessageType() == null)
//         return;

      switch (message.getMessageType()) {
         //potrei fare istanceOf
         case LOGIN:
            server.handleLogin(message, this);
            break;

         case NUMBER_OF_PLAYERS:
            server.lobbySetup(message);
            break;

         case PING:
            break;

         case ACTION: //is an action
            if(!message.getUsername().equals(username))
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
         case NEXT_TURN_STATE:
            server.sendBroadcast(answerMessage);
            break;
      }
   }




   protected void sendMessage(Message message) {
      String jsonMessage = gsonBuilder.toJson(message);
      jsonMessage = jsonMessage.replaceAll("\n", " "); //remove all newlines before sending the message
      out.println(jsonMessage);
      out.flush();

   }


   private Message messageParser(String jsonMessage) throws JsonSyntaxException {
      return gsonBuilder.fromJson(jsonMessage, Message.class);
   }


   protected synchronized void handleClientDisconnection(String message) {
      sendMessage(new Message(MessageType.DISCONNECTED_SERVER_SIDE, message)); // notify user about the server-side disconnection
      try {
         clientSocket.close();
      } catch (IOException e) {
         System.err.println(e.getMessage());
      }
      forcedToDisconnect = true;
      connected = false;
   }

   public boolean connected() {
      return connected;
   }

   public boolean isLogged() {
      return Logged;
   }

   public void setLogged(boolean logged) {
      Logged = logged;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }


}

