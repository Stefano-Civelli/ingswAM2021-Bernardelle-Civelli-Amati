package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ConnectController extends GUIController {

    @FXML
    private TextField serverIP_textField;
    @FXML
    private TextField serverPort_textField;
    @FXML
    public CheckBox localHostServerIP_checkBox;
    @FXML
    private CheckBox defaultServerPort_checkBox;
    @FXML
    private Label error_label;
    @FXML
    private Button connect_button;

    @FXML
    private void initialize() {
        this.serverIP_textField.setText("127.0.0.1");
        this.serverIP_textField.setDisable(true);
        this.localHostServerIP_checkBox.setSelected(true);
        this.serverPort_textField.setText("6754");
        this.serverPort_textField.setDisable(true);
        this.defaultServerPort_checkBox.setSelected(true);
    }

    @FXML
    private void localhostServerIP(ActionEvent actionEvent) {
        if(this.localHostServerIP_checkBox.isSelected()) {
            this.serverIP_textField.setText("127.0.0.1");
            this.serverIP_textField.setDisable(true);
        } else {
            this.serverIP_textField.setText("");
            this.serverIP_textField.setDisable(false);
        }
    }

    @FXML
    private void defaultServerPort(ActionEvent actionEvent) {
        if(this.defaultServerPort_checkBox.isSelected()) {
            this.serverPort_textField.setText("6754");
            this.serverPort_textField.setDisable(true);
        } else {
            this.serverPort_textField.setText("");
            this.serverPort_textField.setDisable(false);
        }
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        if(!this.serverPort_textField.getText().equals("")
                && !this.serverIP_textField.getText().equals("")) {
            this.connect_button.setDisable(true);
            this.error_label.setVisible(false);
            this.error_label.setText("ERROR");
            // TODO gestire errori
            setSocket(this.serverIP_textField.getText(), Integer.parseInt(this.serverPort_textField.getText()));
        } else {
            this.error_label.setVisible(true);
            this.error_label.setText("ERROR: all fields are required");
        }
    }

    public void loginError(String error) {
        this.error_label.setVisible(true);
        this.error_label.setText("ERROR: " + error);
        this.connect_button.setDisable(false);
    }

}
