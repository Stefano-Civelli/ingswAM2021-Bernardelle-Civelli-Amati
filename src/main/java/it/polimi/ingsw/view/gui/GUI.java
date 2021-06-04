package it.polimi.ingsw.view.gui;

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
   }

   @Override
   public void displayNetworkSetup() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   /**
    * Display the form were the player can choose their username
    */
   @Override
   public void displayLogin() {
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
   public void displayFailedLogin() {
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
   public void displayLorenzoDiscarded(String state) {
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
   public void chestUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void warehouseUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void leaderUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void leaderSetup(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater( () -> {
         GameboardController controller = (GameboardController) this.sceneController.getCurrentController();
         controller.leaderSetup(username, stateUpdate);
      });
   }

   @Override
   public void marketUpdate(String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void marketSetup(String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void cardSlotUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void trackUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void vaticanUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void devDeckUpdate(String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void devDeckSetup(String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void tempChestUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void chestMergeUpdate(String username) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void discardedLeaderUpdate(String username, String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void lorenzoTrackUpdate(String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void lorenzoShuffleUpdate() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void lorenzoDevDeckUpdate(String stateUpdate) {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   @Override
   public void gameStartedSetup(Message msg){
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

}