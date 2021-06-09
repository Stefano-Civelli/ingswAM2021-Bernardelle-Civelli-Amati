package it.polimi.ingsw.view.gui.controllers;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.BaseProductionAction;
import it.polimi.ingsw.controller.action.InsertMarbleAction;
import it.polimi.ingsw.controller.action.ProductionAction;
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

import java.lang.reflect.Type;
import java.util.List;

public class PlayerboardController extends GUIController {

    private int i=2;
    private int j=0;

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
    private void initialize() {
        this.leader0_ImageView.setVisible(false);
        this.leader1_ImageView.setVisible(false);
        this.leader2_ImageView.setVisible(false);
        this.leader3_ImageView.setVisible(false);
        this.cardCardSlot1.setVisible(false);
        this.cardCardSlot2.setVisible(false);
        this.cardCardSlot3.setVisible(false);
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

        try {
            devCard = DevelopCardConstructor.getDevelopCardFromId(cardId);
            leaderLevel = devCard.getCardFlag().getLevel()-1;
        } catch (InvalidCardException e) {}

        if(leaderLevel == 0) {
            switch (slot) {
                case 0:
                    cardCardSlot1 = new ImageView(new Image("/images/front/" + devCard.getImage()));
                    System.out.println("talla");
                    this.cardCardSlot1.setVisible(true);
                    break;
                case 1:
                    cardCardSlot2 = new ImageView(new Image("/images/front/" + devCard.getImage()));
                    break;
                case 2:
                    cardCardSlot3 = new ImageView(new Image("/images/front/" + devCard.getImage()));
                    break;
            }
        }
        else {
            ImageView newCard;
            switch (slot) {
                case 0:
                    double firstCardCardSlotX = cardCardSlot1.getX();
                    double firstCardCardSlotY = cardCardSlot1.getY();
                    newCard = new ImageView(new Image("/images/front/" + devCard.getImage()));
                    newCard.setX(firstCardCardSlotX);
                    newCard.setY(firstCardCardSlotY - (leaderLevel * 40));

                    System.out.println("rita");
                    break;
                case 1:
                    newCard = new ImageView(new Image("/images/front/" + devCard.getImage()));
                    break;
                case 2:
                    newCard = new ImageView(new Image("/images/front/" + devCard.getImage()));
                    break;
            }
        }
    }

    @FXML
    void baseProduction(ActionEvent event) {
        //TODO finestra che fa comparire la scelta di risorse
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
