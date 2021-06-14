package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.view.cli.drawer.DevelopCardConstructor;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameboardController extends GUIController {

    private final int N_ROW = 3,
            N_COLUMN = 4;

    @FXML
    private AnchorPane player_anchorPane;
    @FXML
    private GridPane deckGridPane;
    @FXML
    private AnchorPane market_anchorPane;
    @FXML
    private Button myPlayerboardButton;
    @FXML
    private Button playerboard1Button;
    @FXML
    private Button playerboard2Button;
    @FXML
    private Button playerboard3Button;
    @FXML
    private Button endTurnButton;
    @FXML
    private HBox slotSelectionHbox;
    @FXML
    private Button selectSlot1;
    @FXML
    private Button selectSlot2;
    @FXML
    private Button selectSlot3;
    @FXML
    private HBox tempMarbleHbox;
    @FXML
    private Label turnPhaseLable;
    @FXML
    private GridPane choseResourcesGridPane;

    private Circle slide;
    private Circle[][] marbleGrid;
    private List<Integer>[][] developCardDeck;
    private ImageView[][] imagesDevelopCardDeck = new ImageView[N_ROW][N_COLUMN];
    private List<Parent> playerboardList = new ArrayList<>();
    private List<Button> otherPlayerboardButtons = new ArrayList<>();
    private int selectedCardRow;
    private int selectedCardColumn;
    private int i = 2; //TODO usato solo per testing
    private int numberOfInitRes = 0;
    private Map<ResourceType, Integer> chosenResourceMap = new HashMap<>();

    private List<PlayerboardController> playerboardControllers = new ArrayList<>(); //TODO sarebbe meglio avere una lista

    @FXML
    private void initialize() {
        otherPlayerboardButtons.add(playerboard1Button);
        otherPlayerboardButtons.add(playerboard2Button);
        otherPlayerboardButtons.add(playerboard3Button);
//        for(Button b : otherPlayerboardButtons){
//            b.setVisible(false);
//        }
        playerboard1Button.setVisible(false);
        playerboard2Button.setVisible(false);
        playerboard3Button.setVisible(false);
        slotSelectionHbox.setVisible(false);
        endTurnButton.setDisable(true);
        turnPhaseLable.setVisible(false);
        choseResourcesGridPane.setVisible(false);
        // TODO caricare fxml market e develop card deck
    }


    public void constructDeck(DevelopCardDeck.DevelopCardDeckSetup stateSetup) {
        this.developCardDeck = stateSetup.getDevDeck();
        String url;
        for (int i = 0; i < developCardDeck.length; i++) {
            for (int j = 0; j < developCardDeck[i].length; j++) {
                try {
                    url = "images/front/" + DevelopCardConstructor.getDevelopCardFromId(developCardDeck[i][j].get(developCardDeck[i][j].size() - 1)).getImage();
                    imagesDevelopCardDeck[i][j] = new ImageView(new Image(url));
                    imagesDevelopCardDeck[i][j].setFitHeight(138);
                    imagesDevelopCardDeck[i][j].setFitWidth(90);
                    imagesDevelopCardDeck[i][j].setOnMouseEntered((MouseEvent event) -> mouseHover(event));
                    imagesDevelopCardDeck[i][j].setOnMouseExited((MouseEvent event) -> mouseHoverReset(event));
                    assignMethodToCard(imagesDevelopCardDeck[i][j], i, j);
                    deckGridPane.add(imagesDevelopCardDeck[i][j], j, i);
                } catch (InvalidCardException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void assignMethodToCard(ImageView image, int i, int j) {
        switch (i) {
            case 0:
                switch (j) {
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
                switch (j) {
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
                switch (j) {
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

        developCardDeck[row][column].remove(developCardDeck[row][column].size() - 1);
        if (!developCardDeck[row][column].isEmpty()) {
            String url = null;
            try {
                url = "images/front/" + DevelopCardConstructor.getDevelopCardFromId(developCardDeck[row][column].get(developCardDeck[row][column].size() - 1)).getImage();
            } catch (InvalidCardException e) {
                e.printStackTrace();
            }
            imagesDevelopCardDeck[row][column].setImage(new Image(url));
        } else {
            imagesDevelopCardDeck[row][column].setVisible(false);
        }
    }

    public void setUsername(String username) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource("fxml/playerboard.fxml"));
            Parent root = null;
            root = loader.load();
            PlayerboardController newPlayerboardController = loader.getController();
            newPlayerboardController.setUsername(username); //FIXME sembra che non vada
            newPlayerboardController.setClient(this.client);
            this.playerboardControllers.add(newPlayerboardController);
//            this.playerboardControllers[0] = loader.getController();
//            this.playerboardControllers[0].setUsername(username);
//            this.playerboardControllers[0].setClient(this.client);
            this.player_anchorPane.getChildren().add(root);
            playerboardList.add(root);
        } catch (IOException e) {
            e.printStackTrace(); //TODO gestire
        }
    }

    public void setOtherPlayer(List<String> players) {

        for (int i = 0; i < players.size() - 1; i++) {
            otherPlayerboardButtons.get(i).setVisible(true);
        }

        PlayerboardController myPlayerboardController = getPlayerBoardController(client.getUsername());
        this.playerboardControllers = new ArrayList<>();
        try {
            for (String s : players) {
                if (s.equals(client.getUsername()))
                    this.playerboardControllers.add(myPlayerboardController);
                else {
                    PlayerboardController newPlayerboardController;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SceneController.class.getClassLoader().getResource("fxml/playerboard.fxml"));
                    Parent root1 = loader.load();
                    newPlayerboardController = loader.getController();
                    newPlayerboardController.setUsername(s);
                    this.playerboardControllers.add(newPlayerboardController); //the array is ordered to give the right amount of resouces to each player
                    newPlayerboardController.disableAll();
                    playerboardList.add(root1);
                }
            }
        }catch (IOException e) {
            e.printStackTrace(); //TODO gestire
        }

//        try {
//            for (int i = 1; i < this.playerboardControllers.length && i <= usernames.length; i++) {
//                if (usernames[i - 1] != null) {
//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(SceneController.class.getClassLoader().getResource("fxml/playerboard.fxml"));
//                    Parent root1 = loader.load();
//                    this.playerboardControllers[i] = loader.getController();
//                    this.playerboardControllers[i].setUsername(usernames[i - 1]);
//                    playerboardList.add(root1);
//                    //this.player1_anchorPane.getChildren().add(root1);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace(); //TODO gestire
//        }
    }

    public void leaderSetup(String username, Game.LeaderSetup stateUpdate) {
        PlayerboardController playerController = this.playerboardControllers.stream().filter(Objects::nonNull).filter(controller -> controller.getUsername().equals(username))
                .collect(Collectors.toList()).get(0);
        List<Integer> leadersID = stateUpdate.getLeaderList();
        playerController.leaderSetup(leadersID);
    }

    public void constructMarket(Market.MarketSetup stateUpdate) {
        MarbleColor[][] marketColorMatrix = stateUpdate.getMarbleMatrix();
        MarbleColor slideMarble = stateUpdate.getSlide();

        this.slide = new Circle(140, 10, 16, slideMarble.getGuiColor());
        market_anchorPane.getChildren().add(slide);

        this.marbleGrid = new Circle[N_ROW][N_COLUMN];
        for (int i = 0; i < marbleGrid.length; i++)
            for (int j = 0; j < marbleGrid[0].length; j++) {
                marbleGrid[i][j] = new Circle(20 + j * 40, 55 + i * 40, 16, marketColorMatrix[i][j].getGuiColor());
                market_anchorPane.getChildren().add(marbleGrid[i][j]);
            }
    }


    public void updateMarket(Market.MarketUpdate stateUpdate) {
        boolean isRow = stateUpdate.getIsRow();
        int index = stateUpdate.getIndex();

        if (isRow)
            pushRow(index);
        else
            pushColumn(index);
    }

    public void updateMarketOtherPlayers(Market.MarketUpdate stateUpdate) {
        boolean isRow = stateUpdate.getIsRow();
        int index = stateUpdate.getIndex();

        if (isRow)
            pushRowOthers(index);
        else
            pushColumnOthers(index);
    }



    private void askFotCardSlot(int row, int column) {
        this.slotSelectionHbox.setVisible(true);
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        this.endTurnButton.setDisable(true);
        this.selectedCardRow = row;
        this.selectedCardColumn = column;

        //TODO è così solo per testing, sta roba in realtà sta solo nell'phaseUpdate
        updateDeck(new DevelopCardDeck.DevelopCardDeckUpdate(row, column));
        //----------------
    }

    @FXML
    void selectedMarble(MouseEvent event) {
        int i = 0;
        for (Node n : tempMarbleHbox.getChildren()) {
            if (n.equals(event.getSource())) {
                System.out.println("selectedGreyMarble " + i);
                Action buyCardAction = new InsertMarbleAction(i);
                client.forwardAction(buyCardAction);
                break;
            }
            i++;
        }
        tempMarbleHbox.getChildren().remove(event.getSource());
        if (tempMarbleHbox.getChildren().isEmpty()) {
            this.endTurnButton.setDisable(false); //TODO verificare se serve davvero
        }
    }
//    @FXML
//    void selectedYellowMarble(MouseEvent event) {
//        tempMarbleHbox.getChildren().remove(event.getSource());
//        System.out.println("selectedYellowMarble");
//    }
//    @FXML
//    void selectedRedMarble(MouseEvent event) {
//        tempMarbleHbox.getChildren().remove(event.getSource());
//        System.out.println("selectedRedMarble");
//
//    }
//    @FXML
//    void selectedWhiteMarble(MouseEvent event) {
//        tempMarbleHbox.getChildren().remove(event.getSource());
//        System.out.println("selectedWhiteMarble");
//
//    }
//    @FXML
//    void selectedBlueMarble(MouseEvent event) {
//        tempMarbleHbox.getChildren().remove(event.getSource());
//        System.out.println("selectedBlueMarble");
//
//    }
//    @FXML
//    void selectedPurpleMarble(MouseEvent event) {
//        tempMarbleHbox.getChildren().remove(event.getSource());
//        System.out.println("selectedPurpleMarble");
//
//    }


    @FXML
    void buyCard1(MouseEvent event) {
        askFotCardSlot(0, 0);
    }

    @FXML
    void buyCard2(MouseEvent event) {
        askFotCardSlot(0, 1);
    }

    @FXML
    void buyCard3(MouseEvent event) {
        askFotCardSlot(0, 2);
    }

    @FXML
    void buyCard4(MouseEvent event) {
        askFotCardSlot(0, 3);
    }

    @FXML
    void buyCard5(MouseEvent event) {
        askFotCardSlot(1, 0);
    }

    @FXML
    void buyCard6(MouseEvent event) {
        askFotCardSlot(1, 1);
    }

    @FXML
    void buyCard7(MouseEvent event) {
        askFotCardSlot(1, 2);
    }

    @FXML
    void buyCard8(MouseEvent event) {
        askFotCardSlot(1, 3);
    }

    @FXML
    void buyCard9(MouseEvent event) {
        askFotCardSlot(2, 0);
    }

    @FXML
    void buyCard10(MouseEvent event) {
        askFotCardSlot(2, 1);
    }

    @FXML
    void buyCard11(MouseEvent event) {
        askFotCardSlot(2, 2);
    }

    @FXML
    void buyCard12(MouseEvent event) {
        askFotCardSlot(2, 3);
    }


    @FXML
        //TODO le push vanno tolte, servono solo per testare
    void pushColumn1(MouseEvent event) {
        onColumnPushed(0);
        //pushColumn(0);
    }

    @FXML
    void pushColumn2(MouseEvent event) {
        onColumnPushed(1);
        //pushColumn(1);
    }

    @FXML
    void pushColumn3(MouseEvent event) {
        onColumnPushed(2);
        //pushColumn(2);
    }

    @FXML
    void pushColumn4(MouseEvent event) {
        onColumnPushed(3);
        //pushColumn(3);
    }

    @FXML
    void pushRow1(MouseEvent event) {
        onRowPushed(0);
        //pushRow(0);
    }

    @FXML
    void pushRow2(MouseEvent event) {
        onRowPushed(1);
        //pushRow(1);
    }

    @FXML
    void pushRow3(MouseEvent event) {
        onRowPushed(2);
        //pushRow(2);
    }

    private Circle cloneCircle(Circle circle) {
        Circle clonedCircle = new Circle(circle.getRadius(), circle.getFill());
//        if (MarbleColor.GREY.getGuiColor().equals(clonedCircle.getFill())) {
//            clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedGreyMarble(event));
//        }
//        if(MarbleColor.YELLOW.getGuiColor().equals(clonedCircle.getFill())){
//            clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedYellowMarble(event));
//        }
//        if (MarbleColor.BLUE.getGuiColor().equals(clonedCircle.getFill())) {
//            clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedBlueMarble(event));
//        }
//        if(MarbleColor.WHITE.getGuiColor().equals(clonedCircle.getFill())){
//            clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedWhiteMarble(event));
//
//        }
//        if (MarbleColor.RED.getGuiColor().equals(clonedCircle.getFill())) {
//            clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedRedMarble(event));
//        }
//        if (MarbleColor.PURPLE.getGuiColor().equals(clonedCircle.getFill())) {
//            clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedPurpleMarble(event));
//        }
        clonedCircle.setOnMouseClicked((MouseEvent event) -> selectedMarble(event));
        return clonedCircle;
    }

    private void pushRow(int row) {
        Color toSlide = (Color) this.marbleGrid[row][0].getFill();
        tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[row][0]));
        for (int j = 1; j < 4; j++) {
            tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[row][j]));
            this.marbleGrid[row][j - 1].setFill(this.marbleGrid[row][j].getFill());
        }
        this.marbleGrid[row][3].setFill(this.slide.getFill());
        this.slide.setFill(toSlide);
    }

    private void pushColumn(int column) {
        Color temp = (Color) this.marbleGrid[0][column].getFill();
        tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[0][column]));
        for (int i = 1; i < 3; i++) {
            tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[i][column]));
            this.marbleGrid[i - 1][column].setFill(this.marbleGrid[i][column].getFill());
        }
        this.marbleGrid[2][column].setFill(this.slide.getFill());
        this.slide.setFill(temp);
    }



    private void pushRowOthers(int row) {
        Color toSlide = (Color) this.marbleGrid[row][0].getFill();
        for (int j = 1; j < 4; j++) {
            this.marbleGrid[row][j - 1].setFill(this.marbleGrid[row][j].getFill());
        }
        this.marbleGrid[row][3].setFill(this.slide.getFill());
        this.slide.setFill(toSlide);
    }

    private void pushColumnOthers(int column) {
        Color temp = (Color) this.marbleGrid[0][column].getFill();
        for (int i = 1; i < 3; i++) {
            this.marbleGrid[i - 1][column].setFill(this.marbleGrid[i][column].getFill());
        }
        this.marbleGrid[2][column].setFill(this.slide.getFill());
        this.slide.setFill(temp);
    }

    private void onRowPushed(int row) {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        Action marketAction = new ShopMarketAction(true, row);
        client.forwardAction(marketAction);

        //TODO poi sto codice va cancellato finito il testing
//        Color temp = (Color) this.marbleGrid[row][0].getFill();
//        for(int j=1; j<4; j++){
//            this.marbleGrid[row][j-1].setFill(this.marbleGrid[row][j].getFill());
//        }
//        this.marbleGrid[row][3].setFill(this.slide.getFill());
//        this.slide.setFill(temp);
    }

    private void onColumnPushed(int column) {
        Action marketAction = new ShopMarketAction(false, column);
        client.forwardAction(marketAction);
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        //TODO poi sto codice va cancellato finito il testing
//        Color temp = (Color) this.marbleGrid[0][column].getFill();
//        for(int i=1; i<3; i++){
//            this.marbleGrid[i-1][column].setFill(this.marbleGrid[i][column].getFill());
//        }
//        this.marbleGrid[2][column].setFill(this.slide.getFill());
//        this.slide.setFill(temp);
    }

    @FXML
    void endTurn(ActionEvent event) {
        Action endTurnAction = new EndTurnAction(client.getUsername());
        client.forwardAction(endTurnAction);
    }

    @FXML
    void showMyPlayerboard(ActionEvent event) {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(0));
    }

    @FXML
    void showPlayerboard1(ActionEvent event) {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(1));
    }

    @FXML
    void showPlayerboard2(ActionEvent event) {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(2));
    }

    @FXML
    void showPlayerboard3(ActionEvent event) {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(3));
    }


    @FXML
    void completeBuyWithSlot1(ActionEvent event) {
        createBuyCardAction(0);
//        CardSlots.CardSlotUpdate slotUpdate = new CardSlots.CardSlotUpdate(i, 0);
//        for(PlayerboardController p : playerboardControllers)
//            if(p != null)
//                p.updateCardSlot(slotUpdate);
//        i+=16;
    }

    @FXML
    void completeBuyWithSlot2(ActionEvent event) {
        createBuyCardAction(1);
    }

    @FXML
    void completeBuyWithSlot3(ActionEvent event) {
        createBuyCardAction(2);
    }

    private void createBuyCardAction(int slotNumber) {
        this.slotSelectionHbox.setVisible(false);
        this.market_anchorPane.setDisable(false);
        this.deckGridPane.setDisable(false);
        this.endTurnButton.setDisable(false);
        Action buyCardAction = new BuyDevelopCardAction(this.selectedCardRow, this.selectedCardColumn, slotNumber);
        client.forwardAction(buyCardAction);
    }

    public void askLeaderOnWHite(String username) {
        //TODO settare a non usabili tutti i comandi che non siano le leader (sia in playerboard che in gameboard)
        turnPhaseLable.setText("Choose a leader to convert the white marble");
        getPlayerBoardController(username).askLeaderOnWhite();
    }


    public void displayMarbleChoice(String username){
        this.turnPhaseLable.setText("It's " + username + "'s turn");
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        getPlayerBoardController(this.client.getUsername()).disableLeader();
        this.endTurnButton.setDisable(true);
        int i = 0;
        for (PlayerboardController p : playerboardControllers)
            if (!username.equals(p.getUsername()))
                i++;
            else
                break;
        if (i == 0){
            client.forwardAction(new ChooseInitialResourcesAction(new HashMap<>()));
        }
        else {
            this.numberOfInitRes = (i + 1) / 2;
            choseResourcesGridPane.setVisible(true);
            turnPhaseLable.setText("You need to choose " + this.numberOfInitRes + " resources to add");
            turnPhaseLable.setVisible(true);
        }
    }

    public PlayerboardController getPlayerBoardController(String username){
        for(PlayerboardController p : playerboardControllers)
            if(username.equals(p.getUsername()))
                return p;
        return null;
    }

    @FXML
    void chooseGold(MouseEvent event) { onChosenResource(ResourceType.GOLD); }

    @FXML
    void chooseServant(MouseEvent event) { onChosenResource(ResourceType.SERVANT); }

    @FXML
    void chooseShield(MouseEvent event) { onChosenResource(ResourceType.SHIELD); }

    @FXML
    void chooseStone(MouseEvent event) { onChosenResource(ResourceType.STONE); }

    private void onChosenResource(ResourceType resource){
        if(chosenResourceMap.containsKey(resource))
            chosenResourceMap.compute(resource, (k,v) -> (v==null) ? 1 : v + 1);
        else {
            chosenResourceMap.put(resource, 1);
        }
        if(chosenResourceMap.size() == this.numberOfInitRes){
            client.forwardAction(new ChooseInitialResourcesAction(chosenResourceMap));
            choseResourcesGridPane.setVisible(false);
            choseResourcesGridPane.setDisable(true);
        }
    }

    public void displayLeaderChoiceLable(String username) {
        this.turnPhaseLable.setText("It's " + username + "'s turn");
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        getPlayerBoardController(this.client.getUsername()).enableLeader();
        this.endTurnButton.setDisable(true);
        turnPhaseLable.setText("Choose 2 leadercards to DISCARD");
        turnPhaseLable.setVisible(true);

    }

    public void enableInitialAction() {
        this.turnPhaseLable.setText("It's your turn");
        this.market_anchorPane.setDisable(false);
        this.deckGridPane.setDisable(false);
        getPlayerBoardController(this.client.getUsername()).enableProduction();
        getPlayerBoardController(this.client.getUsername()).enableLeader();
        this.endTurnButton.setDisable(true);
    }

    public void otherPlayerTurn(String username) {
        this.turnPhaseLable.setText("It's " + username + "'s turn");
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        getPlayerBoardController(this.client.getUsername()).disableLeader();
        this.endTurnButton.setDisable(true);
    }

    public void enableFinalAction() {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        this.endTurnButton.setDisable(false);
    }

    public void enableProductionAction() {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).enableProduction();
        this.endTurnButton.setDisable(false);
    }

    public void enableShoppingAction() {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        this.endTurnButton.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableLeader();
    }

    // Cosmetics ----------------------------------------------------------------
    @FXML
    void mouseHover(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(1.05);
        node.setScaleY(1.05);
    }

    @FXML
    void mouseHoverReset(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(1.0);
        node.setScaleY(1.0);
    }


    @FXML
    void mouseHoverColorChange(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        image.setImage(new Image("images/MaterialArrowFilledHover.png"));
        image.setScaleX(1.1);
        image.setScaleY(1.1);
    }

    @FXML
    void mouseHoverColorChangeReset(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        image.setImage(new Image("images/MaterialArrowFilled.png"));
        image.setScaleX(1.0);
        image.setScaleY(1.0);
    }
}
