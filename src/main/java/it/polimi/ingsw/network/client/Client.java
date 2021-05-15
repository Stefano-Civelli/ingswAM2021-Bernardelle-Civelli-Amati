package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;
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
  private MessageHandler serverConnector;
  private SimpleGameState simpleGameState;
  private LinkedHashMap<String, SimplePlayerState> simplePlayerStateMap;
  private ClientTurnManager  turnManager;



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

    //devo fargli scegliere se giocare in locale o network

    if (cli) {
      Client client = new Client();
      Cli view = new Cli(client, new CliDrawer(client.simpleGameState, client.simplePlayerStateMap));
      client.setView(view);
      client.turnManager = new ClientTurnManager(client, view);
      view.setClientTurnManager(client.turnManager);
      view.displaySetup();
      client.connectToServer(); //TODO questa posso farla eseguire al thread. poi tolgo l'altro thread che non serve più
      //TODO chiamo la waitforinput che ha il whiletrue
    }
    //else gui
  }

  public Client() {
    this.simpleGameState = new SimpleGameState();
    this.simplePlayerStateMap = new LinkedHashMap<>();
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
        if(username.equals(turnManager.getCurrentPlayer()))
          turnManager.currentPhasePrint();
        else
          view.displayPlayerTurn(msg.getUsername());
        break;
      case NEXT_TURN_STATE:
        System.out.println(msg.getPayload());
        handleTurnState(msg.getPayload());
        //view.displayEndTurn();
        break;
      case LEADERCARD_SETUP:
        SimplePlayerState playerState = new SimplePlayerState();
        //System.out.println(msg.getPayload());
        this.simplePlayerStateMap.put(msg.getUsername(), playerState);
        playerState.setupLeaderCard(msg.getPayload());
        view.displayRecievedLeadercards();
        break;
      case DECK_SETUP:
        simpleGameState.constructDeck(msg.getPayload());
        break;
      case MARKET_SETUP:
        simpleGameState.constructMarket(msg.getPayload());
        //view.displayMarket();
        break;
      case MARKET_UPDATED:
        simpleGameState.updateMarket(msg.getPayload());
       //view.displayMarket();
        break;
      case DEVELOP_CARD_DECK_UPDATED:
        simpleGameState.updateDeck(msg.getPayload());
        break;
      case WAREHOUSE_UPDATE:
        getSimplePlayerState(msg.getUsername()).warehouseUpdate(msg.getPayload());
        break;
      case ACTIVATED_LEADERCARD_UPDATE:
        getSimplePlayerState(msg.getUsername()).activatedLeaderUpdate(msg.getPayload());
        break;
      case TRACK_UPDATED:
        getSimplePlayerState(msg.getUsername()).trackUpdate(msg.getPayload());
        break;
      case VATICAN_REPORT:
        getSimplePlayerState(msg.getUsername()).vaticanReportUpdate(msg.getPayload());
        break;
      case CHEST_UPDATE:
        break;
      case CARD_SLOT_UPDATE:
        break;

    }
  }

  private void handleGameStarted(Message message) {
    ArrayList<String> players = GSON.getGsonBuilder().fromJson(message.getPayload(), ArrayList.class);
    SimplePlayerState currentPlayerState = getSimplePlayerState();

    this.simplePlayerStateMap = new LinkedHashMap<>();

    for(String s : players) {
      if (s.equals(this.username))
        this.simplePlayerStateMap.put(s, currentPlayerState);
      else
        this.simplePlayerStateMap.put(s, new SimplePlayerState()); //the array is ordered to give the right amount of resouces to each player
    }

    turnManager.setCurrentPlayer((String) this.simplePlayerStateMap.keySet().toArray()[0]);
  }

  private void handleTurnState(String payload) {
    TurnManager.TurnState newState = GSON.getGsonBuilder().fromJson(payload, TurnManager.TurnState.class);
    turnManager.newPhase(newState.getPhase());
    if(!username.equals(newState.getPlayer()) && !newState.getPlayer().equals(turnManager.getCurrentPlayer())) {
      view.displayPlayerTurn(newState.getPlayer());
      turnManager.newCurrentPlayer(newState.getPlayer());
    }
    else if(username.equals(newState.getPlayer())) {
      view.displayYourTurn(username);
      turnManager.newCurrentPlayer(newState.getPlayer());
      turnManager.currentPhasePrint();
    }
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


  public void sendMessage(Message msg) { //deve cambiare nome perchè va usata anche per il locale
    if(msg.getMessageType() != MessageType.LOGIN)
      msg.setUsername(this.username);
    serverConnector.sendToServer(msg);
  }

  public void close() {
    System.out.println("You will be disconnected... Bye shdroonzo");
    serverConnector.stop();
    System.exit(0);
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


  //TODO migliorarla
  public int getPlayerTurnPosition(){
    int i = 1;
    for(Map.Entry<String, SimplePlayerState> entry : simplePlayerStateMap.entrySet()){
      if(entry.getKey().equals(username))
        return i;
      i++;
    }
    return -1;
  }
}
