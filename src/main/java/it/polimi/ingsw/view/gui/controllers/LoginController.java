package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController extends ViewObservable implements GUIController {

    @FXML
    private TextField serverIP_textField;
    @FXML
    private TextField serverPort_textField;
    @FXML
    private TextField username_textField;
    @FXML
    public CheckBox localHostServerIP_checkBox;
    @FXML
    private CheckBox defaultServerPort_checkBox;
    @FXML
    private Label error_label;
    @FXML
    private Button login_button;

    @FXML
    public void initialize() {
        this.serverIP_textField.setText("127.0.0.1");
        this.serverIP_textField.setDisable(true);
        this.localHostServerIP_checkBox.setSelected(true);
        this.serverPort_textField.setText("6754");
        this.serverPort_textField.setDisable(true);
        this.defaultServerPort_checkBox.setSelected(true);
    }

    public void localhostServerIP(ActionEvent actionEvent) {
        if(this.localHostServerIP_checkBox.isSelected()) {
            this.serverIP_textField.setText("127.0.0.1");
            this.serverIP_textField.setDisable(true);
        } else {
            this.serverIP_textField.setText("");
            this.serverIP_textField.setDisable(false);
        }
    }

    public void defaultServerPort(ActionEvent actionEvent) {
        if(this.defaultServerPort_checkBox.isSelected()) {
            this.serverPort_textField.setText("6754");
            this.serverPort_textField.setDisable(true);
        } else {
            this.serverPort_textField.setText("");
            this.serverPort_textField.setDisable(false);
        }
    }

    public void login(ActionEvent actionEvent) {
        if(!this.serverPort_textField.getText().equals("")
                && !this.username_textField.getText().equals("")
                && !this.serverIP_textField.getText().equals("")) {
            this.login_button.setDisable(true);
            this.error_label.setVisible(false);
            this.error_label.setText("ERROR");
            // TODO gestire errori
            new Thread(() -> { notifySetSocket(this.serverIP_textField.getText(), Integer.parseInt(this.serverPort_textField.getText()));
                                notifySceneObserver(new Message(MessageType.LOGIN));
            }).start();
        } else {
            this.error_label.setVisible(true);
            this.error_label.setText("ERROR: all fields are required");
        }
    }

    public void loginError() {
        this.login_button.setDisable(false);
        this.error_label.setVisible(true);
        this.error_label.setText("ERROR: login failed");
    }

}
