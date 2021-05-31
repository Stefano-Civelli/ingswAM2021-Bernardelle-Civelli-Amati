package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.gui.controllers.ConnectController;
import it.polimi.ingsw.view.gui.controllers.LoginController;
import javafx.application.Platform;

public class GUI implements ViewInterface {

   private Client client;
   private ClientTurnManager turnManager;
   private SceneController sceneController;

   public GUI(Client client) {
      this.client = client;
      this.sceneController = new SceneController();
   }

   public SceneController getSceneController() {
      return this.sceneController;
   }

   @Override
   public void setClientTurnManager(ClientTurnManager turnManager) {
      this.turnManager = turnManager;
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
   public void displaySetup() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   /**
    * Display the form were the player can choose their username
    */
   @Override
   public void displayLogin() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         this.sceneController.changeScene("fxml/login.fxml", this.client);
      });
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
   public void displayLoginSuccessful() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
   }

   /**
    *
    */
   @Override
   public void displayLobbyCreated() {
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         this.sceneController.changeScene("fxml/lobbyCreated.fxml", this.client);
      });
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
      Platform.runLater(() -> {
         this.sceneController.changeScene("fxml/numberOfPlayer.fxml", this.client);
      });
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
      System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); // print method name for debug
      Platform.runLater(() -> {
         this.sceneController.changeStage("fxml/gameboard.fxml", this.client);
      });
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

}
