package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.client.GuiTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.ClientModelUpdaterInterface;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.gui.controllers.ConnectController;
import it.polimi.ingsw.view.gui.controllers.GameboardController;
import it.polimi.ingsw.view.gui.controllers.LoginController;
import javafx.application.Platform;

public class GUI implements ViewInterface, ClientModelUpdaterInterface {

   private Client client;
   private GuiTurnManager turnManager;
   private SceneController sceneController;
   private String username;
   private String[] othersUsernames = {null, null, null};

   public GUI(Client client) {
      this.client = client;
      this.sceneController = new SceneController();
   }

   public SceneController getSceneController() {
      return this.sceneController;
   }

   @Override
   public void setClientTurnManager(ClientTurnManagerInterface turnManager) {
      this.turnManager = (GuiTurnManager) turnManager;
   }

   @Override
   public void displayMarbleShopping() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLeaderHand() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayMarbleChoice() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      //Platform.runLater(() -> this.sceneController.changeScene("fxml/initialChoice.fxml", this.client));

   }

   @Override
   public void displayNetworkSetup() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   /**
    * Display the form were the player can choose their username
    */
   @Override
   public void displayLogin() { // FIXME PERCHÉ VIENE MOSTRATO ANCHE SE C'`E UN ERRORE DI CONNESSIONE??
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> this.sceneController.changeScene("fxml/login.fxml", this.client));
   }

   /**
    * Show an error message for failing to open a socket with the server
    */
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
      Platform.runLater(() -> this.sceneController.changeScene("fxml/lobbyCreated.fxml", this.client));
   }

   @Override
   public void displayOtherUserJoined(Message msg) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      for(int i = 0; i < this.othersUsernames.length; i++)
         if(this.othersUsernames[i] == null)
            this.othersUsernames[i] = msg.getUsername();
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
      Platform.runLater(() -> this.sceneController.changeScene("fxml/numberOfPlayer.fxml", this.client));
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
   public void displayPlayerTurn(String player) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayYourTurn(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayDefaultCanvas(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayGameEnded(String payload) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayPlainCanvas() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void startingSetupUpdate() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         this.sceneController.changeStage("fxml/gameboard.fxml", this.client);
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.setUsername(this.username);
         controller.setOtherPlayer(this.othersUsernames);
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
   public void displayLorenzoDiscarded(DevelopCardDeck.DevelopCardDeckUpdate state) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLorenzoMoved() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void displayLorenzoShuffled() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }


   @Override
   public void setClientUsername(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void chestUpdate(String username, Chest.ChestUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug

      Platform.runLater(() -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateChest(username, stateUpdate);
      });

   }

   @Override
   public void warehouseUpdate(String username, Warehouse.WarehouseUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void leaderUpdate(String username, PlayerBoard.LeaderUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void leaderSetup(String username, Game.LeaderSetup stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.leaderSetup(username, stateUpdate);
      });
   }

   @Override
   public void marketUpdate(Market.MarketUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateMarket(stateUpdate);
      });
   }

   @Override
   public void marketSetup(Market.MarketSetup stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.constructMarket(stateUpdate);
      });
   }

   @Override
   public void cardSlotUpdate(String username, CardSlots.CardSlotUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateCardSlot(username, stateUpdate);
      });
   }

   @Override
   public void trackUpdate(String username, Track.TrackUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void vaticanUpdate(String username, Track.VaticanReport stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateVatican(username, stateUpdate);
      });
   }

   @Override
   public void devDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.updateDeck(stateUpdate);
      });
   }

   @Override
   public void devDeckSetup(DevelopCardDeck.DevelopCardDeckSetup stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.constructDeck(stateUpdate);
      });
   }

   @Override
   public void tempChestUpdate(String username, Chest.ChestUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void chestMergeUpdate(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void discardedLeaderUpdate(String username, PlayerBoard.LeaderUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void lorenzoTrackUpdate(Track.TrackUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void lorenzoShuffleUpdate() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void lorenzoDevDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void gameStartedSetup(String stateUpdate){
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      this.turnManager.setPlayers(stateUpdate);
   }

}