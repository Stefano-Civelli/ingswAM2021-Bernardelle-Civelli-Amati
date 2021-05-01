package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ServerClientHandler;
import it.polimi.ingsw.utility.ConfigParameters;

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

  private PrintWriter out;
  private BufferedReader in;
  Socket server;

  public static void main(String[] args) {
    ServerClientTester client = new ServerClientTester();

    int port;
    String ip;

    ip = "localhost";
    port = 7659;
    System.out.println("DEBUG server: ip -> localhost, port -> 7659");

    client.server = new Socket();
    try {
      client.server = new Socket(ip, port);
      System.out.println("connection established, you can start sending scemocoglione...");
      client.startPinging();


      try {
         client.out = new PrintWriter(client.server.getOutputStream());
         client.in = new BufferedReader(new InputStreamReader(client.server.getInputStream()));
         client.startSocketReader();

        while (true) {
          Scanner sys = new Scanner(System.in);
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


}


