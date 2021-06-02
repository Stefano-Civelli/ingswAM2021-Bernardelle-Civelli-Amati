package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliDrawer;
import it.polimi.ingsw.view.gui.GUIStarter;
import it.polimi.ingsw.view.gui.ViewObserver;
import javafx.application.Application;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client implements ViewObserver {
  public static final int MIN_PORT = Server.MIN_PORT_NUMBER;
  public static final int MAX_PORT = Server.MAX_PORT_NUMBER;


//  private Socket server;
  private ViewInterface view;
  private String username = null;
  private String serverIP;
  private int serverPort;
  private MessageHandler messageConnector;
  private ClientModelUpdaterInterface state;
  private ClientTurnManagerInterface  turnManager;


  public static void main(String[] args) {
    boolean isCli = true;

    if (args.length > 0)
      switch (args[0]){
        case "cli":
          isCli = true;
          break;
        case "gui":
          isCli = false;
          break;
      }

    //devo fargli scegliere se giocare in locale o network
    if (isCli) {
      Client client = new Client();
      ClientModelState state = new ClientModelState();
      client.setState(state);
      ViewInterface cli = new Cli(state, new CliDrawer(state), client);
      client.setView(cli);
      client.setTurnManager(new CliTurnManager(client, cli, state));
      cli.setClientTurnManager(client.turnManager);
      //////////////////////////////////////////////////////////////////////////////////////////
      //TODO ripensare al login
      cli.displaySetup();
      client.connectToServer();
    }
    else {
      Application.launch(GUIStarter.class);
    }
  }

  public void setView(ViewInterface view) {
    this.view = view;
  }

  public void setState(ClientModelUpdaterInterface state) { this.state = state;}

  public void setTurnManager(ClientTurnManagerInterface turnManager) {
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
    Socket server;
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
    state.setClientUsername(this.username);
    //FIXME cazzo
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

  public String getCurrentPlayer(){
    return turnManager.getCurrentPlayer();
  }

  public void handleMessage(Message msg){
    String messageUser = msg.getUsername();
    String payload = msg.getPayload();

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
        state.gameStartedSetup(msg);
        turnManager.setCurrentPlayer(getFirstPlayer(payload));
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
        state.leaderSetup(messageUser, payload);
        view.displayRecievedLeadercards();
        break;
      case DECK_SETUP:
        state.devDeckSetup(payload);
        break;
      case MARKET_SETUP:
        state.marketSetup(payload);
        break;
      case MARKET_UPDATED:
        state.marketUpdate(payload);
        break;
      case DEVELOP_CARD_DECK_UPDATED:
        state.devDeckUpdate(payload);
        break;
      case WAREHOUSE_UPDATE:
        state.warehouseUpdate(messageUser, payload);
        break;
      case ACTIVATED_LEADERCARD_UPDATE:
        state.leaderUpdate(messageUser, payload);
        break;
      case TRACK_UPDATED:
        state.trackUpdate(messageUser, payload);
        break;
      case VATICAN_REPORT:
        state.vaticanUpdate(messageUser, payload);
        break;
      case CHEST_UPDATE:
        state.chestUpdate(messageUser, payload);
        break;
      case TEMP_CHEST_UPDATE:
        state.tempChestUpdate(messageUser, payload);
        break;
      case CARD_SLOT_UPDATE:
        state.cardSlotUpdate(messageUser, payload);
        break;
      case DISCARDED_LEADERCARD:
        state.discardedLeaderUpdate(messageUser, payload);
        break;
      case GAME_ENDED:
        view.displayGameEnded(msg.getPayload());
        messageConnector.stop();
      case CHEST_MERGED:
        state.chestMergeUpdate(messageUser);
        break;
      case STARTING_GAME_SETUP:
        view.startingSetupUpdate();
        break;
      case LORENZO_TRACK_UPDATE:
      break; //TODO
      case LORENZO_DECK_UPDATE:
        break; //TODO
      case LORENZO_SHUFFLE_UPDATE:
        break; //TODO
      default:
    }
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

  private String getFirstPlayer(String payload) {
    ArrayList<String> players = GSON.getGsonBuilder().fromJson(payload, ArrayList.class);
    return players.get(0);
  }
}
