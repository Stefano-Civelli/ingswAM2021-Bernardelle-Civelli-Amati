package it.polimi.ingsw.network.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.util.Timer;
import java.util.TimerTask;

public class ServerConnector implements MessageHandler{
  private Client client;
  private Socket server;
  private PrintWriter out;
  private BufferedReader in;
  private Timer pingTimer = null;

  public ServerConnector(Socket server, Client client) {
    this.server = server;
    this.client = client;
  }

  public void handleServerConnection() {
      try {
        out = new PrintWriter(server.getOutputStream());
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        startPinging();
        client.displayLogin();

        while (true) {
          Message msg = messageParserFromJson(in.readLine());
          client.handleMessage(msg);
        }
      } catch (IOException | NoSuchElementException e) {
        notifyServerLost();
        stop();
      }

  }

  public void stop(){
    sendToServer(new Message(client.getUsername(), MessageType.QUIT));
    try {
      server.close();
      System.exit(0);
    } catch (IOException e) {
    }
  }

  public void sendToServer(Message msg) {
    //message.setUsername(this.username); non abbiamo lo user in ogni messaggio, dovremmo?
    String message = parserToJson(msg);

    out.println(message);
    out.flush();
  }

  private void startPinging() {
    pingTimer = new Timer();

    pingTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        sendToServer(new Message(client.getUsername(), MessageType.PING));
      }
    }, 1000, ConfigParameters.CLIENT_TIMEOUT);
  }

  private String parserToJson(Message msg){
    String message = GSON.getGsonBuilder().toJson(msg);
    JsonObject jsonObject = (JsonObject) JsonParser.parseString(message);
    if(jsonObject.getAsJsonObject().get("messageType").getAsString().equals(MessageType.ACTION.name()))
      message = message.replaceAll("\\\\\"", "");

    message = message.replaceAll("\n", " "); //devo inviare senza a capo
    return message;
  }

  private void notifyServerLost() {
    client.handleMessage(new Message(MessageType.SERVER_DOWN));
  }

  private Message messageParserFromJson(String in){
    Message message = null;
    JsonObject jsonObject = (JsonObject) JsonParser.parseString(in);
    if(jsonObject.getAsJsonObject().get("messageType").getAsString().equals(MessageType.ERROR.name()))
      in = in.replaceAll("\\\\\"", "");

    message = GSON.getGsonBuilder().fromJson(in, Message.class);
    return message;
  }
}
