package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField serverIP_textField;
    @FXML
    private TextField username_textField;
    @FXML
    private TextField serverPort_textField;
    @FXML
    private CheckBox defaultServerPort_checkBox;
    @FXML
    private Button login_button;
    @FXML
    private Label error_label;

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
            // TODO LOGIN
        } else {
            this.error_label.setVisible(true);
            this.error_label.setText("ERROR: all fields are required");
        }
    }
}
