package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class numberOfPlayersController extends ViewObservable implements GUIController {

    @FXML
    private Pane multiplayer_pane;
    @FXML
    private RadioButton singlePlayer_radioButton;
    @FXML
    private RadioButton multiPlayer_radioButton;
    @FXML
    private RadioButton twoPlayers_radioButton;
    @FXML
    private RadioButton threePlayers_radioButton;
    @FXML
    private RadioButton fourPlayers_radioButton;

    @FXML
    public void initialize() {
        this.multiplayer_pane.setVisible(false);
    }

    @FXML
    public void modeRadioButtons(ActionEvent actionEvent) {
        this.multiplayer_pane.setVisible(this.multiPlayer_radioButton.isSelected());
    }

}
