package it.polimi.ingsw.view.gui;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.client.GuiTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.utility.ConfigParameters;
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

   public GUI(Client client) {
      this.sceneController = new SceneController(client);
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
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setTempMarbleVisible();
      });
   }

   @Override
   public void displayLeaderHand() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.displayLeaderChoiceLable(this.username);
      });
   }

   @Override
   public void displayInitialResourcesChoice() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.displayInitialResourcesChoice(this.username);
      });
   }

   @Override
   public void displayNetworkSetup() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLogin() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadLogin);
   }

   @Override
   public void displaySetupFailure() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         ConnectionController controller = this.sceneController.getConnectionController();
         if(controller != null)
            controller.loginError("This server doesn't exist");
      });
   }

   @Override
   public void displayDisconnected() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayFailedLogin() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         LoginController controller = this.sceneController.getLoginController();
         if(controller != null)
            controller.loginFailed();
      });
   }

   @Override
   public void displayLoginSuccessful(String username) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      this.username = username;
   }

   @Override
   public void displayLobbyCreated() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadLobby);
   }

   @Override
   public void displayOtherUserJoined(Message msg) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadLobby);
   }

   @Override
   public void displayYouJoined() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayPlayersNumberChoice() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(this.sceneController::loadNumberOfPlayer);
   }

   @Override
   public void displayWaiting() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         LoginController loginController = this.sceneController.getLoginController();
         if(loginController != null)
            loginController.gameWait();
      });
   }

   @Override
   public void displayServerDown() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> this.sceneController.fatalError("ERROR: connection lost with server"));
   }

   @Override
   public void displayGameAlreadyStarted() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayReconnection() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayGameStarted() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayRecievedLeadercards() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayMarket() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayPlayerTurn(String player) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.otherPlayerTurn(player);
      });
   }

   @Override
   public void displayYourTurn(String username) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.enableInitialAction();
      });
   }

   @Override
   public void displayDefaultCanvas(String username) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayGameEnded(String payload) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
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
      Platform.runLater(() -> {
         EndGameController controller = this.sceneController.getEndGameController();
         if(controller != null)
            controller.setWinner(finalWinner, score);
      });

   }

   @Override
   public void displayPlainCanvas() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void startingSetupUpdate() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         this.sceneController.loadGameboard();
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setUsername(this.username);
      });
   }

   @Override
   public void displayChooseLeaderOnWhite() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.askLeaderOnWHite(this.username);
      });
   }

   @Override
   public void displayFinalPhase() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.enableFinalAction();
      });
   }

   @Override
   public void displayProducingPhase() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.enableProductionAction();
      });
   }

   @Override
   public void displayShoppingPhase() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.enableShoppingAction();
      });
   }

   @Override
   public void displayNotBuyable() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.NOT_BUYABLE);
      });
   }

   @Override
   public void displayInvalidLeadercard() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.INVALID_LEADERCARD);
      });
   }

   @Override
   public void displayCannotDiscardActiveLeader() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.CANNOT_DISCARD_ACTIVE_LEADER);
      });
   }

   @Override
   public void displayNotActivatableProduction() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.NOT_ACTIVATABLE_PRODUCTION);
      });
   }

   @Override
   public void displayAlreadyProduced() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.ALREADY_PRODUCED);
      });
   }

   @Override
   public void displayNotEnoughResources() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.NOT_ENOUGH_RESOURCES);
      });
   }

   @Override
   public void displayInvalidCardPlacement() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.setErrorLable(ClientStrings.INVALID_CARD_PLACEMENT);
      });
   }

   @Override
   public void displayMatchAlreadyExist() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         LoginController loginController = this.sceneController.getLoginController();
         if(loginController != null)
            loginController.gameAlreadyExists();
      });
   }

   @Override
   public void displayCannotJoinMatch() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         LoginController loginController = this.sceneController.getLoginController();
         if(loginController != null)
            loginController.gameCannotJoin();
      });
   }

   @Override
   public void displayFatalError(String errorMessage) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> this.sceneController.fatalError(errorMessage));
   }

   @Override
   public void displayLorenzoDiscarded(DevelopCardDeckUpdate state) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLorenzoMoved() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLorenzoShuffled() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }
//------------- ViewInterface --------------



//------------- ClientModelUpdaterInterface ---------------
   @Override
   public void setClientUsername(String username) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void chestUpdate(String username, ChestUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateChest(stateUpdate);
         }
      });

//      Platform.runLater(() -> {
//         GameboardController gameboardController = this.sceneController.getGameboardController();
//         if(gameboardController != null) {
//            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
//            if(playerboardController != null)
//               playerboardController.updateChest(stateUpdate);
//         }
//      });

   }

   @Override
   public void warehouseUpdate(String username, WarehouseUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateWarehouse(stateUpdate);
            //controller.updateWarehouse(username, stateUpdate);
         }
      });
   }

   @Override
   public void leaderUpdate(String username, LeaderUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateActivatedLeader(stateUpdate);
         }
      });
   }

   @Override
   public void leaderSetup(String username, LeaderSetup stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         //controller.getPlayerBoardController(username).leaderSetup(stateUpdate);
         if(controller != null)
            controller.leaderSetup(username, stateUpdate);
      });
   }

   @Override
   public void marketUpdate(MarketUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null) {
            if (this.username.equals(this.turnManager.getCurrentPlayer()))
               controller.updateMarket(stateUpdate);
            else
               controller.updateMarketOtherPlayers(stateUpdate);
         }
      });
   }

   @Override
   public void marketSetup(MarketSetup stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.constructMarket(stateUpdate);
      });
   }

   @Override
   public void cardSlotUpdate(String username, CardSlotUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateCardSlot(stateUpdate);
            //controller.updateCardSlot(username, stateUpdate);
         }
      });
   }

   @Override
   public void trackUpdate(String username, TrackUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updatePlayerTrack(stateUpdate);
         }
      });
   }

   @Override
   public void vaticanUpdate(String username, VaticanReport stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateVatican(stateUpdate);
         }
      });
   }

   @Override
   public void devDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.updateDeck(stateUpdate);
      });
   }

   @Override
   public void devDeckSetup(DevelopCardDeckSetup stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null)
            controller.constructDeck(stateUpdate);
      });
   }

   @Override
   public void tempChestUpdate(String username, ChestUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateTempChest(stateUpdate);
         }
      });
   }

   @Override
   public void chestMergeUpdate(String username) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         PlayerboardController playerboardController = null;
         if(gameboardController != null)
            playerboardController = gameboardController.getPlayerBoardController(username);
         if(playerboardController != null)
            playerboardController.chestMergeUpdate();
      });
   }

   @Override
   public void discardedLeaderUpdate(String username, LeaderUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(username);
            if(playerboardController != null)
               playerboardController.updateDiscardedLeader(stateUpdate);
         }
      });
   }

   @Override
   public void lorenzoTrackUpdate(TrackUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameboardController = this.sceneController.getGameboardController();
         if(gameboardController != null) {
            PlayerboardController playerboardController = gameboardController.getPlayerBoardController(this.username);
            if(playerboardController != null)
               playerboardController.updateLorenzoTrack(stateUpdate);
            gameboardController.setLorenzoToken(GuiResources.lorenzoMoveTrackToken);
         }
      });
   }

   @Override
   public void lorenzoShuffleUpdate() {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         GameboardController gameController = this.sceneController.getGameboardController();
         if(gameController != null)
            gameController.setLorenzoToken(GuiResources.lorenzoShuffleToken);
      });
   }

   @Override
   public void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null) {
            controller.updateDeck(stateUpdate);
            controller.setLorenzoToken(GuiResources.deckTokensMap.get(stateUpdate.getColumn()));
         }
      });
   }

   @Override
   public void gameStartedSetup(List<String> stateUpdate){
      if(ConfigParameters.TESTING) System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = this.sceneController.getGameboardController();
         if(controller != null) {
            controller.setOtherPlayer(stateUpdate);
            if (stateUpdate.size() == 1) {
               controller.setupLorenzo();
            }
         }
      });
   }

   //------------- ClientModelUpdaterInterface ---------------

}