package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUI extends Application /*implements ViewInterface*/ {


   private Client client;

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
      System.out.println(getClass().getClassLoader().getResource("fxml/login.fxml"));
      Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
      stage.setTitle("Login");
      stage.setScene(new Scene(root));
      stage.setResizable(false);
      stage.show();
   }


   private void drawCards(GraphicsContext gc) {
      gc.drawImage(GUIConfig.card1, 10, 20, 231, 349); // x  y  width  height
   }
}
