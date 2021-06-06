package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.LocalVirtualView;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliDrawer;
import it.polimi.ingsw.view.gui.GUIStarter;
import javafx.application.Application;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client implements PhaseChangedObserver{
  public static final int MIN_PORT = Server.MIN_PORT_NUMBER;
  public static final int MAX_PORT = Server.MAX_PORT_NUMBER;

  private ViewInterface view;
  private String username = null;
  private VirtualModel virtualModel;
  private ClientModelUpdaterInterface state;
  private ClientTurnManagerInterface clientTurnManager;


  public static void main(String[] args) {
    boolean isCli = true;
    boolean isLocal = true;

    if(args.length > 0)
      isLocal = true;

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
      client.setClientTurnManager(new CliTurnManager(client, cli, state));
      cli.setClientTurnManager(client.clientTurnManager);

      if(isLocal) {
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
      //TODO fare come sopra per il game in locale con la GUI
      Application.launch(GUIStarter.class);
    }
  }

  public void setView(ViewInterface view) {
    this.view = view;
  }

  public void setState(ClientModelUpdaterInterface state) { this.state = state; }

  public void setClientTurnManager(ClientTurnManagerInterface clientTurnManager) {
    this.clientTurnManager = clientTurnManager;
  }

  /**
   * Instantiates a connection with the server
   */
  public void connectToServer(String serverIP, int serverPort) {
    Socket server;
    try {
      server = new Socket(serverIP, serverPort);
      ServerConnector serverConnector = new ServerConnector(server, this);
      this.virtualModel = new NetworkVirtualModel(serverConnector);

      Runnable threadServerConnection = () -> serverConnector.handleServerConnection();
      Thread thread = new Thread(threadServerConnection);
      thread.setName("StreamIn");
      thread.start();

    } catch (IOException e) {
      view.displaySetupFailure();
    }
  }

  public void sendLogin(){
    state.setClientUsername(this.username);
    forwardMessage(new Message(username, MessageType.LOGIN));
  }

  public void forwardAction(Action actionToForward) {
    actionToForward.setUsername(username);
    virtualModel.handleAction(actionToForward);
  }

  public void forwardMessage(Message message) {
    if(virtualModel != null)
      virtualModel.handleMessage(message);
  }

  public void close() {
    System.out.println("You will be disconnected... Bye shdroonzo");
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

  public void handleMessage(Message msg) {
    String messageUser = msg.getUsername();
    String payload = msg.getPayload();

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
        clientTurnManager.setCurrentPlayer(getFirstPlayer(payload));
        view.displayGameStarted();
        if(username.equals(clientTurnManager.getCurrentPlayer()))
          clientTurnManager.currentPhasePrint();
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
      case CHEST_MERGED:
        state.chestMergeUpdate(messageUser);
        break;
      case STARTING_GAME_SETUP:
        view.startingSetupUpdate();
        break;
      case LORENZO_TRACK_UPDATE:
        state.lorenzoTrackUpdate(payload);
        view.displayLorenzoMoved();
      break;
      case LORENZO_DECK_UPDATE:
        state.lorenzoDevDeckUpdate(payload);
        view.displayLorenzoDiscarded(payload);
        break;
      case LORENZO_SHUFFLE_UPDATE:
        state.lorenzoShuffleUpdate();
        view.displayLorenzoShuffled();
        break;
      default:
    }
  }

  private void handleTurnState(String payload) {
    TurnManager.TurnState newState = GSON.getGsonBuilder().fromJson(payload, TurnManager.TurnState.class);
    //TODO probabilmente non serve più il fatto che setState ritorna un booleano
    if(clientTurnManager.setStateIsPlayerChanged(newState)) {
      if (username.equals(clientTurnManager.getCurrentPlayer())) {
        view.displayYourTurn(clientTurnManager.getCurrentPlayer());
      }
      else
        view.displayPlayerTurn(clientTurnManager.getCurrentPlayer());
    }

    if(username.equals(clientTurnManager.getCurrentPlayer())){
      clientTurnManager.currentPhasePrint();
    }
  }


  public void handleError(ErrorType errorType) {
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
        clientTurnManager.currentPhasePrint();
        break;
      case INVALID_LEADERCARD:
        clientTurnManager.currentPhasePrint();
        break;
      case INVALID_DEVELOP_CARD:
        break;
      case CANNOT_DISCARD_ACTIVE_LEADER:
        System.out.println("sorry mate, you can't discard an active leader card ");
        clientTurnManager.currentPhasePrint();
        break;
      case NOT_ACTIVATABLE_PRODUCTION:
        System.out.println("sorry mate, you can't activate this production ");
        clientTurnManager.currentPhasePrint();
        break;
      case ALREADY_PRODUCED:
        System.out.println("sorry mate, you already have already used this production in this turn");
        clientTurnManager.currentPhasePrint();
        break;
      case NOT_ENOUGH_RESOURCES:
        System.out.println("sorry mate, you don't have enough resources to perform this action ");
        clientTurnManager.currentPhasePrint();
        break;
      case INVALID_CARD_PLACEMENT:
        System.out.println("sorry mate, you can't place your Develop card here ");
        clientTurnManager.currentPhasePrint();
        break;
      default:
    }
  }

  private String getFirstPlayer(String payload) {
    ArrayList<String> players = GSON.getGsonBuilder().fromJson(payload, ArrayList.class);
    return players.get(0);
  }

  private void localGameSetup() {
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
  }

  @Override
  public void update(String nextPhase) {
    handleTurnState(nextPhase);
  }

  public ViewInterface getView() {
    return this.view;
  }
}
