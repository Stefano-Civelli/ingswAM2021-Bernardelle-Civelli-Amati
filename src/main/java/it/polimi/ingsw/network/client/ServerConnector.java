package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnector {
  private Client client;
  private Socket server;
  private PrintWriter out;
  private Scanner in;

  public ServerConnector(Socket server, Client client) {
    this.server = server;
    this.client = client;
  }

  public void handleServerConnection() {
    try {
      out = new PrintWriter(server.getOutputStream());
      in = new Scanner(server.getInputStream());

      while (true) {
        Message msg = messageParserFromJson(in.nextLine());
        client.handleMessage(msg);
      }
    } catch(IOException e){
      notifyServerLost();
      try {
        server.close();
      } catch (IOException ex) {}
    }
  }

  private void notifyServerLost() {
    client.handleMessage(new Message(MessageType.SERVER_DOWN));
  }

  //TODO
  private Message messageParserFromJson(String in){
    Message message = null;
    return message;
  }

  public void stop(){
    try {
      server.close();
    } catch (IOException e) {
    }
  }

  public void sendToServer(String msg) {
    out.println(msg);
    out.flush();
  }
}
