package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

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

    @FXML
    private GridPane marketGrid;

    @FXML
    private Circle greyMarble1;

    @FXML
    private Circle redMarble;

    @FXML
    private Circle whiteMarble1;

    @FXML
    private Circle purpleMarble1;

    @FXML
    private Circle blueMarble1;

    @FXML
    private Circle whiteMarble2;

    @FXML
    private Circle whiteMarble3;

    @FXML
    private Circle blueMarble2;

    @FXML
    private Circle whiteMarble4;

    @FXML
    private Circle purpleMarble2;

    @FXML
    private Circle greyMarble2;

    @FXML
    private Circle yellowMarble2;

    @FXML
    private Circle yellowMarble1;

    @FXML
    private ImageView rowArrow3;

    @FXML
    private ImageView columnArrow2;

    @FXML
    private ImageView rowArrow2;

    @FXML
    private ImageView rowArrow1;

    @FXML
    private ImageView columnArrow3;

    @FXML
    private ImageView columnArrow4;

    @FXML
    private ImageView columnArrow1;

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

    public void updateChest(String username, String stateUpdate){
        for(PlayerboardController p : playerboardControllers)
            if(username.equals(p.getUsername())){
                p.updateChest(stateUpdate);
            }

    }


    @FXML
    void pushColumn1(MouseEvent event) {

        System.out.println("pushColumn1");
    }

    @FXML
    void pushColumn2(MouseEvent event) {
        System.out.println("pushColumn2");
    }

    @FXML
    void pushColumn3(MouseEvent event) {
        System.out.println("pushColumn3");
    }

    @FXML
    void pushColumn4(MouseEvent event) {
        System.out.println("pushColumn4");
    }

    @FXML
    void pushRow1(MouseEvent event) {
        System.out.println("pushRow1");
        //System.out.println(marketGrid.getRowIndex(redMarble));
        changeRow(1);
    }

    @FXML
    void pushRow2(MouseEvent event) {
        System.out.println("pushRow2");
        changeRow(2);
    }

    @FXML
    void pushRow3(MouseEvent event) {
        System.out.println("pushRow3");
        changeRow(3);
    }

    private void changeRow(int row){
        Circle toSlide = (Circle) getNodeByRowColumnIndex(row, 0, marketGrid);
        for(int i = 1; i<4; i++){
            Circle circle = (Circle)getNodeByRowColumnIndex(row, i, marketGrid);
            marketGrid.getChildren().remove(circle);
            marketGrid.add(circle, row, i-1);
        }
        marketGrid.add(toSlide, 0, 3);
    }

    private void changeColumn(int column){

    }



    private Node getNodeByRowColumnIndex (final Integer row, final Integer column, GridPane pane) {
        Node result = null;
        List<Node> childrens =  pane.getChildren();

        System.out.println(childrens);

        for (Node node : childrens) {
            if(row.equals(pane.getRowIndex(node)) && column.equals(pane.getColumnIndex(node))) {
                result = node;
                break;
            }
        }
        System.out.println("return: " + result);
        return result;
    }

}
