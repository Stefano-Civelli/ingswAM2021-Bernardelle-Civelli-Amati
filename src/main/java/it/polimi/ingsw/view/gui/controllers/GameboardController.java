package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameboardController extends GUIController {

    @FXML
    private AnchorPane player_anchorPane;
    @FXML
    private AnchorPane player1_anchorPane;
    @FXML
    private AnchorPane player2_anchorPane;
    @FXML
    private AnchorPane player3_anchorPane;
    @FXML
    private AnchorPane deck_anchorPane;
    @FXML
    private AnchorPane market_anchorPane;

    @FXML
    private void initialize() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getClassLoader().getResource("fxml/playerboard.fxml"));
        try {
            Parent root = loader.load();
            this.player_anchorPane.getChildren().add(root);
            this.player1_anchorPane.getChildren().add(root);
            this.player2_anchorPane.getChildren().add(root);
            this.player3_anchorPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace(); //TODO gestire
        }
    }

}
