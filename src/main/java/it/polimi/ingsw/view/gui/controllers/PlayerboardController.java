package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.CardSlots;
import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.ResourceType;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerboardController extends GUIController {

    private int i=2;
    private int j=0;
    private Map<ImageView, Integer> leaderImageIdMap = new HashMap<>();
    private ImageView selectedLeader;
    private ResourceType firstToConsume = null;
    private ResourceType secondToConsume = null;

    @FXML
    private ImageView leader0_ImageView;
    @FXML
    private ImageView leader1_ImageView;
    @FXML
    private ImageView leader2_ImageView;
    @FXML
    private ImageView leader3_ImageView;
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
    }

    private String username;

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
        image.setFitWidth(20);
        image.setFitHeight(20);

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
        leader0_ImageView.setOnMouseClicked((MouseEvent event) -> selectWhiteLeader0(event));
        leader1_ImageView.setOnMouseClicked((MouseEvent event) -> selectWhiteLeader1(event));
    }

    @FXML
    void selectWhiteLeader0(MouseEvent event) {
        Action chooseLeaderOnWhiteMarbleAction = new ChooseLeaderOnWhiteMarbleAction(leaderImageIdMap.get(leader0_ImageView));
        client.forwardAction(chooseLeaderOnWhiteMarbleAction);
        //TODO rimetto alle leader il loro metodo standard
        leader0_ImageView.setOnMouseClicked((MouseEvent event1) -> selectWhiteLeader0(event1));
        leader1_ImageView.setOnMouseClicked((MouseEvent event1) -> selectWhiteLeader1(event1));
    }
    @FXML
    void selectWhiteLeader1(MouseEvent event) {
        Action chooseLeaderOnWhiteMarbleAction = new ChooseLeaderOnWhiteMarbleAction(leaderImageIdMap.get(leader1_ImageView));
        client.forwardAction(chooseLeaderOnWhiteMarbleAction);
        //TODO rimetto alle leader il loro metodo standard
        leader0_ImageView.setOnMouseClicked((MouseEvent event1) -> selectWhiteLeader0(event1));
        leader1_ImageView.setOnMouseClicked((MouseEvent event1) -> selectWhiteLeader1(event1));
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
        }
    }


    // Cosmetics ----------------------------------------------------------------
    @FXML
    void mouseHover(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setScaleX(1.05);
        button.setScaleY(1.05);
    }

    @FXML
    void mouseHoverReset(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setScaleX(1.0);
        button.setScaleY(1.0);
    }
}
