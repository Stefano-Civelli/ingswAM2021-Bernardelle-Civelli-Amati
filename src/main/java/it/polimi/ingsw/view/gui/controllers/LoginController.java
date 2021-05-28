package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController extends ViewObservable implements GUIController {

    @FXML
    private TextField username_textField;
    @FXML
    private Button login_button;

    public void login(ActionEvent actionEvent) {
        new Thread(() ->
                notifySceneObserver(new Message(this.username_textField.getText(), MessageType.LOGIN))
        ).start();
    }

}
