package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ShopMarketAction;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameboardController extends GUIController {

    private final int N_ROW = 3,
            N_COLUMN = 4;

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

    Circle slide;
    Circle[][] marbleGrid;

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

    public void constructMarket(String stateUpdate){
        Type token = new TypeToken<Pair<MarbleColor[][], MarbleColor>>(){}.getType();
        Pair<MarbleColor[][], MarbleColor> pair = GSON.getGsonBuilder().fromJson(stateUpdate, token);
        MarbleColor[][] marketColorMatrix = pair.getKey();
        MarbleColor slideMarble = pair.getValue();

        this.slide = new Circle(220, 55, 16,slideMarble.getGuiColor());
        deck_anchorPane.getChildren().add(slide);

        this.marbleGrid = new Circle[N_ROW][N_COLUMN];
        for(int i=0; i < marbleGrid.length; i++)
            for (int j=0; j<marbleGrid[0].length; j++) {
                marbleGrid[i][j] = new Circle(100 + j*40,100 + i*40,16, marketColorMatrix[i][j].getGuiColor());
                deck_anchorPane.getChildren().add(marbleGrid[i][j]);
            }
    }


    public void updateChest(String username, String stateUpdate){
        for(PlayerboardController p : playerboardControllers)
            if(username.equals(p.getUsername())){
                p.updateChest(stateUpdate);
            }
    }

    public void updateMarket(String stateUpdate){
        Type token = new TypeToken<Pair<Boolean, Integer>>(){}.getType();
        Pair<Boolean, Integer> pair = GSON.getGsonBuilder().fromJson(stateUpdate, token);
        boolean isRow = pair.getKey();
        int index = pair.getValue();

        if(isRow)
            changeRow(index);
        else
            changeColumn(index);

    }



    @FXML
    void pushColumn1(MouseEvent event) {
        Action marketAction = new ShopMarketAction(false, 0);
        client.forwardAction(marketAction);
        changeColumn(0);
    }

    @FXML
    void pushColumn2(MouseEvent event) {
        changeColumn(1);
    }

    @FXML
    void pushColumn3(MouseEvent event) {
        changeColumn(2);
    }

    @FXML
    void pushColumn4(MouseEvent event) {
        changeColumn(3);
    }

    @FXML
    void pushRow1(MouseEvent event) {
        changeRow(0);
    }

    @FXML
    void pushRow2(MouseEvent event) {
        changeRow(1);
    }

    @FXML
    void pushRow3(MouseEvent event) {
        changeRow(2);
    }

    private void changeRow(int row){
        Color temp = (Color) this.marbleGrid[row][0].getFill();
        for(int j=1; j<4; j++){
            this.marbleGrid[row][j-1].setFill(this.marbleGrid[row][j].getFill());
        }
        this.marbleGrid[row][3].setFill(this.slide.getFill());
        this.slide.setFill(temp);
    }

    private void changeColumn(int column){
        Color temp = (Color) this.marbleGrid[0][column].getFill();
        for(int i=1; i<3; i++){
            this.marbleGrid[i-1][column].setFill(this.marbleGrid[i][column].getFill());
        }
        this.marbleGrid[2][column].setFill(this.slide.getFill());
        this.slide.setFill(temp);
    }



}
