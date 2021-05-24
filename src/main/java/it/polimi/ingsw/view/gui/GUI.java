package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.gui.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUI extends Application implements ViewInterface {

   private Client client;
   private Stage stage;

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

   @Override
   public void start(Stage stage) throws IOException {
      this.stage = stage;
      this.client = new Client();
      client.setView(this);
      this.client.setTurnManager(new ClientTurnManager(client, this));
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getClassLoader().getResource("fxml/login.fxml"));
      Parent root = loader.load();
      LoginController controller = loader.getController();
      controller.addObserver(this.client);
      this.stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/logo.png")));
      this.stage.setTitle("Login");
      this.stage.setScene(new Scene(root));
      this.stage.setResizable(false);
      this.stage.show();
   }


   private void drawCards(GraphicsContext gc) {
      gc.drawImage(GUIConfig.card1, 10, 20, 231, 349); // x  y  width  height
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
      new Stage().show();
   }

   @Override
   public void displaySetupFailure() {

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
}
