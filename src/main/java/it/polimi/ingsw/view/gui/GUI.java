package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.gui.controllers.ConnectController;
import javafx.application.Platform;

public class GUI implements ViewInterface, ModelObserver {

   private Client client;
   private ClientTurnManager turnManager;

//   @Override
//   public void start(Stage stage) throws Exception {
//      this.primaryStage = stage;
//
//      client = new Client();
//      //client.setView(this);
//
//      stage.setTitle("Masters of Renaissance");
//
//      //----------------------------------------------------
//      Group root = new Group();
//      Canvas canvas = new Canvas(600, 700);
//      GraphicsContext gc = canvas.getGraphicsContext2D();
//
//      drawCards(gc);
//
//      root.getChildren().add(canvas);
//      stage.setScene(new Scene(root));
//      //----------------------------------------------------
//
//      stage.show();
//   }


//   private void drawCards(GraphicsContext gc) {
//      gc.drawImage(GUIConfig.card1, 10, 20, 231, 349); // x  y  width  height
//   }

   public GUI(Client client) {
      this.client = client;
   }

   @Override
   public void setClientTurnManager(ClientTurnManager turnManager) {
      this.turnManager = turnManager;
   }

   @Override
   public void displayMarbleShopping() {

   }

   @Override
   public void displayLeaderHand() {

   }

   @Override
   public void displayMarbleChoice() {

   }

   @Override
   public void displaySetup() {

   }

   @Override
   public void displayLogin() {
      Platform.runLater(() -> {
         SceneController.changeScene("fxml/login.fxml");
      });
   }

   @Override
   public void displaySetupFailure() {
      Platform.runLater(() -> {
         ConnectController controller = (ConnectController) SceneController.getController();
         controller.loginError("This server doesn't exist");
      });
   }

   @Override
   public void displayDisconnected() {

   }

   @Override
   public void displayFailedLogin() {

   }

   @Override
   public void displayLoginSuccessful() {

   }

   @Override
   public void displayLobbyCreated() {

   }

   @Override
   public void displayOtherUserJoined(Message msg) {

   }

   @Override
   public void displayYouJoined() {

   }

   @Override
   public void displayPlayersNumberChoice() {
      Platform.runLater(() -> {
         SceneController.changeScene("fxml/numberOfPlayer.fxml");
      });
   }

   @Override
   public void displayWaiting() {

   }

   @Override
   public void displayServerDown() {

   }

   @Override
   public void displayGameAlreadyStarted() {

   }

   @Override
   public void displayReconnection() {

   }

   @Override
   public void displayGameStarted() {

   }

   @Override
   public void displayRecievedLeadercards() {

   }

   @Override
   public void displayMarket() {

   }

   @Override
   public void displayPlayerTurn(String player) {

   }

   @Override
   public void displayYourTurn(String username) {

   }

   @Override
   public void displayDefaultCanvas(String username) {

   }

   @Override
   public void displayGameEnded(String payload) {

   }

   @Override
   public void displayPlainCanvas() {}

   @Override
   public void chestUpdate(String stateUpdate) {

   }

   @Override
   public void warehouseUpdate(String stateUpdate) {

   }

   @Override
   public void leaderUpdate(String stateUpdate) {

   }

   @Override
   public void leaderSetupUpdate(String username, String stateUpdate) {

   }

   @Override
   public void marketUpdate(String stateUpdate) {

   }

   @Override
   public void marketSetupUpdate(String stateUpdate) {

   }

   @Override
   public void cardSlotUpdate(String stateUpdate) {

   }

   @Override
   public void trackUpdate(String username, String stateUpdate) {

   }

   @Override
   public void vaticanUpdate(String username, String stateUpdate) {

   }

   @Override
   public void devDeckUpdate(String stateUpdate) {

   }

   @Override
   public void devDeckSetup(String stateUpdate) {

   }

   @Override
   public void tempChestUpdate(String stateUpdate) {

   }

   @Override
   public void discardedLeaderUpdate(String stateUpdate) {

   }

   @Override
   public void endGameUpdate(String stateUpdate) {

   }

   @Override
   public void lorenzoTrackUpdate(String stateUpdate) {

   }

   @Override
   public void lorenzoShuffleUpdate() {

   }

   @Override
   public void lorenzoDevDeckUpdate(String stateUpdate) {

   }
}
