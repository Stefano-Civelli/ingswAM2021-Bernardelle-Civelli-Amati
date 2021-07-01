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

/**
 * Class that manage the connection with the server.
 * This class receives and send Messages
 */
public class ServerConnector{
  private final Client client;
  private final Socket server;
  private PrintWriter out;
  private BufferedReader in;
  private Timer pingTimer = null;

  /**
   * Constructor for CliDrawer class
   * @param server, socket that connects this class to server
   * @param client, client's instance
   */
  public ServerConnector(Socket server, Client client) {
    this.server = server;
    this.client = client;
  }

  /**
   * Receives messages from server formatted in Json, de-parses them into Message class and sends them to client
   */
  public void handleServerConnection() {
      try {
        out = new PrintWriter(server.getOutputStream());
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        startPinging();
        while (true) {
          Message msg = messageParserFromJson(in.readLine());
          client.handleMessage(msg);
        }
      } catch (IOException | NoSuchElementException e) {
        notifyServerLost();
      }
  }

  /**
   * sends a quit message to server and closes client socket
   */
  public void stop(){
    sendToServer(new Message(client.getUsername(), MessageType.QUIT));
    try {
      server.close();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends the message passed as parameter to server
   * @param msg, message to send
   */
  public void sendToServer(Message msg) {
    if(msg.getMessageType() != MessageType.CREATE_MATCH && msg.getMessageType() != MessageType.JOIN_MATCH)
      msg.setUsername(client.getUsername());

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

  private Message messageParserFromJson(String in) {
    if(in == null) {
      this.client.getView().displayServerDown();
    }
    Message message;
    JsonObject jsonObject = (JsonObject) JsonParser.parseString(in);
    if(jsonObject.getAsJsonObject().get("messageType").getAsString().equals(MessageType.ERROR.name()))
      in = in.replaceAll("\\\\\"", "");

    message = GSON.getGsonBuilder().fromJson(in, Message.class);
    return message;
  }
}
