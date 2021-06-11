package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.cli.drawer.DevelopCardConstructor;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerboardController extends GUIController {

    private String username;
    private int i=2;
    private int j=0;
    private Map<ImageView, Integer> leaderImageIdMap = new HashMap<>();
    private ImageView selectedLeader;
    private ImageView selectedLeaderToDiscard;
    private ResourceType firstToConsume = null;
    private ResourceType secondToConsume = null;
    private Pair<ResourceType, Integer>[] storageLevels;
    private List<ImageView>[] guiStorage;
    private Map<ResourceType,String> resTypeToUrlMap = Map.of(ResourceType.GOLD, "/images/punchboard/coin.png",
                                                            ResourceType.STONE, "/images/punchboard/stone.png",
                                                            ResourceType.SERVANT, "/images/punchboard/servant.png",
                                                            ResourceType.SHIELD, "/images/punchboard/shield.png");

    @FXML
    private ImageView leader0_ImageView;
    @FXML
    private ImageView leader1_ImageView;
    @FXML
    private ImageView leader2_ImageView;
    @FXML
    private ImageView leader3_ImageView;
    @FXML
    private VBox leaderCardVbox;
    @FXML
    private ImageView popeCard1;
    @FXML
    private ImageView popeCard2;
    @FXML
    private ImageView popeCard3;
    @FXML
    private Label coinCounter;
    @FXML
    private Label servantCounter;
    @FXML
    private Label stoneCounter;
    @FXML
    private Label shieldCounter;
    @FXML
    private Label usernameLabel;
    @FXML
    private GridPane trackGrid;
    @FXML
    private ImageView trackPosition;
    @FXML
    private Button productionSlot1Button;
    @FXML
    private Button productionSlot2Button;
    @FXML
    private Button productionSlot3Button;
    @FXML
    private Button baseProductionButton;
    @FXML
    private ImageView cardCardSlot2;
    @FXML
    private ImageView cardCardSlot3;
    @FXML
    private ImageView cardCardSlot1;
    @FXML
    private Pane playerBoardPane;
    @FXML
    private Button activateLeaderButton;
    @FXML
    private Button discardLeaderButton;
    @FXML
    private VBox baseProdChoice;
    @FXML
    private HBox warehouseLevel0Hbox;
    @FXML
    private HBox warehouseLevel1Hbox;
    @FXML
    private HBox warehouseLevel2Hbox;







    @FXML
    private void initialize() {
        this.leader0_ImageView.setVisible(false);
        this.leader1_ImageView.setVisible(false);
        this.leader2_ImageView.setVisible(false);
        this.leader3_ImageView.setVisible(false);
        this.cardCardSlot1.setVisible(false);
        this.cardCardSlot2.setVisible(false);
        this.cardCardSlot3.setVisible(false);
        this.activateLeaderButton.setVisible(false);
        this.discardLeaderButton.setVisible(false);
        this.baseProdChoice.setVisible(false);

        //non so se ste cose sono valide qua
        this.storageLevels = new Pair[5];
        for(int i=0; i<5; i++)
            storageLevels[i] = new Pair(null, null);

        this.guiStorage = new List[5];
        for(int i=0; i<5; i++) {
            guiStorage[i] = new ArrayList<>();
//            ImageView image = new ImageView(new Image("/images/punchboard/shield.png"));
//            image.setVisible(false);
//            guiStorage[i].add(image);
        }
    }


    public void setUsername(String username) {
        this.username = username;
        this.usernameLabel.setText(this.username);
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
            leaderImageIdMap.put(this.leader0_ImageView, ledersID.get(0));


            // SECOND IMAGE
            url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(1)).getImage();
            this.leader1_ImageView.setImage(new Image(url));
            this.leader1_ImageView.setVisible(true);
            leaderImageIdMap.put(this.leader1_ImageView, ledersID.get(1));

            // THIRD IMAGE
            url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(2)).getImage();
            this.leader2_ImageView.setImage(new Image(url));
            this.leader2_ImageView.setVisible(true);
            leaderImageIdMap.put(this.leader2_ImageView, ledersID.get(2));

            // FOURTH IMAGE
            url = "images/front/" + LeaderConstructor.getLeaderCardFromId(ledersID.get(3)).getImage();
            this.leader3_ImageView.setImage(new Image(url));
            this.leader3_ImageView.setVisible(true);
            leaderImageIdMap.put(this.leader3_ImageView, ledersID.get(3));

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
//        this.trackPosition.setVisible(false);
//        ImageView image = new ImageView(new Image("images/punchboard/faithTrackCross.png"));
//        this.trackPosition = image;
//        image.setFitWidth(20);
//        image.setFitHeight(20);
//
//        if(j==18)
//            return;
//        if(((j==2 || j==12) && i>0)) {
//            i--;
//            trackGrid.add(image, j, i);
//            return;
//        }
//        if(j==7 && i<2) {
//            i++;
//            trackGrid.add(image, j, i);
//            return;
//        }
//        j++;
//        trackGrid.add(image, j, i);
    }

    public void updateVatican(Track.VaticanReport stateUpdate) {
        int zone = stateUpdate.getZone();
        if(stateUpdate.isActive())
            switch (zone) {
                case 1:
                    popeCard1 = new ImageView(new Image("images/punchboard/coin.png"));
                    break;
                case 2:
                    popeCard2 = new ImageView(new Image("images/punchboard/shield.png"));
                    break;
                case 3:
                    popeCard3 = new ImageView(new Image("images/punchboard/stone.png"));
                    break;
            }
    }

    @FXML
    void move(MouseEvent event) {
        System.out.println("moved");
        this.trackPosition.setVisible(false);
        ImageView image = new ImageView(new Image("images/punchboard/faithTrackCross.png"));
        this.trackPosition = image;
        image.setFitWidth(32);
        image.setFitHeight(31);

        if(j==18)
            return;
        if(((j==2 || j==12) && i>0)) {
            i--;
            trackGrid.add(image, j, i);
            return;
        }
        if(j==7 && i<2) {
            i++;
            trackGrid.add(image, j, i);
            return;
        }
        j++;
        trackGrid.add(image, j, i);
    }

    public void updateCardSlot(CardSlots.CardSlotUpdate stateUpdate) {
        int slot = stateUpdate.getSlotNumber();
        int cardId = stateUpdate.getDevCardID();
        int leaderLevel = 0;
        DevelopCard devCard = null;
        ImageView newCard;
        double height = cardCardSlot1.getFitHeight();
        double width = cardCardSlot1.getFitWidth();

        double cardCardSlotY = cardCardSlot1.getLayoutY();

        try {
            devCard = DevelopCardConstructor.getDevelopCardFromId(cardId);
            leaderLevel = devCard.getCardFlag().getLevel()-1;
        } catch (InvalidCardException e) {}


        newCard = new ImageView(new Image("/images/front/" + devCard.getImage()));
        ImageView clone = cloneImageView(newCard);

        switch (slot) {
            case 0:
                double firstCardCardSlotX = cardCardSlot1.getLayoutX();
                clone.setLayoutX(firstCardCardSlotX);
                break;
            case 1:
                double secondCardCardSlotX = cardCardSlot2.getLayoutX();
                clone.setLayoutX(secondCardCardSlotX);
                break;
            case 2:
                double thirdCardCardSlotX = cardCardSlot3.getLayoutX();
                clone.setLayoutX(thirdCardCardSlotX);
                break;
        }

        clone.setLayoutY(cardCardSlotY - (leaderLevel * 40));
        clone.setFitHeight(height);
        clone.setFitWidth(width);
        clone.setOnMouseEntered((MouseEvent event) -> mouseHover(event));
        clone.setOnMouseExited((MouseEvent event) -> mouseHoverReset(event));

        playerBoardPane.getChildren().add(clone);

    }

    @FXML
    void baseProduction(ActionEvent event) {
        //TODO finestra che fa comparire la scelta di risorse
        this.baseProdChoice.setVisible(true);
        //Action productionAction = new BaseProductionAction();
        //client.forwardAction(productionAction);
    }
    @FXML
    void produceSlot1(ActionEvent event) {
        createProductionAction(0);
    }
    @FXML
    void produceSlot2(ActionEvent event) {
        createProductionAction(1);
    }
    @FXML
    void produceSlot3(ActionEvent event) {
        createProductionAction(2);
    }

    private void createProductionAction(int cardIndex){
        Action productionAction = new ProductionAction(cardIndex);
        client.forwardAction(productionAction);
    }

    private ImageView cloneImageView(ImageView imageView){
        return new ImageView(imageView.getImage());
    }

    public void askLeaderOnWhite(){
        this.enableLeader();
        leader0_ImageView.setOnMouseClicked((MouseEvent event) -> selectWhiteLeader(event));
        leader1_ImageView.setOnMouseClicked((MouseEvent event) -> selectWhiteLeader(event));
        leader2_ImageView.setOnMouseClicked((MouseEvent event) -> selectWhiteLeader(event));
        leader3_ImageView.setOnMouseClicked((MouseEvent event) -> selectWhiteLeader(event));
    }

    @FXML
    void selectWhiteLeader(MouseEvent event) {
        Action chooseLeaderOnWhiteMarbleAction = new ChooseLeaderOnWhiteMarbleAction(leaderImageIdMap.get((ImageView) event.getSource()));
        client.forwardAction(chooseLeaderOnWhiteMarbleAction);
        //TODO rimetto alle leader il loro metodo standard
        leader0_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
        leader1_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
        leader2_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
        leader3_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
        this.disableLeader();
    }


    @FXML
    void showDiscardActivateMenu(MouseEvent event) {
        this.selectedLeader = (ImageView) event.getSource();
        this.activateLeaderButton.setVisible(true);
        this.discardLeaderButton.setVisible(true);
    }
    @FXML
    void activateLeader(ActionEvent event) {
        Action activateLeaderAction = new ActivateLeaderAction(leaderImageIdMap.get(this.selectedLeader));
        System.out.println(leaderImageIdMap.get(this.selectedLeader));
        client.forwardAction(activateLeaderAction);
        this.activateLeaderButton.setVisible(false);
        this.discardLeaderButton.setVisible(false);
    }
    @FXML
    void discardLeader(ActionEvent event) {
        Action discardLeaderAction = new DiscardLeaderAction(leaderImageIdMap.get(this.selectedLeader));
        System.out.println(leaderImageIdMap.get(this.selectedLeader));
        client.forwardAction(discardLeaderAction);
        this.activateLeaderButton.setVisible(false);
        this.discardLeaderButton.setVisible(false);
    }

    @FXML
    void chooseGold(MouseEvent event) {
        createBaseProduction(ResourceType.GOLD);
//        if(firstToConsume == null)
//            firstToConsume = ResourceType.GOLD;
//        else if(secondToConsume == null)
//            secondToConsume = ResourceType.GOLD;
//        else {
//            Action baseProduction = new BaseProductionAction(firstToConsume, secondToConsume, ResourceType.GOLD);
//            client.forwardAction(baseProduction);
//        }
    }

    @FXML
    void chooseServant(MouseEvent event) {
        createBaseProduction(ResourceType.SERVANT);
    }

    @FXML
    void chooseShield(MouseEvent event) {
        createBaseProduction(ResourceType.SHIELD);
    }

    @FXML
    void chooseStone(MouseEvent event) {
        createBaseProduction(ResourceType.STONE);
    }

    private void createBaseProduction(ResourceType r) {
        System.out.println(r);
        if(firstToConsume == null)
            firstToConsume = r;
        else if(secondToConsume == null)
            secondToConsume = r;
        else {
            Action baseProduction = new BaseProductionAction(firstToConsume, secondToConsume, r);
            client.forwardAction(baseProduction);
            this.baseProdChoice.setVisible(false);
            firstToConsume = null;
            secondToConsume = null;
        }
    }

    @FXML
    void addStone(MouseEvent event) {
        updateWarehouse(new Warehouse.WarehouseUpdate(ResourceType.STONE, 2, 1));
    }

    @FXML
    void addShield(MouseEvent event) {
        updateWarehouse(new Warehouse.WarehouseUpdate(ResourceType.SHIELD, 2, 0));
        updateWarehouse(new Warehouse.WarehouseUpdate(ResourceType.STONE, 3, 2));
    }


    public void updateWarehouse(Warehouse.WarehouseUpdate update){
        ResourceType resource = update.getResourceType();
            //res, quantity, level
        //normalLevels
        warehouseLevel0Hbox.getChildren().clear();
        warehouseLevel1Hbox.getChildren().clear();
        warehouseLevel2Hbox.getChildren().clear();


        if(update.getLevel()<3) {
            //controllo se la risorsa é presente
            for (int i = 0; i < 3; i++) {

                if (storageLevels[i].getKey() != null) {
                    if (storageLevels[i].getKey().equals(resource)) {
                        System.out.println("ciao");
                        if (i == update.getLevel()) {
                            System.out.println("ciao2");
                            storageLevels[i] = new Pair<>(resource, update.getQuantity());
                            guiStorage[i].clear();
                            for(int k=0; k<update.getQuantity(); k++){
                                guiStorage[i].add(new ImageView(new Image(resTypeToUrlMap.get(storageLevels[i].getKey()))));
                            }
                            updateWarehouseVisuals();
                            return;
                        } else {
                            guiStorage[update.getLevel()].clear();
                            guiStorage[i].clear();
                            Pair<ResourceType, Integer> temp = new Pair<>(storageLevels[update.getLevel()].getKey(), storageLevels[update.getLevel()].getValue());
                            storageLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                            storageLevels[i] = new Pair<>(temp.getKey(), temp.getValue());

                            for(int k=0; k<update.getQuantity(); k++){
                                guiStorage[update.getLevel()].add(new ImageView(new Image(resTypeToUrlMap.get(storageLevels[update.getLevel()].getKey()))));
                            }

                            for(int k=0; temp.getValue()!= null && k<temp.getValue(); k++){
                                guiStorage[i].add(new ImageView(new Image(resTypeToUrlMap.get(storageLevels[i].getKey()))));
                            }
                            updateWarehouseVisuals();
                            return;
                        }
                    }
                }
            }
        }
        //se non presente creo
        storageLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());

        guiStorage[update.getLevel()] =  new ArrayList<>();
        for(int k=0; k<update.getQuantity(); k++){
            guiStorage[update.getLevel()].add(new ImageView(new Image(resTypeToUrlMap.get(storageLevels[update.getLevel()].getKey()))));
        }

        updateWarehouseVisuals();
    }

    private void updateWarehouseVisuals(){
        for(ImageView i : guiStorage[0]){
            i.setFitHeight(30);
            i.setFitWidth(30);
            warehouseLevel2Hbox.getChildren().add(i);
        }
        for(ImageView i : guiStorage[1]){
            i.setFitHeight(30);
            i.setFitWidth(30);
            warehouseLevel1Hbox.getChildren().add(i);
        }
        for(ImageView i : guiStorage[2]){
            i.setFitHeight(30);
            i.setFitWidth(30);
            warehouseLevel0Hbox.getChildren().add(i);
        }
    }

    @FXML
    void initialDiscard(MouseEvent event) {
        if(selectedLeaderToDiscard == null) {
            this.selectedLeaderToDiscard = (ImageView) event.getSource();
            this.selectedLeaderToDiscard.setVisible(false);
            this.selectedLeaderToDiscard.setDisable(true);
        }
        else {
            Action discardLeaderAction = new DiscardInitialLeaderAction(leaderImageIdMap.get((ImageView) event.getSource()), leaderImageIdMap.get(this.selectedLeaderToDiscard));
            leaderCardVbox.getChildren().remove((ImageView) event.getSource());
            leaderCardVbox.getChildren().remove(this.selectedLeaderToDiscard);
            client.forwardAction(discardLeaderAction);

            this.selectedLeaderToDiscard = null;
//            List<ImageView> cardList = new ArrayList<>(List.of(leader0_ImageView, leader1_ImageView, leader2_ImageView, leader3_ImageView));
//            //List<ImageView> cardListClone = List.of(leader0_ImageView, leader1_ImageView, leader2_ImageView, leader3_ImageView);
//            for(ImageView i : cardList){ //FIXME questa parte non va
//                if(i.equals(this.selectedLeaderToDiscard) || i.equals((ImageView) event.getSource()))
//                    cardList.remove(i);
//            }
//            leader0_ImageView = cardList.get(0);
//            leader1_ImageView = cardList.get(1);

            leader0_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
            leader1_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
            leader2_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
            leader3_ImageView.setOnMouseClicked((MouseEvent event1) -> showDiscardActivateMenu(event1));
        }
    }

    public void enableProduction() {
        this.baseProductionButton.setDisable(false);
        this.productionSlot1Button.setDisable(false);
        this.productionSlot2Button.setDisable(false);
        this.productionSlot3Button.setDisable(false);
    }

    public void disableProduction() {
        this.baseProductionButton.setDisable(true);
        this.productionSlot1Button.setDisable(true);
        this.productionSlot2Button.setDisable(true);
        this.productionSlot3Button.setDisable(true);
    }

    public void disableLeader() {
        this.leaderCardVbox.setDisable(true);
    }

    public void enableLeader() {
        this.leaderCardVbox.setDisable(false);
    }

    public void disableAll() {
        this.disableLeader();
        this.disableProduction();
        this.baseProductionButton.setVisible(false);
        this.productionSlot1Button.setVisible(false);
        this.productionSlot2Button.setVisible(false);
        this.productionSlot3Button.setVisible(false);
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
}
