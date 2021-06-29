package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Java controller of the login.fxml file
 */
public class LoginController extends GUIController {

    @FXML
    private Label gameId_label;
    @FXML
    private RadioButton join_radioButton;
    @FXML
    private RadioButton create_radioButton;
    @FXML
    private TextField gameID_textField;
    @FXML
    private Label error_label;
    @FXML
    private TextField username_textField;
    @FXML
    private Button login_button;

    @FXML
    private void initialize() {
        this.error_label.setVisible(false);
    }

    /**
     * Check if the game is local and in case set invisible the correct components
     */
    public void checkLocal() {
        if(super.client.isLocal()) {
            this.gameId_label.setVisible(false);
            this.join_radioButton.setVisible(false);
            this.create_radioButton.setVisible(false);
            this.gameID_textField.setVisible(false);
        }
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        if(super.client.isLocal()) {
            super.client.setUsername(this.username_textField.getText());
            super.client.getView().displayLoginSuccessful(this.username_textField.getText());
            super.client.getView().startingSetupUpdate();
            super.client.localGameSetup();
        }
        this.login_button.setDisable(true);
        super.client.setUsername(this.username_textField.getText());
        boolean create = this.create_radioButton.isSelected();
        super.client.sendLogin(create, this.gameID_textField.getText());
    }

    /**
     * Notify the player that the username is invalid
     */
    public void loginFailed() {
        this.error_label.setText("ERROR: username already taken or invalid in this game");
        this.error_label.setVisible(true);
        this.login_button.setDisable(false);
    }

    /**
     * notify the player that the name of the match they tried to create is occupied by another match
     */
    public void gameAlreadyExists() {
        this.error_label.setText("ERROR: invalid gameID: this game already exists");
        this.error_label.setVisible(true);
        this.login_button.setDisable(false);
    }

    /**
     * Notify the player that they cannot join the match they tried to:
     * i.e. the match doesn't exist, the match is full or the match is already started
     */
    public void gameCannotJoin() {
        this.error_label.setText("ERROR: invalid gameID: this match doesn't exist or is already started");
        this.error_label.setVisible(true);
        this.login_button.setDisable(false);
    }

    /**
     * Notify the player that the creation of the game they tried to join is not already completed
     */
    public void gameWait() {
        this.error_label.setText("Wait a moment: your mate is creating this game");
        this.error_label.setVisible(true);
        this.login_button.setDisable(false);
    }

}
