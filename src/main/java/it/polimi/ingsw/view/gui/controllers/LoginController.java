package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController extends ViewObservable implements GUIController {
    public TextField username_textField;
    public Button login_button;

    public void login(ActionEvent actionEvent) {
        new Thread(() ->
                notifySceneObserver(new Message(this.username_textField.getText(), MessageType.LOGIN))
        ).start();
    }
}
