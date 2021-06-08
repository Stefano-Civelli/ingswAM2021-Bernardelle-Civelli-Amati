package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ShopMarketAction;
import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.cli.drawer.DevelopCardConstructor;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    private GridPane deckGridPane;
    @FXML
    private AnchorPane market_anchorPane;
    @FXML
    private Button playerboard4Button;
    @FXML
    private Button playerboard1Button;
    @FXML
    private Button playerboard2Button;
    @FXML
    private Button playerboard3Button;
    @FXML
    private Button endTurnButton;

    private Circle slide;
    private Circle[][] marbleGrid;
    private List<Integer>[][] developCardDeck;
    private ImageView[][] imagesDevelopCardDeck = new ImageView[N_ROW][N_COLUMN];

    private final PlayerboardController [] playerboardControllers = {null, null, null, null};

    @FXML
    private void initialize() {
//        imagesDevelopCardDeck = new ImageView[N_ROW][N_COLUMN];
//        for (int i = 0; i < imagesDevelopCardDeck.length; i++) {
//            for (int j = 0; j < imagesDevelopCardDeck[i].length; j++) {
//                //imagesDevelopCardDeck[i][j] =
//            }
//        }
        // TODO caricare fxml market e develop card deck
    }


    public void constructDeck(DevelopCardDeck.DevelopCardDeckSetup stateSetup) {
        this.developCardDeck = stateSetup.getDevDeck();
        String url;
        for (int i = 0; i < developCardDeck.length; i++) {
            for (int j = 0; j < developCardDeck[i].length; j++) {
                try {
                    url = "images/front/" + DevelopCardConstructor.getDevelopCardFromId(developCardDeck[i][j].get(developCardDeck[i][j].size()-1)).getImage();
                    imagesDevelopCardDeck[i][j] = new ImageView(new Image(url));
                    imagesDevelopCardDeck[i][j].setFitHeight(138);
                    imagesDevelopCardDeck[i][j].setFitWidth(90);
                    assignMethodToCard(imagesDevelopCardDeck[i][j], i, j);
                    deckGridPane.add(imagesDevelopCardDeck[i][j],j,i);
                } catch (InvalidCardException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void assignMethodToCard(ImageView image, int i, int j){
        switch(i){
            case 0:
                switch(j){
                    case 0:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard1(event));
                        break;
                    case 1:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard2(event));
                        break;
                    case 2:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard3(event));
                        break;
                    case 3:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard4(event));
                        break;
                }
            break;
            case 1:
                switch(j){
                    case 0:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard5(event));
                        break;
                    case 1:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard6(event));
                        break;
                    case 2:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard7(event));
                        break;
                    case 3:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard8(event));
                        break;
                }
                break;
            case 2:
                switch(j){
                    case 0:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard9(event));
                        break;
                    case 1:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard10(event));
                        break;
                    case 2:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard11(event));
                        break;
                    case 3:
                        image.setOnMouseClicked((MouseEvent event) -> buyCard12(event));
                        break;
                }
                break;

        }


    }

    public void updateDeck(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate) {
        int row = stateUpdate.getRow();
        int column = stateUpdate.getColumn();

        developCardDeck[row][column].remove(developCardDeck[row][column].size()-1);
        if(!developCardDeck[row][column].isEmpty()) {
            String url = null;
            try {
                url = "images/front/" + DevelopCardConstructor.getDevelopCardFromId(developCardDeck[row][column].get(developCardDeck[row][column].size() - 1)).getImage();
            } catch (InvalidCardException e) {
                e.printStackTrace();
            }
            imagesDevelopCardDeck[row][column].setImage(new Image(url));
        }
        else {
            imagesDevelopCardDeck[row][column].setVisible(false);
        }
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

    public void leaderSetup(String username, Game.LeaderSetup stateUpdate) {
        PlayerboardController playerController = Arrays.stream(this.playerboardControllers).filter(Objects::nonNull).filter(controller -> controller.getUsername().equals(username))
                .collect(Collectors.toList()).get(0);
        List<Integer> leadersID = stateUpdate.getLeaderList();
        playerController.leaderSetup(leadersID);
    }

    public void constructMarket(Market.MarketSetup stateUpdate){
        MarbleColor[][] marketColorMatrix = stateUpdate.getMarbleMatrix();
        MarbleColor slideMarble = stateUpdate.getSlide();

        this.slide = new Circle(140, 10, 16,slideMarble.getGuiColor());
        market_anchorPane.getChildren().add(slide);

        this.marbleGrid = new Circle[N_ROW][N_COLUMN];
        for(int i=0; i < marbleGrid.length; i++)
            for (int j=0; j<marbleGrid[0].length; j++) {
                marbleGrid[i][j] = new Circle(20 + j*40,55 + i*40,16, marketColorMatrix[i][j].getGuiColor());
                market_anchorPane.getChildren().add(marbleGrid[i][j]);
            }
    }

    public void updateChest(String username, Chest.ChestUpdate stateUpdate){
        for(PlayerboardController p : playerboardControllers)
            if(username.equals(p.getUsername())){
                p.updateChest(stateUpdate);
            }
    }

    public void updateMarket(Market.MarketUpdate stateUpdate) {
        boolean isRow = stateUpdate.getIsRow();
        int index = stateUpdate.getIndex();

        if(isRow)
            changeRow(index);
        else
            changeColumn(index);
    }



    private void createBuyCardAction(int row, int column){
        //TODO è così solo per testing, sta roba in realtà sta solo nell'update
        updateDeck(new DevelopCardDeck.DevelopCardDeckUpdate(row, column));
    }

    @FXML
    void buyCard1(MouseEvent event) {
        createBuyCardAction(0, 0);
    }

    @FXML
    void buyCard2(MouseEvent event) { createBuyCardAction(0, 1); }

    @FXML
    void buyCard3(MouseEvent event) { createBuyCardAction(0, 2); }

    @FXML
    void buyCard4(MouseEvent event) { createBuyCardAction(0, 3); }

    @FXML
    void buyCard5(MouseEvent event) { createBuyCardAction(1, 0); }

    @FXML
    void buyCard6(MouseEvent event) { createBuyCardAction(1, 1); }

    @FXML
    void buyCard7(MouseEvent event) { createBuyCardAction(1, 2); }

    @FXML
    void buyCard8(MouseEvent event) { createBuyCardAction(1, 3); }

    @FXML
    void buyCard9(MouseEvent event) { createBuyCardAction(2, 0); }

    @FXML
    void buyCard10(MouseEvent event) { createBuyCardAction(2, 1); }

    @FXML
    void buyCard11(MouseEvent event) { createBuyCardAction(2, 2); }

    @FXML
    void buyCard12(MouseEvent event) { createBuyCardAction(2, 3); }


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

    @FXML
    void endTurn(ActionEvent event) {

    }

    @FXML
    void showPlayerboard1(ActionEvent event) {

    }

    @FXML
    void showPlayerboard2(ActionEvent event) {

    }

    @FXML
    void showPlayerboard3(ActionEvent event) {

    }

    @FXML
    void showPlayerboard4(ActionEvent event) {

    }


}
