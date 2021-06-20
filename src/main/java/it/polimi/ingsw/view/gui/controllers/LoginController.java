package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends GUIController {

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

    @FXML
    private void login(ActionEvent actionEvent) {
        if(((GUI) super.client.getView()).isSinglePlayer()) {
            super.client.setUsername(this.username_textField.getText());
            //super.client.sendLogin(); FIXME
            super.client.getView().displayLoginSuccessful(this.username_textField.getText());
            super.client.getView().startingSetupUpdate();
            super.client.localGameSetup();
        }
        this.login_button.setDisable(true);
        //super.sendMessage(new Message(this.username_textField.getText(), MessageType.LOGIN));
    }

    public void loginFailed() {
        this.error_label.setText("ERROR: username already taken or invalid");
        this.error_label.setVisible(true);
        this.login_button.setDisable(false);
    }

}
