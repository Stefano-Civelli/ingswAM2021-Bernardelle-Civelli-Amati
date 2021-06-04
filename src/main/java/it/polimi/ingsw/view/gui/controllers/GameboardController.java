package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final PlayerboardController [] playerboardControllers = {null, null, null, null};

    @FXML
    private void initialize() {
        // TODO caricare fxml market e develop card deck
    }

    public void setUsername(String username) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource("fxml/playerboard.fxml"));
            Parent root = null;
            root = loader.load();
            this.playerboardControllers[0] = loader.getController();
            this.playerboardControllers[0].setUsername(username);
            this.player_anchorPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace(); //TODO gestire
        }
    }

    public void setOtherPlayer(String[] usernames) {
        try {
            for(int i = 1; i < this.playerboardControllers.length && i <= usernames.length; i++) {
                if(usernames[i - 1] != null) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SceneController.class.getClassLoader().getResource("fxml/othersPlayerboard.fxml"));
                    Parent root1 = loader.load();
                    this.playerboardControllers[i] = loader.getController();
                    this.playerboardControllers[i].setUsername(usernames[i - 1]);
                    this.player1_anchorPane.getChildren().add(root1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); //TODO gestire
        }
    }

    public void leaderSetup(String username, String stateUpdate) {
        PlayerboardController playerController = Arrays.stream(this.playerboardControllers).filter(Objects::nonNull).filter(controller -> controller.getUsername().equals(username))
                .collect(Collectors.toList()).get(0);
        Type listType = new TypeToken<ArrayList<Integer>>(){}.getType();
        List<Integer> leadersID = GSON.getGsonBuilder().fromJson(stateUpdate, listType);
        playerController.leaderSetup(leadersID);
    }

}
