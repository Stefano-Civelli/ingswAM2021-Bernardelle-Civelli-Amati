package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.BuyDevelopCardAction;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ServerClientTester {

  public PrintWriter out;
  public BufferedReader in;
  Socket server;

  public static void main(String[] args) {
    ServerClientTester client = new ServerClientTester();

    int port;
    String ip;

    ip = "localhost";
    port = 6754;
    System.out.println("DEBUG server: ip -> localhost, port -> 7659");

    client.server = new Socket();
    try {
      client.server = new Socket(ip, port);
      System.out.println("connection established, you can start sending scemocoglione...");
      client.startPinging();


      try {
         client.out = new PrintWriter(client.server.getOutputStream());
         client.in = new BufferedReader(new InputStreamReader(client.server.getInputStream()));
         Scanner sys = new Scanner(System.in);
         client.startSocketReader();
         //client.createActionMessage();
         client.setup(sys);

        while (true) {
          client.sendToServer(sys.nextLine());
        }

      } catch(IOException e){
        try {
          client.server.close();
        } catch (IOException ex) { ex.printStackTrace();}
      }


    } catch (IOException e) {
      System.out.println("An error has been encountered while opening connection");
    }
  }

  private void setup(Scanner sys) {
    System.out.println("AutoLogin? [y/n]");
    boolean yes = sys.nextLine().equals("y");
    if(!yes)
      return;
    System.out.println("insert username");
    String username = sys.nextLine();
    System.out.println("are you the first player? [y/n]");
    boolean first = sys.nextLine().equals("y");

    out.println("{ \"username\": " + username + ", \"messageType\": \"LOGIN\", \"payload\": null }");
    out.flush();
    if(first){
      out.println("{ \"username\": "  + username + ", \"messageType\": \"NUMBER_OF_PLAYERS\", \"payload\": \"2\" }");
      out.flush();
    }


  }

  private void startPinging() {
    Timer pingTimer = new Timer();

    pingTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        sendToServer("{'username': null, 'messageType': 'PING', 'payload': null }");
      }
    }, 1000, ConfigParameters.CLIENT_TIMEOUT);
  }

  private void sendToServer(String sendThis){
    out.println(sendThis);
    out.flush();
  }


  private void startSocketReader(){
    Runnable socketReader = () ->
    {
      while(true){
        String msg = null;
        try {
          msg = in.readLine();
          if(msg == null){
            System.out.println("disconnected from the server");
            server.close();
            break;
          }
        } catch (IOException | NoSuchElementException e) {
          try {
            server.close();
          } catch (IOException ex) { ex.printStackTrace();}
          e.printStackTrace();
        }
        System.out.println(msg);
      }
    };
    new Thread(socketReader).start();
  }



  public String createActionMessage(){
    Action action = new BuyDevelopCardAction(2,0,1);
    // problema, devo settare il type in qualche modo, magari serve togliere \" ( posso farlo già lato client)
    //String actionString = GSON.getGsonBuilder().toJson(action);
    Message message = new Message("pippo", MessageType.ACTION, action);

    String jsonMessage = GSON.getGsonBuilder().toJson(message);
    jsonMessage = jsonMessage.replaceAll("\n", " ");
    jsonMessage = jsonMessage.replaceAll("\\\\\"", "'"); // first 4 backslashes are to escape \"
    System.out.println(jsonMessage);
    return jsonMessage;
  }
}