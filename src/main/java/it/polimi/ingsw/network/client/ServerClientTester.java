package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ServerClientHandler;
import it.polimi.ingsw.utility.ConfigParameters;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ServerClientTester {

  private String username = null;
  private PrintWriter out;
  private Scanner in;

  public static void main(String[] args) {
    ServerClientTester client = new ServerClientTester();

    int port;
    String ip;

    if (ConfigParameters.TESTING) {
      ip = "localhost";
      port = 7659;
      System.out.println("DEBUG server: ip -> localhost, port -> 7659");
      client.username = "gino";
    }
    else return;

    Socket server = new Socket();
    try {
      server = new Socket(ip, port);
      client.startPinging();


      try {
         client.out = new PrintWriter(server.getOutputStream());
         client.in = new Scanner(server.getInputStream());


        while (true) {
          Scanner sys = new Scanner(System.in);
          client.sendToServer(sys.nextLine());
          String msg = sys.nextLine();
          System.out.println(msg);
        }
      } catch(IOException e){
        //notifyServerLost();
        try {
          server.close();
        } catch (IOException ex) {}
      }


    } catch (IOException e) {
      System.out.println("An error has been encountered while opening connection");
    }
  }

  private void startPinging() {
    Timer pingTimer = new Timer();

    pingTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        sendToServer("{'username': 'gino', 'messageType': 'PING', 'payload': '' }");
      }
    }, 1000, ConfigParameters.CLIENT_TIMEOUT * 1000);
  }

  private void sendToServer(String sendThis){
    out.println(sendThis);
    out.flush();
  }
}


