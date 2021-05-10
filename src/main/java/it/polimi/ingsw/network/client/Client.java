package it.polimi.ingsw.network.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliDrawer;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client {
  public static final int MIN_PORT = Server.MIN_PORT_NUMBER;
  public static final int MAX_PORT = Server.MAX_PORT_NUMBER;


  private Socket server;
  private ViewInterface view;
  private String username = null;
  private String serverIP;
  private int serverPort;
  private ServerConnector serverConnector;
  private SimpleGameState simpleGameState;
  private Map<String, SimplePlayerState> simplePlayerStateMap;

  private Timer pingTimer = null;


  public static void main(String[] args) {
    boolean cli = true;

    if (args.length > 0)
      switch (args[0]){
        case "cli":
          cli = true;
          break;
        case "gui":
          break;
      }

    if (cli) {
      Client client = new Client();
      Cli view = new Cli(client, new CliDrawer(client.simpleGameState, client.simplePlayerStateMap));
      client.setView(view);
      view.displaySetup();
      client.connectToServer(); //TODO questa posso farla eseguire al thread. poi tolgo l'altro thread che non serve più
      //TODO chiamo la waitforinput che ha il whiletrue
    }
    //else gui
  }

  public Client() {
    this.simpleGameState = new SimpleGameState();
    this.simplePlayerStateMap = new HashMap<>();
  }

  public void setView(ViewInterface view) {
    this.view = view;
  }

  public void setServerIP(String ip) { serverIP = ip; }

  public void setServerPort(int port) { serverPort = port; }

  private String getServerIP() { return serverIP; }

  private int getServerPort() { return serverPort; }

  /**
   * Instantiates a connection with the server
   */
  public void connectToServer() {
    Socket server = new Socket();
    try {
      server = new Socket(getServerIP(), getServerPort());
      serverConnector = new ServerConnector(server, this);
      startPinging();
      serverConnector.handleServerConnection();
    } catch (IOException e) {
      view.displaySetupFailure();
    }
  }

  public void displayLogin(){
    view.displayLogin();
  }

  public void handleMessage(Message msg){

    switch (msg.getMessageType()){
      case PING:
        break;
      case SERVER_DOWN:
        view.displayServerDown();
        break;
      case ERROR:
        handleError(ErrorType.fromValue(msg.getPayload()));
        break;
      case LOGIN_SUCCESSFUL:
        view.displayLoginSuccessful();
        break;
      case NUMBER_OF_PLAYERS:
        view.displayPlayersNumberChoice();
        break;
      case WAIT_FOR_LOBBY_CREATION:
        view.displayWaiting();
        break;
      case LOBBY_CREATED:
        view.displayLobbyCreated();
        break;
      case RECONNECTED:
        view.displayReconnection();
        break;
      case OTHER_USER_JOINED:
        view.displayOtherUserJoined(msg);
        break;
      case YOU_JOINED:
        view.displayYouJoined();
        view.displayOtherUserJoined(msg);
        break;
      case GAME_STARTED:
        handleGameStarted(msg);
        view.displayGameStarted();
        //TODO gestire le risorse in base alla posizione del player nell'array
        break;
      case LEADERCARD_SETUP:
        //le setto nel SimpleModel
        view.displayRecievedLeadercards();
      case DECK_SETUP:
        simpleGameState.constructDeck(msg.getPayload());
        break;
      case MARKET_SETUP:
        simpleGameState.constructMarket(msg.getPayload());
        view.displayMarket();
        break;
      case MARKET_UPDATED:
        simpleGameState.updateMarket(msg.getPayload());
        view.displayMarket();
        break;
      case DEVELOP_CARD_DECK_UPDATED:
        simpleGameState.updateDeck(msg.getPayload());
        break;
      case WAREHOUSE_UPDATE:
        break;
      case ACTIVATED_LEADERCARD_UPDATE:
        break;
      case TRACK_UPDATED:
        break;
      case VATICAN_REPORT:
        break;

    }
  }

  private void handleGameStarted(Message message) {
    ArrayList<String> players = GSON.getGsonBuilder().fromJson(message.getPayload(), ArrayList.class);
    for(String s : players)
      this.simplePlayerStateMap.put(s, new SimplePlayerState()); //the array is ordered to give the right amount of resouces to each player
  }


  private void handleError(ErrorType errorType) {
    switch (errorType){
      case GAME_ALREADY_STARTED:
        view.displayGameAlreadyStarted();
        close();
        break;
      case INVALID_LOGIN_USERNAME:
        view.displayFailedLogin();
        view.displayLogin();
        break;
    }

  }

  private void startPinging() {
    pingTimer = new Timer();

    pingTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        sendToServer(new Message(MessageType.PING));
      }
    }, 1000, ConfigParameters.CLIENT_TIMEOUT);
  }


  public void sendToServer(Message msg) {
    if(msg.getMessageType() != MessageType.LOGIN)
      msg.setUsername(this.username);

    //message.setUsername(this.username); non abbiamo lo user in ogni messaggio, dovremmo?
    String message = parserToJson(msg);
    JsonObject jsonObject = (JsonObject) JsonParser.parseString(message);
    if(jsonObject.getAsJsonObject().get("messageType").getAsString().equals(MessageType.ACTION.name()))
      message = message.replaceAll("\\\\\"", "");
    serverConnector.sendToServer(message);
  }

  public void close() {
    System.out.println("You will be disconnected... Bye shdroonzo");
    serverConnector.stop();
    System.exit(0);
  }

  private String parserToJson(Message msg){
    return GSON.getGsonBuilder().toJson(msg);
  }

  public String getUsername() { return this.username; }

  public void setUsername(String username) { this.username = username; }

  public SimplePlayerState getSimplePlayerState() {
    return this.simplePlayerStateMap.get(username);
  }

  public SimplePlayerState getSimplePlayerState(String username) {
    return this.simplePlayerStateMap.get(username);
  }

  public List<String> usernameList(){
    return new ArrayList<>(this.simplePlayerStateMap.keySet());
  }
}
