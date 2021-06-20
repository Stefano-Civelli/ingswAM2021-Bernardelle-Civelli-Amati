package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ConnectController extends GUIController {

    @FXML
    private CheckBox local_checkBox;
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
        this.localHostServerIP_checkBox.setDisable(false);
        this.serverPort_textField.setText("6754");
        this.serverPort_textField.setDisable(true);
        this.defaultServerPort_checkBox.setSelected(true);
        this.defaultServerPort_checkBox.setDisable(false);
        this.local_checkBox.setSelected(false);
    }

    @FXML
    private void localhostServerIP(ActionEvent actionEvent) {
        if(this.localHostServerIP_checkBox.isSelected()) {
            this.serverIP_textField.setText("127.0.0.1");
            this.serverIP_textField.setDisable(true);
        } else {
            this.serverIP_textField.setText("");
            this.serverIP_textField.setDisable(false);
            this.serverIP_textField.setEditable(true);
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
            this.serverPort_textField.setEditable(true);
        }
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        if(this.local_checkBox.isSelected()) {
            ((GUI) super.client.getView()).local(); // FIXME
            super.client.getView().displayLogin();
        } else {
            if (!this.serverPort_textField.getText().equals("")
                    && !this.serverIP_textField.getText().equals("")) {
                this.connect_button.setDisable(true);
                this.error_label.setVisible(false);
                this.error_label.setText("ERROR");
                boolean connected = this.client.connectToServer(this.serverIP_textField.getText(), Integer.parseInt(this.serverPort_textField.getText()));
                if(connected)
                    this.client.getView().displayLogin();
            } else {
                this.error_label.setVisible(true);
                this.error_label.setText("ERROR: all fields are required");
            }
        }
    }

    @FXML
    private void local(ActionEvent actionEvent) {
        if(this.local_checkBox.isSelected()) {
            this.serverIP_textField.setText("");
            this.serverIP_textField.setDisable(true);
            this.localHostServerIP_checkBox.setSelected(false);
            this.localHostServerIP_checkBox.setDisable(true);
            this.serverPort_textField.setText("");
            this.serverPort_textField.setDisable(true);
            this.defaultServerPort_checkBox.setSelected(false);
            this.defaultServerPort_checkBox.setDisable(true);
        } else {
            this.initialize();
        }
    }

    public void loginError(String error) {
        this.error_label.setVisible(true);
        this.error_label.setText("ERROR: " + error);
        this.connect_button.setDisable(false);
    }

}
