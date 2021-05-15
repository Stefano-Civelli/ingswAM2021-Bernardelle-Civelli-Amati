package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUI extends Application{

   private Stage primaryStage;
   private Client client;

   @Override
   public void start(Stage stage) throws Exception {
      this.primaryStage = stage;

      client = new Client();
      //client.setView(this);

      stage.setTitle("Masters of Reinassance");

      //----------------------------------------------------
      Group root = new Group();
      Canvas canvas = new Canvas(600, 700);
      GraphicsContext gc = canvas.getGraphicsContext2D();

      drawCards(gc);

      root.getChildren().add(canvas);
      stage.setScene(new Scene(root));
      //----------------------------------------------------

      stage.show();
   }


   private void drawCards(GraphicsContext gc) {
      gc.drawImage(GUIConfig.card1, 10, 20, 231, 349); // x  y  width  height
   }
}
