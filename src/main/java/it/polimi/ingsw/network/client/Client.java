package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.LocalVirtualView;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliDrawer;
import it.polimi.ingsw.view.cli.Color;
import it.polimi.ingsw.view.gui.GUIStarter;
import javafx.application.Application;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Main class to manage the client side of the application.
 * Performs the message handling of messages coming from server.
 */
public class Client implements PhaseChangedObserver {

  public static final int MIN_PORT = Server.MIN_PORT_NUMBER;
  public static final int MAX_PORT = Server.MAX_PORT_NUMBER;

  private ViewInterface view;
  private String username = null;
  private VirtualModel virtualModel;
  private ClientModelUpdaterInterface state;
  private ClientTurnManagerInterface clientTurnManager;

  private boolean isLocal = false;

  public static void main(String[] args) {
    boolean isCli = false;
    boolean isLocal = false;

//    if(args.length > 0)
////      isLocal = true;

    if (args.length > 0)
      switch (args[0]){
        case "cli":
          isCli = true;
          break;
        case "gui":
          isCli = false;
          break;
      }

    if (isCli) {
      Client client = new Client();
      ClientModelState state = new ClientModelState();
      client.setState(state);
      ViewInterface cli = new Cli(state, new CliDrawer(state), client);
      client.setView(cli);
      client.setClientTurnManager(new CliTurnManager(client, cli, state));
      cli.setClientTurnManager(client.clientTurnManager);

      // Ask for local game
      System.out.println("Do you want to play local?["
              + Color.ANSI_RED.escape() + "y" + Color.RESET.escape() + "/"
              + Color.ANSI_RED.escape() + "n"+ Color.RESET.escape() + "]");
      Scanner in = new Scanner(System.in);
      String local = in.nextLine().toLowerCase();
      if("y".equals(local)) {
        client.setLocal();
        client.view.displayLogin();
        client.view.displayGameStarted();
        client.localGameSetup();
      }
      else
      {
        cli.displayNetworkSetup();
        client.view.displayLogin();
      }
    }
    else {
      Application.launch(GUIStarter.class);
    }
  }

  public void setLocal() {
    this.isLocal = true;
  }

  public boolean isLocal() {
    return this.isLocal;
  }

  public void setView(ViewInterface view) {
    this.view = view;
  }

  public void setState(ClientModelUpdaterInterface state) { this.state = state; }

  public void setClientTurnManager(ClientTurnManagerInterface clientTurnManager) {
    this.clientTurnManager = clientTurnManager;
  }

  /**
   * Establishes a connection with the server given IP and port to connect to
   * @param serverIP the server IP to connect to
   * @param serverPort the server  port to connect to
   * @return true if connection is successful, false otherwise
   */
  public boolean connectToServer(String serverIP, int serverPort) {
    Socket server;
    try {
      server = new Socket(serverIP, serverPort);
      //server.setSoTimeout(ConfigParameters.SERVER_TIMEOUT);
      ServerConnector serverConnector = new ServerConnector(server, this);
      this.virtualModel = new NetworkVirtualModel(serverConnector);

      Runnable threadServerConnection = () -> serverConnector.handleServerConnection();
      Thread thread = new Thread(threadServerConnection);
      thread.setName("StreamIn");
      thread.start();
    } catch (IOException e) {
      view.displaySetupFailure();
      return false;
    }
    return true;
  }

  /**
   * Sends a login message to server
   * @param create this parameter should be true if you want to create a game, false if you want to join
   * @param roomName name of the room to create or join
   */
  public void sendLogin(boolean create, String roomName){
    this.state.setClientUsername(this.username);
    if(create)
      forwardMessage(new Message(username, MessageType.CREATE_MATCH, roomName));
    else
      forwardMessage(new Message(username, MessageType.JOIN_MATCH, roomName));
  }

  /**
   * forward action to the next layer where it will be handled by the virtualModel
   * @param actionToForward the action to forward
   */
  public void forwardAction(Action actionToForward) {
    actionToForward.setUsername(username);
    virtualModel.handleAction(actionToForward);
  }

  /**
   * forward action to the next layer where it will be handled by the virtualModel
   * @param message the message to forward
   */
  public void forwardMessage(Message message) {
    if(virtualModel != null)
      virtualModel.handleMessage(message);
  }

  /**
   * print disconnection message in console, disconnects client socket
   * and terminate client execution
   */
  public void close() {
    System.out.println("You will be disconnected...");
    virtualModel.stop();
    System.exit(0);
  }

  public String getUsername() { return this.username; }

  public void setUsername(String username) {
    this.username = username;

  }

  public String getCurrentPlayer(){
    return clientTurnManager.getCurrentPlayer();
  }

  /**
   * Takes the message and handles it performing the expected action
   * based on the message type and the payload content
   * NOTE: msg cannot be null
   * @param msg the message to be handled, received from the server
   */
  public void handleMessage(Message msg) {
    if(msg == null)
      return;

    String messageUser = msg.getUsername();

    switch (msg.getMessageType()) {
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
        this.state.setClientUsername(this.username);
        view.displayLoginSuccessful(this.username);
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
        state.gameStartedSetup(msg.getPayloadByType(List.class));
        //clientTurnManager.setCurrentPlayer(getFirstPlayer(payload));
        view.displayGameStarted();
        ArrayList<String> players = msg.getPayloadByType(ArrayList.class);
        clientTurnManager.setStateIsPlayerChanged(new TurnState(getFirstPlayer(players), PhaseType.SETUP_CHOOSING_RESOURCES));
        break;
      case NEXT_TURN_STATE:
        TurnState newState = msg.getPayloadByType(TurnState.class);
        clientTurnManager.setStateIsPlayerChanged(newState);
        break;
      case LEADERCARD_SETUP: //received only by the interested player
        state.leaderSetup(messageUser, msg.getPayloadByType(LeaderSetup.class));
        view.displayRecievedLeadercards();
        break;
      case DECK_SETUP:
        state.devDeckSetup(msg.getPayloadByType(DevelopCardDeckSetup.class));
        break;
      case MARKET_SETUP:
        state.marketSetup(msg.getPayloadByType(MarketSetup.class));
        break;
      case MARKET_UPDATED:
        state.marketUpdate(msg.getPayloadByType(MarketUpdate.class));
        break;
      case DEVELOP_CARD_DECK_UPDATED:
        state.devDeckUpdate(msg.getPayloadByType(DevelopCardDeckUpdate.class));
        break;
      case WAREHOUSE_UPDATE:
        state.warehouseUpdate(messageUser, msg.getPayloadByType(WarehouseUpdate.class));
        break;
      case ACTIVATED_LEADERCARD_UPDATE:
        state.leaderUpdate(messageUser, msg.getPayloadByType(LeaderUpdate.class));
        break;
      case TRACK_UPDATED:
        state.trackUpdate(messageUser, msg.getPayloadByType(TrackUpdate.class));
        break;
      case VATICAN_REPORT:
        state.vaticanUpdate(messageUser, msg.getPayloadByType(VaticanReport.class));
        break;
      case CHEST_UPDATE:
        state.chestUpdate(messageUser, msg.getPayloadByType(ChestUpdate.class));
        break;
      case TEMP_CHEST_UPDATE:
        state.tempChestUpdate(messageUser, msg.getPayloadByType(ChestUpdate.class));
        break;
      case CARD_SLOT_UPDATE:
        state.cardSlotUpdate(messageUser, msg.getPayloadByType(CardSlotUpdate.class));
        break;
      case DISCARDED_LEADERCARD:
        state.discardedLeaderUpdate(messageUser, msg.getPayloadByType(LeaderUpdate.class));
        break;
      case GAME_ENDED:
        view.displayGameEnded(msg.getPayload());
      case CHEST_MERGED:
        state.chestMergeUpdate(messageUser);
        break;
      case STARTING_GAME_SETUP:
        view.startingSetupUpdate();
        break;
      case LORENZO_TRACK_UPDATE:
        state.lorenzoTrackUpdate(msg.getPayloadByType(TrackUpdate.class));
        view.displayLorenzoMoved();
      break;
      case LORENZO_DECK_UPDATE:
        state.lorenzoDevDeckUpdate(msg.getPayloadByType(DevelopCardDeckUpdate.class));
        view.displayLorenzoDiscarded(msg.getPayloadByType(DevelopCardDeckUpdate.class));
        break;
      case LORENZO_SHUFFLE_UPDATE:
        state.lorenzoShuffleUpdate();
        view.displayLorenzoShuffled();
        break;
      default:
    }
  }

  /**
   * handles error received from the server
   * @param errorType the error type
   */
  public void handleError(ErrorType errorType) {
    switch (errorType){
      case GAME_ALREADY_STARTED:
        view.displayGameAlreadyStarted();
        close(); //FIXME ?? perché?
        break;
      case INVALID_LOGIN_USERNAME:
        view.displayFailedLogin();
        break;
      case NOT_BUYABLE:
        view.displayNotBuyable();
        clientTurnManager.currentPhasePrint();
        break;
      case INVALID_LEADERCARD:
        view.displayInvalidLeadercard();
        clientTurnManager.currentPhasePrint();
        break;
      case INVALID_DEVELOP_CARD:
        break;
      case CANNOT_DISCARD_ACTIVE_LEADER:
        view.displayCannotDiscardActiveLeader();
        clientTurnManager.currentPhasePrint();
        break;
      case NOT_ACTIVATABLE_PRODUCTION:
        view.displayNotActivatableProduction();
        clientTurnManager.currentPhasePrint();
        break;
      case ALREADY_PRODUCED:
        view.displayAlreadyProduced();
        clientTurnManager.currentPhasePrint();
        break;
      case NOT_ENOUGH_RESOURCES:
        view.displayNotEnoughResources();
                clientTurnManager.currentPhasePrint();
        break;
      case INVALID_CARD_PLACEMENT:
        view.displayInvalidCardPlacement();
        clientTurnManager.currentPhasePrint();
        break;
      case MATCH_ALREADY_EXISTS:
        view.displayMatchAlreadyExist();
        break;
      case CANNOT_JOIN_MATCH:
        view.displayCannotJoinMatch();
        break;
      default:
    }
  }

  private String getFirstPlayer(List<String> players) {
    return players.get(0);
  }

  /**
   * Setup the client to be able to play in local mode (without needing the server)
   */
  public void localGameSetup() {
    ModelObserver localVirtualView = new LocalVirtualView(state, username, view);
    TurnManager gameTurnManager = null;
    try {
      Game game = new SinglePlayer(localVirtualView);
      gameTurnManager = new TurnManager(game, Arrays.asList(username));
      gameTurnManager.startGame();
    } catch (IOException |MaximumNumberOfPlayersException e) {
      System.out.println("A problem has occurred while creating the game.");
      close();
    }
    virtualModel = new LocalVirtualModel(gameTurnManager, clientTurnManager, this);
    clientTurnManager.setCurrentPlayer(username);
    clientTurnManager.currentPhasePrint();
    state.gameStartedSetup(new ArrayList<>(List.of(this.username)));
  }

  /**
   * Updates the turnState with the new state received by the server
   * @param nextPhase the next state received by the server
   */
  public void phaseUpdate(TurnState nextPhase) {
    clientTurnManager.setStateIsPlayerChanged(nextPhase);
    //handleTurnState(nextPhase);
  }

  public ViewInterface getView() {
    return this.view;
  }
}
