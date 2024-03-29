package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

/**
 * Java controller of the numberOfPlayer.fxml file
 */
public class NumberOfPlayersController extends GUIController {

    @FXML
    private Button createGame_button;
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
    private void initialize() {
        this.multiplayer_pane.setVisible(false);
    }

    @FXML
    private void modeRadioButtons() {
        this.multiplayer_pane.setVisible(this.multiPlayer_radioButton.isSelected());
    }

    @FXML
    private void createGame() {
        this.createGame_button.setDisable(true);
        int playersNumber = 0;
        if(this.singlePlayer_radioButton.isSelected())
            playersNumber = 1;
        else if(this.twoPlayers_radioButton.isSelected())
            playersNumber = 2;
        else if(this.threePlayers_radioButton.isSelected())
            playersNumber = 3;
        else if(this.fourPlayers_radioButton.isSelected())
            playersNumber = 4;
        int finalPlayersNumber = playersNumber;
        this.client.forwardMessage(new Message(MessageType.NUMBER_OF_PLAYERS, finalPlayersNumber));
    }

}
