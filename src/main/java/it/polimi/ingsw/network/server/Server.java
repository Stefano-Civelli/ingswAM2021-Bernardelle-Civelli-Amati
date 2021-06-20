package it.polimi.ingsw.network.server;

import it.polimi.ingsw.utility.ConfigParameters;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * This class accepts connection to a client and assign the client handling to the ServerClientHandler class via Thread.
 * Server class also contains reference to all the clientHandlers in order to manage at an high level of abstraction
 * the message sending process and the client disconnections.
 */
public class Server {

   public static final int MIN_PORT_NUMBER = 1024;
   public static final int MAX_PORT_NUMBER = 65535;

   private Map<String, Match> matchMap = new HashMap<>();

   public synchronized Match createNewMatch(String matchId){
      if(matchIdPresent(matchId))
         return null;
      Match match = new Match(matchId, this);
      matchMap.put(matchId, match);
      return match;
   }

   public synchronized Match assignToMatch(String matchId, ServerClientHandler serverClientHandler){
      Match match = matchMap.get(matchId);
      if(match.getPlayersNumber() == 0)
         return null;
      return match;
   }

   public synchronized Boolean matchIdPresent(String matchId){
      if(this.matchMap.entrySet().stream().map(x -> x.getKey()).filter(x -> x.equals(matchId)).count() > 0)
         return true;
      return false;
   }

   public synchronized void deleteMatch(String matchName){
      this.matchMap.remove(matchName);
   }

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

}
