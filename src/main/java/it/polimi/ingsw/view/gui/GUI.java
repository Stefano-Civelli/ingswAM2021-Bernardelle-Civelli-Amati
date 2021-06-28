package it.polimi.ingsw.view.gui;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.client.GuiTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.ClientModelUpdaterInterface;
import it.polimi.ingsw.view.ClientStrings;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Platform;
import java.lang.reflect.Type;
import java.util.List;

public class GUI implements ViewInterface, ClientModelUpdaterInterface {

   private GuiTurnManager turnManager;
   private final SceneController sceneController;
   private String username;
   private boolean local = false;

   public GUI(Client client) {
      this.sceneController = new SceneController(client);
   }

   public void local() {
      this.local = true;
   }

   public boolean isLocal() {
      return this.local;
   }

   public SceneController getSceneController() {
      return this.sceneController;
   }

//------------- ViewInterface --------------
   @Override
   public void setClientTurnManager(ClientTurnManagerInterface turnManager) {
      this.turnManager = (GuiTurnManager) turnManager;
   }

   @Override
   public void displayMarbleShopping() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setTempMarbleVisible();
      });
   }

   @Override
   public void displayLeaderHand() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.displayLeaderChoiceLable(this.username);
      });
   }

   @Override
   public void displayMarbleChoice() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.displayMarbleChoice(this.username);
      });
   }

   @Override
   public void displayNetworkSetup() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLogin() { //FIXME PERCHÉ VIENE MOSTRATO ANCHE SE C'`E UN ERRORE DI CONNESSIONE??
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadLogin);
   }

   @Override
   public void displaySetupFailure() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         ConnectController controller = (ConnectController) this.sceneController.getCurrentController();
         controller.loginError("This server doesn't exist");
      });
   }

   @Override
   public void displayDisconnected() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayFailedLogin() { // FIXME PERCHÉ VIENE SUBITO "RISCRITTO" DA UNA CHIAMATA A displayLogin DA PARTE DEL CLIENT??
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         LoginController controller = (LoginController) this.sceneController.getCurrentController();
         controller.loginFailed();
      });
   }

   @Override
   public void displayLoginSuccessful(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      this.username = username;
   }

   /**
    *
    */
   @Override
   public void displayLobbyCreated() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadLobby);
   }

   @Override
   public void displayOtherUserJoined(Message msg) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayYouJoined() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   /**
    * Display the form where the first player can choose the number of players
    */
   @Override
   public void displayPlayersNumberChoice() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadNumberOfPlayer);
   }

   @Override
   public void displayWaiting() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayServerDown() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayGameAlreadyStarted() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayReconnection() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayGameStarted() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayRecievedLeadercards() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayMarket() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayPlayerTurn(String player) { //FIXME viene chiamata dopo game ended
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.otherPlayerTurn(player);
      });
   }

   @Override
   public void displayYourTurn(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.enableInitialAction();
      });
   }

   @Override
   public void displayDefaultCanvas(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayGameEnded(String payload) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Type token = new TypeToken<Pair<String, Integer>>(){}.getType();
      Pair<String, Integer> winnerAndScore = GSON.getGsonBuilder().fromJson(payload, token);
      String winner = winnerAndScore.getKey();
      int score = winnerAndScore.getValue();
      if(this.username.equals(winner))
         Platform.runLater(this.sceneController::loadEndGameWin);
      else
         Platform.runLater(this.sceneController::loadEndGameLose);
      if("".equals(winner))
         winner = "Lorenzo Il Magnifico";
      final String finalWinner = winner; // necessary for lambda
      Platform.runLater(() -> ((EndGameController) this.sceneController.getCurrentController()).setWinner(finalWinner, score));

   }

   @Override
   public void displayPlainCanvas() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void startingSetupUpdate() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         this.sceneController.loadGameboard();
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setUsername(this.username);
      });
   }

   @Override
   public void displayChooseLeaderOnWhite() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.askLeaderOnWHite(this.username);
      });
   }

   @Override
   public void displayFinalPhase() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.enableFinalAction();
      });
   }

   @Override
   public void displayProducingPhase() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.enableProductionAction();
      });
   }

   @Override
   public void displayShoppingPhase() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.enableShoppingAction();
      });
   }

   @Override
   public void displayNotBuyable() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.NOT_BUYABLE);
      });
   }

   @Override
   public void displayInvalidLeadercard() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.INVALID_LEADERCARD);
      });
   }

   @Override
   public void displayCannotDiscardActiveLeader() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.CANNOT_DISCARD_ACTIVE_LEADER);
      });
   }

   @Override
   public void displayNotActivatableProduction() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.NOT_ACTIVATABLE_PRODUCTION);
      });
   }

   @Override
   public void displayAlreadyProduced() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.ALREADY_PRODUCED);
      });
   }

   @Override
   public void displayNotEnoughResources() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.NOT_ENOUGH_RESOURCES);
      });
   }

   @Override
   public void displayInvalidCardPlacement() {
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setErrorLable(ClientStrings.INVALID_CARD_PLACEMENT);
      });
   }

   @Override
   public void displayLorenzoDiscarded(DevelopCardDeckUpdate state) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
//      Platform.runLater( () -> {
//         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
//         controller.setLorenzoToken(GuiResources.deckTokensMap.get(state.getColumn()));
//      });
   }

   @Override
   public void displayLorenzoMoved() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
//      Platform.runLater(() -> {
//         GameboardController gameController = (GameboardController) this.sceneController.getCurrentController();
//         gameController.setLorenzoToken(GuiResources.lorenzoMoveTrackToken);
//      });
   }

   @Override
   public void displayLorenzoShuffled() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
//      Platform.runLater(() -> {
//         GameboardController gameController = (GameboardController) this.sceneController.getCurrentController();
//         gameController.setLorenzoToken(GuiResources.lorenzoShuffleToken);
//      });
   }
//------------- ViewInterface --------------



//------------- ClientModelUpdaterInterface ---------------
   @Override
   public void setClientUsername(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void chestUpdate(String username, ChestUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.getPlayerBoardController(username).updateChest(stateUpdate);
      });

      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.getPlayerBoardController(username).updateChest(stateUpdate);
      });

   }

   @Override
   public void warehouseUpdate(String username, WarehouseUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.getPlayerBoardController(username).updateWarehouse(stateUpdate);
         //controller.updateWarehouse(username, stateUpdate);
      });
   }

   @Override
   public void leaderUpdate(String username, LeaderUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         PlayerboardController controller = ((GameboardController) this.sceneController.getCurrentController()).getPlayerBoardController(username);
         controller.updateActivatedLeader(stateUpdate);
      });
   }

   @Override
   public void leaderSetup(String username, LeaderSetup stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         //controller.getPlayerBoardController(username).leaderSetup(stateUpdate);
         controller.leaderSetup(username, stateUpdate);
      });
   }

   @Override
   public void marketUpdate(MarketUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         if(this.username.equals(this.turnManager.getCurrentPlayer()))
            controller.updateMarket(stateUpdate);
         else
            controller.updateMarketOtherPlayers(stateUpdate);
      });
   }

   @Override
   public void marketSetup(MarketSetup stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.constructMarket(stateUpdate);
      });
   }

   @Override
   public void cardSlotUpdate(String username, CardSlotUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.getPlayerBoardController(username).updateCardSlot(stateUpdate);
         //controller.updateCardSlot(username, stateUpdate);
      });
   }

   @Override
   public void trackUpdate(String username, TrackUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.getPlayerBoardController(username).updatePlayerTrack(stateUpdate);
      });
   }

   @Override
   public void vaticanUpdate(String username, VaticanReport stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.getPlayerBoardController(username).updateVatican(stateUpdate);
      });
   }

   @Override
   public void devDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateDeck(stateUpdate);
      });
   }

   @Override
   public void devDeckSetup(DevelopCardDeckSetup stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.constructDeck(stateUpdate);
      });
   }

   @Override
   public void tempChestUpdate(String username, ChestUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         PlayerboardController controller = ((GameboardController) this.sceneController.getCurrentController()).getPlayerBoardController(username);
         controller.updateTempChest(stateUpdate);
      });
   }

   @Override
   public void chestMergeUpdate(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         PlayerboardController controller = ((GameboardController) this.sceneController.getCurrentController()).getPlayerBoardController(username);
         controller.chestMergeUpdate();
      });
   }

   @Override
   public void discardedLeaderUpdate(String username, LeaderUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         PlayerboardController controller = ((GameboardController) this.sceneController.getCurrentController()).getPlayerBoardController(username);
         controller.updateDiscardedLeader(stateUpdate);
      });
   }

   @Override
   public void lorenzoTrackUpdate(TrackUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         PlayerboardController controller = ((GameboardController) this.sceneController.getCurrentController()).getPlayerBoardController(this.username);
         controller.updateLorenzoTrack(stateUpdate);
         GameboardController gameController = (GameboardController) this.sceneController.getCurrentController();
         gameController.setLorenzoToken(GuiResources.lorenzoMoveTrackToken);
      });
   }

   @Override
   public void lorenzoShuffleUpdate() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameController = (GameboardController) this.sceneController.getCurrentController();
         gameController.setLorenzoToken(GuiResources.lorenzoShuffleToken);
      });
   }

   @Override
   public void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateDeck(stateUpdate);
         controller.setLorenzoToken(GuiResources.deckTokensMap.get(stateUpdate.getColumn()));
      });
   }

   @Override
   public void gameStartedSetup(List<String> stateUpdate){
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setOtherPlayer(stateUpdate);
         if(stateUpdate.size() == 1) {
            controller.setupLorenzo();
         }
      });
   }

   //------------- ClientModelUpdaterInterface ---------------
}