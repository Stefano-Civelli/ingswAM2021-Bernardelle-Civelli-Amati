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
import it.polimi.ingsw.view.gui.GUIStarter;
import it.polimi.ingsw.view.gui.ViewObserver;
import javafx.application.Application;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Client implements ViewObserver {
  public static final int MIN_PORT = Server.MIN_PORT_NUMBER;
  public static final int MAX_PORT = Server.MAX_PORT_NUMBER;


  private Socket server;
  private ViewInterface view;
  private String username = null;
  private String serverIP;
  private int serverPort;
  private MessageHandler messageConnector;
  //private ModelObserver updater;
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
          cli = false;
          break;
      }

    //devo fargli scegliere se giocare in locale o network
    if (cli) {
      Client client = new Client();
      client.simpleGameState = new SimpleGameState();
      client.simplePlayerStateMap = new LinkedHashMap<>();
      Cli view = new Cli(client, new CliDrawer(client));
      client.setView(view);
      //client.setUpdater(new ClientVirtualView(client));
      client.turnManager = new ClientTurnManager(client, view);
      view.setClientTurnManager(client.turnManager);
      view.displaySetup();
      client.connectToServer();
    }
    else {
      Application.launch(GUIStarter.class);
    }
  }

  public void setView(ViewInterface view) {
    this.view = view;
  }

  //public void setUpdater(ModelObserver updater) { this.updater = updater;}

  public void setTurnManager(ClientTurnManager turnManager) {
    this.turnManager = turnManager;
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
      messageConnector = new ServerConnector(server, this);
      messageConnector.handleServerConnection();
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
        //quando ricevo un errore la cosa migliore è passarlo all'handler che poi lo printa facendo uno switch che printa diverso per ogni tipo di errore
        //System.out.println("ERROR: " + msg.getPayload());
        handleError(ErrorType.fromValue(msg.getPayload()));
        break;
      case LOGIN_SUCCESSFUL:
        this.username = msg.getUsername();
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
        handleTurnState(msg.getPayload());
        break;
      case LEADERCARD_SETUP: //received only by the interested player
        SimplePlayerState playerState = new SimplePlayerState();
        this.simplePlayerStateMap.put(msg.getUsername(), playerState);
        playerState.setupLeaderCard(msg.getPayload());
        view.displayRecievedLeadercards();
        break;
      case DECK_SETUP:
        simpleGameState.constructDeck(msg.getPayload());
        break;
      case MARKET_SETUP:
        simpleGameState.constructMarket(msg.getPayload());
        break;
      case MARKET_UPDATED:
        simpleGameState.updateMarket(msg.getPayload());
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
        //updater.chestUpdate(Message msg);
        getSimplePlayerState(msg.getUsername()).chestUpdate(msg.getPayload());
        break;
      case TEMP_CHEST_UPDATE:
        getSimplePlayerState(msg.getUsername()).tempChestUpdate(msg.getPayload());
        break;
      case CARD_SLOT_UPDATE:
        getSimplePlayerState(msg.getUsername()).cardSlotUpdate(msg.getPayload());
        break;
      case DISCARDED_LEADERCARD:
        getSimplePlayerState(msg.getUsername()).discardLeader(Integer.parseInt(msg.getPayload()));
        //TODO controllare se ha senso
        break;
      case GAME_ENDED:
        view.displayGameEnded(msg.getPayload());
        messageConnector.stop();
      case LORENZO_TRACK_UPDATE:
        break; //TODO
      case LORENZO_DECK_UPDATE:
        break; //TODO
      case LORENZO_SHUFFLE_UPDATE:
        break; //TODO
      default:
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
    //TODO probabilmente non serve più il fatto che setState ritorna un booleano
    if(turnManager.setStateIsPlayerChanged(newState)){
      if (username.equals(turnManager.getCurrentPlayer())) {
        view.displayYourTurn(turnManager.getCurrentPlayer());
        //view.displayDefaultCanvas(turnManager.getCurrentPlayer());
      }
      else
        view.displayPlayerTurn(turnManager.getCurrentPlayer());
    }

    if(username.equals(turnManager.getCurrentPlayer())){
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
      case NOT_BUYABLE:
        System.out.println("sorry mate, sei troppo povero per comprarla. Riprova quando avrai comprato azioni Tesla");
        turnManager.currentPhasePrint();
        break;
      case INVALID_LEADERCARD:
        turnManager.currentPhasePrint();
        break;
      case INVALID_DEVELOP_CARD:
        break;
      case CANNOT_DISCARD_ACTIVE_LEADER:
        System.out.println("sorry mate, you can't discard an active leader card ");
        turnManager.currentPhasePrint();
        break;
      case NOT_ACTIVATABLE_PRODUCTION:
        System.out.println("sorry mate, you can't activate this production ");
        turnManager.currentPhasePrint();
        break;
      case ALREADY_PRODUCED:
        System.out.println("sorry mate, you already have already used this production in this turn");
        turnManager.currentPhasePrint();
        break;
      case NOT_ENOUGH_RESOURCES:
        System.out.println("sorry mate, you don't have enough resources to perform this action ");
        turnManager.currentPhasePrint();
        break;
      case INVALID_CARD_PLACEMENT:
        System.out.println("sorry mate, you can't place your Develop card here ");
        turnManager.currentPhasePrint();
        break;
      default:
    }
  }


  public void sendMessage(Message msg) { // FIXME deve cambiare nome perchè va usata anche per il locale
    if(msg.getMessageType() != MessageType.LOGIN)
      msg.setUsername(this.username);
    messageConnector.sendToServer(msg);
  }

  public void close() {
    System.out.println("You will be disconnected... Bye shdroonzo");
    messageConnector.stop();
    System.exit(0);
  }


  public String getUsername() { return this.username; }

  public void setUsername(String username) { this.username = username; }



  /**
   * @return this client's simpleplayerstate
   */
  public SimplePlayerState getSimplePlayerState() {
    return this.simplePlayerStateMap.get(username);
  }

  /**
   * returns the simpleplayerstate belonging to the specified player
   *
   * @param username the username of the players whose simpleplayerstate is to be returned
   * @return the specified player's simpleplayerstate
   */
  public SimplePlayerState getSimplePlayerState(String username) {
    return this.simplePlayerStateMap.get(username);
  }

  public List<SimplePlayerState> otherSimplePlayerStates(){
    return simplePlayerStateMap.entrySet().stream()
            .filter(x -> !x.getKey().equals(this.username))
            .map(x -> x.getValue())
            .collect(Collectors.toList());
  }

  public List<String> otherPlayersUsername(){
    return simplePlayerStateMap.entrySet().stream()
            .filter(x -> !x.getKey().equals(this.username))
            .map(x -> x.getKey())
            .collect(Collectors.toList());
  }

  public List<String> usernameList(){
    return new ArrayList<>(this.simplePlayerStateMap.keySet());
  }

  public SimpleGameState getSimpleGameState() {
    return simpleGameState;
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

  public String getCurrentPlayer(){
    return turnManager.getCurrentPlayer();
  }

}
