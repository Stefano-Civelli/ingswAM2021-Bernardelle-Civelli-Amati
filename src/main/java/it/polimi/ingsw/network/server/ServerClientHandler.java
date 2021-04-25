package it.polimi.ingsw.network.server;

import com.google.gson.*;
import it.polimi.ingsw.network.action.Action;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerClientHandler implements Runnable {
   private final Socket clientSocket;
   private final Server server;
   private Scanner in;
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
         in = new Scanner(clientSocket.getInputStream());
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

         while (true) {
           String messageString = in.nextLine();
           messageString = messageString.replaceAll("\n", " ");
           Message message = messageParser(messageString);
           messageReceived(message);
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
            Message answerMessage = server.getTurnManager().handleAction(action);
            confirmationMessage(answerMessage);
            break;

            default: return;


      }
   }

   private void confirmationMessage(Message answerMessage){
      //ci metto come nome l'username
      //switch sul tipo
//      ERROR, -> mando solo a chi fa errore
//      NEXT_STATE; -> mando a tutti


      sendMessage(answerMessage);
   }




   protected void sendMessage(Message message) {

         String jsonMessage = gsonBuilder.toJson(message);
         out.println(jsonMessage);

   }


   private Message messageParser(String jsonMessage) throws JsonSyntaxException {
      return gsonBuilder.fromJson(jsonMessage, Message.class);
   }


   protected synchronized void closeConnection(String message) {
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

