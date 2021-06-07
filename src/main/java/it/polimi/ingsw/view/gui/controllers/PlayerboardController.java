package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Type;
import java.util.List;

public class PlayerboardController extends GUIController {

    @FXML
    private ImageView leader0_ImageView;
    @FXML
    private ImageView leader1_ImageView;
    @FXML
    private ImageView leader2_ImageView;
    @FXML
    private ImageView leader3_ImageView;

    @FXML
    private Label coinCounter;

    @FXML
    private Label servantCounter;

    @FXML
    private Label stoneCounter;

    @FXML
    private Label shieldCounter;

    @FXML
    private GridPane trackGrid;

    @FXML
    private void initialize() {
        this.leader0_ImageView.setVisible(false);
        this.leader1_ImageView.setVisible(false);
        this.leader2_ImageView.setVisible(false);
        this.leader3_ImageView.setVisible(false);
    }

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void leaderSetup(List<Integer> ledersID) {
        try {

            // FIRST IMAGE
            String url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(0)).getImage();
            this.leader0_ImageView.setImage(new Image(url));
            this.leader0_ImageView.setVisible(true);

            // SECOND IMAGE
            url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(1)).getImage();
            this.leader1_ImageView.setImage(new Image(url));
            this.leader1_ImageView.setVisible(true);

            // THIRD IMAGE
            url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(2)).getImage();
            this.leader2_ImageView.setImage(new Image(url));
            this.leader2_ImageView.setVisible(true);

            // FOURTH IMAGE
            url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(3)).getImage();
            this.leader3_ImageView.setImage(new Image(url));
            this.leader3_ImageView.setVisible(true);

        } catch (IndexOutOfBoundsException ignored) {
            // Don't print tack trace because it's normal entering here
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
    }

    public void updateChest(Chest.ChestUpdate stateUpdate){
        ResourceType resource = stateUpdate.getResourceType();
        int quantity = stateUpdate.getQuantity();

        switch(resource){
            case GOLD:
                coinCounter.setText(Integer.toString(quantity));
                break;
            case STONE:
                stoneCounter.setText(Integer.toString(quantity));
                break;
            case SHIELD:
                shieldCounter.setText(Integer.toString(quantity));
                break;
            case SERVANT:
                servantCounter.setText(Integer.toString(quantity));
                break;
        }
    }

    public void updateTrack(String payload){
//        trackGrid.add();
//        trackGrid.getChildren().remove(1,2);
    }


}
