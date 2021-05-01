package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerConnector {
  private static final Gson gsonBuilder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
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

      client.displayLogin();

      while (true) {
        Message msg = messageParserFromJson(in.nextLine());
        client.handleMessage(msg);
      }
    } catch(IOException | NoSuchElementException e){
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
    message = gsonBuilder.fromJson(in, Message.class);
    return message;
  }

  public void stop(){
    try {
      server.close();
    } catch (IOException e) {
    }
  }

  public void sendToServer(String msg) {
    msg = msg.replaceAll("\n", " "); //devo inviare senza a capo
    out.println(msg);
    out.flush();
  }
}
