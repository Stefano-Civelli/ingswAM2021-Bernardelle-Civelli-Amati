package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.view.cli.drawer.DevelopCardConstructor;
import it.polimi.ingsw.view.gui.GuiResources;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Java controller of the gameboard.fxml file
 */
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
    private Label errorLable;
    @FXML
    private GridPane choseResourcesGridPane;
    @FXML
    private ImageView lorenzoToken;

    private Circle slide;
    private Circle[][] marbleGrid;
    private List<Integer>[][] developCardDeck;
    private final Rectangle[][] imagesDevelopCardDeck = new Rectangle[N_ROW][N_COLUMN];
    private final List<Parent> playerboardList = new ArrayList<>();
    private final List<Button> playerboardButtons = new ArrayList<>();
    private int selectedCardRow;
    private int selectedCardColumn;
    private int numberOfInitRes = 0;
    private final Map<ResourceType, Integer> chosenResourceMap = new HashMap<>();

    private List<PlayerboardController> playerboardControllers = new ArrayList<>();

    @FXML
    private void initialize() {
        playerboardButtons.add(myPlayerboardButton);
        playerboardButtons.add(playerboard1Button);
        playerboardButtons.add(playerboard2Button);
        playerboardButtons.add(playerboard3Button);
        myPlayerboardButton.setVisible(false);
        playerboard1Button.setVisible(false);
        playerboard2Button.setVisible(false);
        playerboard3Button.setVisible(false);
        slotSelectionHbox.setVisible(false);
        endTurnButton.setDisable(true);
        turnPhaseLable.setVisible(false);
        choseResourcesGridPane.setVisible(false);
        errorLable.setVisible(false);
        lorenzoToken.setVisible(false);
    }

    /**
     * Construct the gui's develop card deck
     * @param stateSetup the state of the develop card deck
     */
    public void constructDeck(DevelopCardDeckSetup stateSetup) {
        this.developCardDeck = stateSetup.getDevDeck();
        String url;
        for (int i = 0; i < developCardDeck.length; i++) {
            for (int j = 0; j < developCardDeck[i].length; j++) {
                try {
                    url = "images/front/" + DevelopCardConstructor.getDevelopCardFromId(developCardDeck[i][j].get(developCardDeck[i][j].size() - 1)).getImage();
                    imagesDevelopCardDeck[i][j] = createRectangleFromImageUrl(url, GuiResources.cardWidth, GuiResources.cardHeight);
                    imagesDevelopCardDeck[i][j].setOnMouseEntered(this::mouseHover);
                    imagesDevelopCardDeck[i][j].setOnMouseExited(this::mouseHoverReset);
                    assignMethodToCard(imagesDevelopCardDeck[i][j], i, j);
                    deckGridPane.add(imagesDevelopCardDeck[i][j], j, i);
                } catch (InvalidCardException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Rectangle createRectangleFromImageUrl(String url, double width, double height){
        Rectangle rectangle = new Rectangle(0, 0, GuiResources.cardWidth, GuiResources.cardHeight);
        rectangle.setArcWidth(30.0);   // Corner radius
        rectangle.setArcHeight(30.0);
        ImagePattern pattern = new ImagePattern(
                new Image(url, GuiResources.cardWidth, GuiResources.cardHeight, false, false) // Resizing
        );
        rectangle.setFill(pattern);
        return rectangle;
    }

    private void assignMethodToCard(Rectangle image, int i, int j) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        image.setOnMouseClicked(this::buyCard1);
                        break;
                    case 1:
                        image.setOnMouseClicked(this::buyCard2);
                        break;
                    case 2:
                        image.setOnMouseClicked(this::buyCard3);
                        break;
                    case 3:
                        image.setOnMouseClicked(this::buyCard4);
                        break;
                }
                break;
            case 1:
                switch (j) {
                    case 0:
                        image.setOnMouseClicked(this::buyCard5);
                        break;
                    case 1:
                        image.setOnMouseClicked(this::buyCard6);
                        break;
                    case 2:
                        image.setOnMouseClicked(this::buyCard7);
                        break;
                    case 3:
                        image.setOnMouseClicked(this::buyCard8);
                        break;
                }
                break;
            case 2:
                switch (j) {
                    case 0:
                        image.setOnMouseClicked(this::buyCard9);
                        break;
                    case 1:
                        image.setOnMouseClicked(this::buyCard10);
                        break;
                    case 2:
                        image.setOnMouseClicked(this::buyCard11);
                        break;
                    case 3:
                        image.setOnMouseClicked(this::buyCard12);
                        break;
                }
                break;

        }


    }

    /**
     * Display a deck update: a player bought a card
     * @param stateUpdate the update object of the develop card deck
     */
    public void updateDeck(DevelopCardDeckUpdate stateUpdate) {
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
            imagesDevelopCardDeck[row][column].setFill(new ImagePattern(new Image(url, GuiResources.cardWidth, GuiResources.cardHeight, false, false)));
        } else {
            imagesDevelopCardDeck[row][column].setVisible(false);
        }
    }

    /**
     * Set player username and create their personal player board loading the correct fxml file and saving its controller
     * @param username player's username
     */
    public void setUsername(String username) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(GuiResources.playerboardFXML));
            if(loader.getLocation() == null)
                throw new IOException();
            root = loader.load();
            PlayerboardController newPlayerboardController = loader.getController();
            newPlayerboardController.setUsername(username);
            newPlayerboardController.setClient(this.client);
            newPlayerboardController.thisClientPlayer();
            this.playerboardControllers.add(newPlayerboardController);
            this.player_anchorPane.getChildren().add(root);
            playerboardList.add(root);
        } catch (IOException e) {
            e.printStackTrace();
            this.client.getView().displayFatalError("Fatal Error: loading fxml file failed");
        }
    }

    /**
     * Create the player boards of the other players loading the fxml files and saving their controller
     * @param players other player usernames list
     */
    public void setOtherPlayer(List<String> players) {

        for (int i = 0; i < players.size(); i++) {
            playerboardButtons.get(i).setVisible(true);
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
                    loader.setLocation(SceneController.class.getClassLoader().getResource(GuiResources.playerboardFXML));
                    if(loader.getLocation() == null)
                        throw new IOException();
                    Parent root1 = loader.load();
                    newPlayerboardController = loader.getController();
                    newPlayerboardController.setUsername(s);
                    newPlayerboardController.clearLeaderVbox();
                    this.playerboardControllers.add(newPlayerboardController); //the array is ordered to give the right amount of resouces to each player
                    newPlayerboardController.disableAll();
                    playerboardList.add(root1);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            this.client.getView().displayFatalError("Fatal Error: loading fxml file failed");
        }
    }



    /**
     * Set the last token and the black cross visible
     */
    public void setupLorenzo(){
        lorenzoToken.setVisible(true);
        getPlayerBoardController(client.getUsername()).setupLorenzo();
    }

    /**
     * Set the last lorenzo token image
     * @param image the image of the last token
     */
    public void setLorenzoToken(Image image){
        this.lorenzoToken.setImage(image);
    }

    /**
     *
     * @param username username of the user of which return the controller
     * @return the player board controller of the specified user
     */
    public PlayerboardController getPlayerBoardController(String username){
        for(PlayerboardController p : playerboardControllers)
            if(username.equals(p.getUsername()))
                return p;
        return null;
    }

    /**
     * Display leader cards during setup
     * @param username username of the player
     * @param stateUpdate  the leader update containing the leader cards to display
     */
    public void leaderSetup(String username, LeaderSetup stateUpdate) {
        PlayerboardController playerController = this.playerboardControllers.stream().filter(Objects::nonNull).filter(controller -> controller.getUsername().equals(username))
                .collect(Collectors.toList()).get(0);
        List<Integer> leadersID = stateUpdate.getLeaderList();
        playerController.leaderSetup(leadersID);
    }

    /**
     * Construct the GUI's market
     * @param stateUpdate the model update representing the initial market
     */
    public void constructMarket(MarketSetup stateUpdate) {
        MarbleColor[][] marketColorMatrix = stateUpdate.getMarbleMatrix();
        MarbleColor slideMarble = stateUpdate.getSlide();

        this.slide = new Circle(140, 10, GuiResources.marbleRadius, slideMarble.getGuiColor());
        market_anchorPane.getChildren().add(slide);

        this.marbleGrid = new Circle[N_ROW][N_COLUMN];
        for (int i = 0; i < marbleGrid.length; i++)
            for (int j = 0; j < marbleGrid[0].length; j++) {
                marbleGrid[i][j] = new Circle(20 + j * 40, 55 + i * 40, GuiResources.marbleRadius, marketColorMatrix[i][j].getGuiColor());
                market_anchorPane.getChildren().add(marbleGrid[i][j]);
            }
    }

    /**
     * Display a market update when you are the player who bought from the market and show the tmp marble to insert them into warehouse
     * @param stateUpdate the model update representing the market update
     */
    public void updateMarket(MarketUpdate stateUpdate) {
        boolean isRow = stateUpdate.getIsRow();
        int index = stateUpdate.getIndex();

        if (isRow)
            pushRow(index);
        else
            pushColumn(index);
    }

    /**
     * Display a market update when another player bought a row or a column
     * @param stateUpdate the model update representing the market update
     */
    public void updateMarketOtherPlayers(MarketUpdate stateUpdate) {
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
    }

    @FXML
    private void selectedMarble(MouseEvent event) {
        int i = 0;
        for (Node n : tempMarbleHbox.getChildren()) {
            if (n.equals(event.getSource())) {
                Action buyCardAction = new InsertMarbleAction(i);
                client.forwardAction(buyCardAction);
                break;
            }
            i++;
        }
        Node source = (Node) event.getSource();
        tempMarbleHbox.getChildren().remove(source);
    }

    @FXML
    private void buyCard1(MouseEvent event) {
        askFotCardSlot(0, 0);
    }

    @FXML
    private void buyCard2(MouseEvent event) {
        askFotCardSlot(0, 1);
    }

    @FXML
    private void buyCard3(MouseEvent event) {
        askFotCardSlot(0, 2);
    }

    @FXML
    private void buyCard4(MouseEvent event) {
        askFotCardSlot(0, 3);
    }

    @FXML
    private void buyCard5(MouseEvent event) {
        askFotCardSlot(1, 0);
    }

    @FXML
    private void buyCard6(MouseEvent event) {
        askFotCardSlot(1, 1);
    }

    @FXML
    private void buyCard7(MouseEvent event) {
        askFotCardSlot(1, 2);
    }

    @FXML
    private void buyCard8(MouseEvent event) {
        askFotCardSlot(1, 3);
    }

    @FXML
    private void buyCard9(MouseEvent event) {
        askFotCardSlot(2, 0);
    }

    @FXML
    private void buyCard10(MouseEvent event) {
        askFotCardSlot(2, 1);
    }

    @FXML
    private void buyCard11(MouseEvent event) {
        askFotCardSlot(2, 2);
    }

    @FXML
    private void buyCard12(MouseEvent event) {
        askFotCardSlot(2, 3);
    }


    @FXML
    private void pushColumn1() { onColumnPushed(0); }

    @FXML
    private void pushColumn2() { onColumnPushed(1); }

    @FXML
    private void pushColumn3() { onColumnPushed(2); }

    @FXML
    private void pushColumn4() { onColumnPushed(3); }

    @FXML
    private void pushRow1() { onRowPushed(0); }

    @FXML
    private void pushRow2() { onRowPushed(1); }

    @FXML
    private void pushRow3() { onRowPushed(2); }


    private Circle cloneCircle(Circle circle) {
        Circle clonedCircle = new Circle(circle.getRadius(), circle.getFill());
        clonedCircle.setOnMouseClicked(this::selectedMarble);
        return clonedCircle;
    }

    private void pushRow(int row) {
        Color toSlide = (Color) this.marbleGrid[row][0].getFill();
        tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[row][0]));
        for (int j = 1; j < N_COLUMN; j++) {
            tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[row][j]));
            this.marbleGrid[row][j - 1].setFill(this.marbleGrid[row][j].getFill());
        }
        this.marbleGrid[row][3].setFill(this.slide.getFill());
        this.slide.setFill(toSlide);
    }

    private void pushColumn(int column) {
        Color temp = (Color) this.marbleGrid[0][column].getFill();
        tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[0][column]));
        for (int i = 1; i < N_ROW; i++) {
            tempMarbleHbox.getChildren().add(cloneCircle(this.marbleGrid[i][column]));
            this.marbleGrid[i - 1][column].setFill(this.marbleGrid[i][column].getFill());
        }
        this.marbleGrid[2][column].setFill(this.slide.getFill());
        this.slide.setFill(temp);
    }



    private void pushRowOthers(int row) {
        Color toSlide = (Color) this.marbleGrid[row][0].getFill();
        for (int j = 1; j < N_COLUMN; j++) {
            this.marbleGrid[row][j - 1].setFill(this.marbleGrid[row][j].getFill());
        }
        this.marbleGrid[row][3].setFill(this.slide.getFill());
        this.slide.setFill(toSlide);
    }

    private void pushColumnOthers(int column) {
        Color temp = (Color) this.marbleGrid[0][column].getFill();
        for (int i = 1; i < N_ROW; i++) {
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
    }

    private void onColumnPushed(int column) {
        Action marketAction = new ShopMarketAction(false, column);
        client.forwardAction(marketAction);
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
    }

    @FXML
    private void endTurn() {
        Action endTurnAction = new EndTurnAction(client.getUsername());
        client.forwardAction(endTurnAction);
    }

    @FXML
    private void showMyPlayerboard() {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(0));
    }

    @FXML
    private void showPlayerboard1() {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(1));
    }

    @FXML
    private void showPlayerboard2() {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(2));
    }

    @FXML
    private void showPlayerboard3() {
        player_anchorPane.getChildren().remove(0);
        player_anchorPane.getChildren().add(playerboardList.get(3));
    }


    @FXML
    private void completeBuyWithSlot1() {
        createBuyCardAction(0);
    }

    @FXML
    private void completeBuyWithSlot2() {
        createBuyCardAction(1);
    }

    @FXML
    private void completeBuyWithSlot3() {
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

    /**
     * Ask the player which leader card must be used to convert a white marble
     * @param username username of this user
     */
    public void askLeaderOnWHite(String username) {
        this.tempMarbleHbox.setDisable(true);
        turnPhaseLable.setText("Choose a leader to convert the white marble");
        getPlayerBoardController(username).askLeaderOnWhite();
    }

    /**
     * Display the tmp marbles bought form market
     * @param username the player who bought from market
     */
    public void displayInitialResourcesChoice(String username){
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


    @FXML
    private void chooseGold() { onChosenResource(ResourceType.GOLD); }

    @FXML
    private void chooseServant() { onChosenResource(ResourceType.SERVANT); }

    @FXML
    private void chooseShield() { onChosenResource(ResourceType.SHIELD); }

    @FXML
    private void chooseStone() { onChosenResource(ResourceType.STONE); }

    private void onChosenResource(ResourceType resource) {
        if(chosenResourceMap.containsKey(resource))
            chosenResourceMap.compute(resource, (k,v) -> (v==null) ? 1 : v + 1);
        else {
            chosenResourceMap.put(resource, 1);
        }
        int numberOfResourcesInMap = chosenResourceMap.values().stream().reduce(0, Integer::sum);
        if(numberOfResourcesInMap == this.numberOfInitRes){
            client.forwardAction(new ChooseInitialResourcesAction(chosenResourceMap));
            choseResourcesGridPane.setVisible(false);
            choseResourcesGridPane.setDisable(true);
        }
    }

    /**
     * Ask the player to discard two leader card during setup
     * @param username the player username
     */
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

    /**
     * Set disabled all the correct component for the initial phase
     */
    public void enableInitialAction() {
        this.turnPhaseLable.setText("It's your turn");
        this.market_anchorPane.setDisable(false);
        this.deckGridPane.setDisable(false);
        getPlayerBoardController(this.client.getUsername()).enableProduction();
        getPlayerBoardController(this.client.getUsername()).enableLeader();
        this.endTurnButton.setDisable(true);
    }

    /**
     * Set disabled all the correct component during an other player turn
     */
    public void otherPlayerTurn(String username) {
        this.turnPhaseLable.setText("It's " + username + "'s turn");
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        getPlayerBoardController(this.client.getUsername()).disableLeader();
        this.endTurnButton.setDisable(true);
    }

    /**
     * Set disabled all the correct component for the final phase
     */
    public void enableFinalAction() {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        this.endTurnButton.setDisable(false);
    }

    /**
     * Set disabled all the correct component for the producing phase
     */
    public void enableProductionAction() {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).enableProduction();
        this.endTurnButton.setDisable(false);
    }

    /**
     * Set disabled all the correct component for the shopping phase
     */
    public void enableShoppingAction() {
        this.market_anchorPane.setDisable(true);
        this.deckGridPane.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableProduction();
        this.endTurnButton.setDisable(true);
        getPlayerBoardController(this.client.getUsername()).disableLeader();
    }

    /**
     * Display and error
     * @param lableText error message
     */
    public void setErrorLable(String lableText) {
        errorLable.setText("ERROR: " + lableText);
        errorLable.setVisible(true);

        PauseTransition visiblePause = new PauseTransition( Duration.seconds(4) );
        visiblePause.setOnFinished( event -> errorLable.setVisible(false) );
        visiblePause.play();
    }

    /**
     *
     */
    public void setTempMarbleVisible(){
        this.tempMarbleHbox.setDisable(false);
    }


    // Cosmetics ----------------------------------------------------------------
    @FXML
    private void mouseHover(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(1.05);
        node.setScaleY(1.05);
        node.setEffect(new DropShadow(10, Color.WHITE));
    }

    @FXML
    private void mouseHoverReset(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(1.0);
        node.setScaleY(1.0);
        node.setEffect(new DropShadow(0, Color.WHITE));
    }


    @FXML
    private void mouseHoverColorChange(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        image.setImage(GuiResources.redMarketArrow);
        image.setScaleX(1.1);
        image.setScaleY(1.1);
        image.setEffect(new DropShadow(10, Color.WHITE));
    }

    @FXML
    private void mouseHoverColorChangeReset(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        image.setImage(GuiResources.marketArrow);
        image.setScaleX(1.0);
        image.setScaleY(1.0);
        image.setEffect(new DropShadow(0, Color.WHITE));
    }

}
