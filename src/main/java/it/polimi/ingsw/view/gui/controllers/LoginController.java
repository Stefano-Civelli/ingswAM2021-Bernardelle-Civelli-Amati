package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
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
        if(((GUI) super.client.getView()).isLocal()) {
            this.gameId_label.setVisible(false);
            this.join_radioButton.setVisible(false);
            this.create_radioButton.setVisible(false);
            this.gameID_textField.setVisible(false);
        }
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        if(((GUI) super.client.getView()).isLocal()) {
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
     * Notify the player that the gameID is invalid
     */
    public void gameFailed() {
        this.error_label.setText("ERROR: invalid gameID");
        this.error_label.setVisible(true);
        this.login_button.setDisable(false);
    }

}
