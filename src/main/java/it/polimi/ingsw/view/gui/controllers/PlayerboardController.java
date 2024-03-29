package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.cli.drawer.DevelopCardConstructor;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import it.polimi.ingsw.view.gui.GuiResources;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerboardController extends GUIController {

    private boolean isPlayer = false;
    private String username;
    private final Map<ImageView, Integer> leaderImageIdMap = new HashMap<>();
    private ImageView selectedLeader = null;
    private ImageView selectedLeaderToDiscard;
    private ResourceType firstToConsume = null;
    private ResourceType secondToConsume = null;
    private Pair<ResourceType, Integer>[] storageLevels;
    private List<ImageView>[] guiStorage;

    private boolean forLeaderProd = false;

    @FXML
    private final List<Pair<ResourceType, HBox>> resourceTypeHBoxPairList = new ArrayList<>();
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
    private GridPane lorenzoTrackGrid;
    @FXML
    private ImageView trackPosition;
    @FXML
    private ImageView lorenzoTrackPosition;
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
    @SuppressWarnings("unchecked") // For lines 131 and 135
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
        this.lorenzoTrackGrid.setVisible(false);


        //non so se ste cose sono valide qua
        this.storageLevels = new Pair[5];
        for(int i=0; i<5; i++)
            storageLevels[i] = new Pair<>(null, null);

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

    public void thisClientPlayer() {
        this.isPlayer = true;
    }

    /**
     * Display leader cards during setup
     * @param ledersID leader cards ids list
     */
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

    /**
     * Display a chest update
     * @param stateUpdate model update represent a chest update
     */
    public void updateChest(ChestUpdate stateUpdate){
        ResourceType resource = stateUpdate.getResourceType();
        int quantity = stateUpdate.getQuantity();

        switch(resource) {
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

    /**
     * Display a tmp chest update after production
     * @param chestUpdate model update represent a chest update
     */
    public void updateTempChest(ChestUpdate chestUpdate) {
        ResourceType resource = chestUpdate.getResourceType();
        int quantity = chestUpdate.getQuantity();
        Label resourceCounter = null;
        switch (resource) {
            case GOLD:
                resourceCounter = coinCounter;
                break;
            case STONE:
                resourceCounter = stoneCounter;
                break;
            case SHIELD:
                resourceCounter = shieldCounter;
                break;
            case SERVANT:
                resourceCounter = servantCounter;
                break;
        }
        String string = resourceCounter.getText().split("\\(")[0] + "(" + quantity + ")";
        resourceCounter.setText(string);
    }

    /**
     * Display merging between tmp chest and normal chest
     */
    public void chestMergeUpdate() {
        String[] amounts;
        int amount;

        amounts = coinCounter.getText().split("\\(");
        amount = Integer.parseInt(amounts[0]) + (amounts.length >= 2 ? Integer.parseInt(amounts[1].replace(")", "")) : 0);
        coinCounter.setText(Integer.toString(amount));

        amounts = stoneCounter.getText().split("\\(");
        amount = Integer.parseInt(amounts[0]) + (amounts.length >= 2 ? Integer.parseInt(amounts[1].replace(")", "")) : 0);
        stoneCounter.setText(Integer.toString(amount));

        amounts = shieldCounter.getText().split("\\(");
        amount = Integer.parseInt(amounts[0]) + (amounts.length >= 2 ? Integer.parseInt(amounts[1].replace(")", "")) : 0);
        shieldCounter.setText(Integer.toString(amount));

        amounts = servantCounter.getText().split("\\(");
        amount = Integer.parseInt(amounts[0]) + (amounts.length >= 2 ? Integer.parseInt(amounts[1].replace(")", "")) : 0);
        servantCounter.setText(Integer.toString(amount));
    }

    /**
     * Display a track position update
     * @param stateUpdate a model update representing a track update
     */
    public void updatePlayerTrack(TrackUpdate stateUpdate) {
        int position = stateUpdate.getPlayerPosition();
        this.trackPosition.setVisible(false);
        ImageView image = new ImageView(GuiResources.faithTrackCross);
        this.trackPosition = image;
        image.setFitWidth(GuiResources.trackCrossWidth);
        image.setFitHeight(GuiResources.trackCrossHeight);

        positionTrackMarker(this.trackGrid, image, position);
    }

    private void positionTrackMarker(GridPane grid, ImageView image, int position){
        if(position<3)
            grid.add(image, position, 2);
        if(position>2 && position<5)
            grid.add(image, 2, 4-position);
        if(position>4 && position<10)
            grid.add(image, position-2, 0);
        if(position>9 && position<12)
            grid.add(image, 7, position-9);
        if(position>11 && position<17)
            grid.add(image, position-4, 2);
        if(position>16 && position<19)
            grid.add(image, 12, 18-position);
        if(position>18)
            grid.add(image, position-6, 0);
    }

    /**
     * Display a pope cards track update
     * @param stateUpdate the vatican report update
     */
    public void updateVatican(VaticanReport stateUpdate) {
        int zone = stateUpdate.getZone();
        if(stateUpdate.isActive())
            switch (zone) {
                case 0:
                    popeCard1.setImage(GuiResources.popeFavorFront1);
                    break;
                case 1:
                    popeCard2.setImage(GuiResources.popeFavorFront2);
                    break;
                case 2:
                    popeCard3.setImage(GuiResources.popeFavorFront3);
                    break;
            }
    }

    /**
     * Display a card slot update
     * @param stateUpdate the card slot update
     */
    public void updateCardSlot(CardSlotUpdate stateUpdate) {
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
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }


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
        clone.setOnMouseEntered(this::mouseHover);
        clone.setOnMouseExited(this::mouseHoverReset);

        playerBoardPane.getChildren().add(clone);

    }

    @FXML
    private void baseProduction() {
        this.baseProdChoice.setVisible(true);
    }

    @FXML
    private void produceSlot1() {
        createProductionAction(0);
    }
    @FXML
    private void produceSlot2() {
        createProductionAction(1);
    }
    @FXML
    private void produceSlot3() {
        createProductionAction(2);
    }

    private void createProductionAction(int cardIndex){
        Action productionAction = new ProductionAction(cardIndex);
        client.forwardAction(productionAction);
    }

    private ImageView cloneImageView(ImageView imageView){
        return new ImageView(imageView.getImage());
    }

    /**
     * Ask the player which leader must be used to convert a white marble
     */
    public void askLeaderOnWhite(){
        this.enableLeader();
        leader0_ImageView.setOnMouseClicked(this::selectWhiteLeader);
        leader1_ImageView.setOnMouseClicked(this::selectWhiteLeader);
        leader2_ImageView.setOnMouseClicked(this::selectWhiteLeader);
        leader3_ImageView.setOnMouseClicked(this::selectWhiteLeader);
    }

    @FXML
    private void selectWhiteLeader(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        Action chooseLeaderOnWhiteMarbleAction = new ChooseLeaderOnWhiteMarbleAction(leaderImageIdMap.get(source));
        client.forwardAction(chooseLeaderOnWhiteMarbleAction);
        //set click on leaders to do nothing
        leader0_ImageView.setOnMouseClicked(null);
        leader1_ImageView.setOnMouseClicked(null);
        leader2_ImageView.setOnMouseClicked(null);
        leader3_ImageView.setOnMouseClicked(null);
        this.disableLeader();
    }


    @FXML
    private void showDiscardActivateMenu(MouseEvent event) {
        this.selectedLeader = (ImageView) event.getSource();
        this.activateLeaderButton.setVisible(true);
        this.discardLeaderButton.setVisible(true);
    }

    @FXML
    private void activateLeader() {
        Action activateLeaderAction = new ActivateLeaderAction(leaderImageIdMap.get(this.selectedLeader));
        client.forwardAction(activateLeaderAction);
        this.activateLeaderButton.setVisible(false);
        this.discardLeaderButton.setVisible(false);
    }

    @FXML
    private void discardLeader() {
        Action discardLeaderAction = new DiscardLeaderAction(leaderImageIdMap.get(this.selectedLeader));
        client.forwardAction(discardLeaderAction);
        this.activateLeaderButton.setVisible(false);
        this.discardLeaderButton.setVisible(false);
    }

    @FXML
    private void chooseGold() {
        if(!forLeaderProd)
            createBaseProduction(ResourceType.GOLD);
        else
            createLeaderProductionAction(ResourceType.GOLD);

    }

    @FXML
    private void chooseServant() {
        if(!forLeaderProd)
            createBaseProduction(ResourceType.SERVANT);
        else
            createLeaderProductionAction(ResourceType.SERVANT);
    }

    @FXML
    private void chooseShield() {
        if(!forLeaderProd)
            createBaseProduction(ResourceType.SHIELD);
        else
            createLeaderProductionAction(ResourceType.SHIELD);
    }

    @FXML
    private void chooseStone() {
        if(!forLeaderProd)
            createBaseProduction(ResourceType.STONE);
        else
            createLeaderProductionAction(ResourceType.STONE);
    }

    private void createBaseProduction(ResourceType r) {
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


    /**
     * Display a warehouse update
     * @param update the warehouse update
     */
    public void updateWarehouse(WarehouseUpdate update){
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
                        if (i == update.getLevel()) {
                            storageLevels[i] = new Pair<>(resource, update.getQuantity());
                            guiStorage[i].clear();
                            for(int k=0; k<update.getQuantity(); k++){
                                guiStorage[i].add(new ImageView(GuiResources.resTypeToImageMap.get(storageLevels[i].getKey())));
                            }
                        } else {
                            guiStorage[update.getLevel()].clear();
                            guiStorage[i].clear();
                            Pair<ResourceType, Integer> temp = new Pair<>(storageLevels[update.getLevel()].getKey(), storageLevels[update.getLevel()].getValue());
                            storageLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                            storageLevels[i] = new Pair<>(temp.getKey(), temp.getValue());

                            for(int k=0; k<update.getQuantity(); k++){
                                guiStorage[update.getLevel()].add(new ImageView(GuiResources.resTypeToImageMap.get(storageLevels[update.getLevel()].getKey())));
                            }

                            for(int k=0; temp.getValue()!= null && k<temp.getValue(); k++){
                                guiStorage[i].add(new ImageView(GuiResources.resTypeToImageMap.get(storageLevels[i].getKey())));
                            }
                        }
                        updateWarehouseVisuals();
                        return;
                    }
                }
            }
        }
        //se non presente creo
        storageLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());

        guiStorage[update.getLevel()] =  new ArrayList<>();
        for(int k=0; k<update.getQuantity(); k++){
            guiStorage[update.getLevel()].add(new ImageView(GuiResources.resTypeToImageMap.get(storageLevels[update.getLevel()].getKey())));
        }

        if(update.getLevel()>2) {
            boolean alreadyAdded = false;
            if(!this.resourceTypeHBoxPairList.isEmpty()) {
                for (Pair<ResourceType, HBox> p : resourceTypeHBoxPairList) {
                    if (p.getKey().equals(update.getResourceType())) { //already exists an HBOX for this resource
                        p.getValue().getChildren().clear();
                        for(ImageView i : guiStorage[update.getLevel()]){
                            i.setFitHeight(GuiResources.resourcesDimension);
                            i.setFitWidth(GuiResources.resourcesDimension);
                            p.getValue().getChildren().add(i);
                        }
                        alreadyAdded = true;
                    }
                }
            }
            if(!alreadyAdded){
                HBox resourcesHbox = new HBox(5);
                resourcesHbox.setAlignment(Pos.CENTER);
                for(ImageView i : guiStorage[update.getLevel()]){
                    i.setFitHeight(GuiResources.resourcesDimension);
                    i.setFitWidth(GuiResources.resourcesDimension);
                    resourcesHbox.getChildren().add(i);
                }
                this.resourceTypeHBoxPairList.add(new Pair<>(update.getResourceType(), resourcesHbox));
                leaderCardVbox.getChildren().add(0, resourcesHbox);
            }
        }

        updateWarehouseVisuals();
    }

    private void updateWarehouseVisuals(){

        for(ImageView i : guiStorage[2]){
            i.setFitHeight(GuiResources.resourcesDimension);
            i.setFitWidth(GuiResources.resourcesDimension);
            warehouseLevel2Hbox.getChildren().add(i);
        }
        for(ImageView i : guiStorage[1]){
            i.setFitHeight(GuiResources.resourcesDimension);
            i.setFitWidth(GuiResources.resourcesDimension);
            warehouseLevel1Hbox.getChildren().add(i);
        }
        for(ImageView i : guiStorage[0]){
            i.setFitHeight(GuiResources.resourcesDimension);
            i.setFitWidth(GuiResources.resourcesDimension);
            warehouseLevel0Hbox.getChildren().add(i);
        }
    }

    @FXML
    private void initialDiscard(MouseEvent event) {
        if(selectedLeaderToDiscard == null) {
            this.selectedLeaderToDiscard = (ImageView) event.getSource();
            this.selectedLeaderToDiscard.setVisible(false);
            this.selectedLeaderToDiscard.setDisable(true);
        }
        else {
            ImageView source = (ImageView) event.getSource();
            Action discardLeaderAction = new DiscardInitialLeaderAction(leaderImageIdMap.get(source), leaderImageIdMap.get(this.selectedLeaderToDiscard));
            leaderCardVbox.getChildren().remove(source);
            leaderCardVbox.getChildren().remove(this.selectedLeaderToDiscard);
            client.forwardAction(discardLeaderAction);

            this.selectedLeaderToDiscard = null;

            leader0_ImageView.setOnMouseClicked(this::showDiscardActivateMenu);
            leader1_ImageView.setOnMouseClicked(this::showDiscardActivateMenu);
            leader2_ImageView.setOnMouseClicked(this::showDiscardActivateMenu);
            leader3_ImageView.setOnMouseClicked(this::showDiscardActivateMenu);
        }
    }

    /**
     * Set disabled all the correct component for the producing phase
     */
    public void enableProduction() {
        this.baseProductionButton.setDisable(false);
        this.productionSlot1Button.setDisable(false);
        this.productionSlot2Button.setDisable(false);
        this.productionSlot3Button.setDisable(false);
    }

    /**
     * Set disabled all the component concerning production
     */
    public void disableProduction() {
        this.baseProductionButton.setDisable(true);
        this.productionSlot1Button.setDisable(true);
        this.productionSlot2Button.setDisable(true);
        this.productionSlot3Button.setDisable(true);
    }

    /**
     * Disable leader cards
     */
    public void disableLeader() {
        this.leaderCardVbox.setDisable(true);
    }

    /**
     * Enable leader cards
     */
    public void enableLeader() {
        this.leaderCardVbox.setDisable(false);
    }

    /**
     * Set all disabled
     */
    public void disableAll() {
        this.disableLeader();
        this.disableProduction();
        this.baseProductionButton.setVisible(false);
        this.productionSlot1Button.setVisible(false);
        this.productionSlot2Button.setVisible(false);
        this.productionSlot3Button.setVisible(false);
    }

    /**
     * Display a discarded leader card update
     * @param stateUpdate the leader update
     */
    public void updateDiscardedLeader(LeaderUpdate stateUpdate){
        int leaderId = stateUpdate.getCardId();
        for(Map.Entry<ImageView, Integer> p : leaderImageIdMap.entrySet())
            if(p.getValue() == leaderId)
                leaderCardVbox.getChildren().remove(p.getKey());
    }

    /**
     * Display an activated leader card update
     * @param stateUpdate the leader update
     */
    public void updateActivatedLeader(LeaderUpdate stateUpdate){
        int leaderId = stateUpdate.getCardId();
        if(!this.isPlayer) {
            // this isn't this client player
            String url = null;
            try {
                url = "images/front/" + LeaderConstructor.getLeaderCardFromId(leaderId).getImage();
            } catch (InvalidCardException e) {
                e.printStackTrace();
            }
            ImageView newCard = new ImageView(new Image(url));
            newCard.setFitHeight(GuiResources.cardHeight);
            newCard.setFitWidth(GuiResources.cardWidth);
            this.leaderCardVbox.getChildren().add(newCard);
        } else {
            for (Map.Entry<ImageView, Integer> p : leaderImageIdMap.entrySet()) {
                if (p.getValue() == leaderId) {
                    ImageView activatedCard = p.getKey();
                    activatedCard.setOnMouseClicked(null);
                    //p.getKey().
                    // here we could set a property to show that the card is active
                    try {
                        if (LeaderConstructor.getLeaderCardFromId(leaderId).getProductionRequirement() != null) {
                            activatedCard.setOnMouseClicked(this::activateLeaderProduction);
                        }
                    } catch (InvalidCardException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @FXML
    private void activateLeaderProduction(MouseEvent event) {
        this.selectedLeader = (ImageView) event.getSource();
        this.baseProdChoice.setVisible(true);
        this.forLeaderProd = true;

    }

    private void createLeaderProductionAction(ResourceType r){
        Action leaderProdAction = new LeaderProductionAction(leaderImageIdMap.get(this.selectedLeader),r);
        this.selectedLeader = null;
        client.forwardAction(leaderProdAction);
        this.baseProdChoice.setVisible(false);
        this.forLeaderProd = false;
    }

    /**
     * Set the black cross visible
     */
    public void setupLorenzo(){
        this.lorenzoTrackGrid.setVisible(true);
    }

    /**
     * Display a Lorenzo track update
     * @param stateUpdate the track update
     */
    public void updateLorenzoTrack(TrackUpdate stateUpdate){
        int position = stateUpdate.getPlayerPosition();
        this.lorenzoTrackPosition.setVisible(false);
        ImageView image = new ImageView(GuiResources.lorenzoFaithTrackCross);
        this.lorenzoTrackPosition = image;
        image.setFitWidth(GuiResources.lorenzoTrackCrossWidth);
        image.setFitHeight(GuiResources.lorenzoTrackCrossHeight);

        positionTrackMarker(this.lorenzoTrackGrid, image, position);
    }

    public void clearLeaderVbox(){
        this.leaderCardVbox.getChildren().clear();
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

}