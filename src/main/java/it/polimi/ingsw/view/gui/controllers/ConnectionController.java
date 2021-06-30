package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Java controller of the connection.fxml file
 */
public class ConnectionController extends GUIController {

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
    private void localhostServerIP() {
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
    private void defaultServerPort() {
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
    private void connect() {
        if(this.local_checkBox.isSelected()) {
            super.client.setLocal();
            super.client.getView().displayLogin();
        } else {
            if (validPort(this.serverPort_textField.getText())
                    && validIP(this.serverIP_textField.getText())) {
                this.connect_button.setDisable(true);
                this.error_label.setVisible(false);
                boolean connected = this.client.connectToServer(this.serverIP_textField.getText(), Integer.parseInt(this.serverPort_textField.getText()));
                if(connected)
                    this.client.getView().displayLogin();
            } else {
                this.error_label.setText("ERROR");
                this.error_label.setVisible(true);
            }
        }
    }

    @FXML
    private void local() {
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

    /**
     * Notify the player that the connect failed
     * @param error error message
     */
    public void loginError(String error) {
        this.error_label.setVisible(true);
        this.error_label.setText("ERROR: " + error);
        this.connect_button.setDisable(false);
    }

    private boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean validPort(String port) {
        try{
            int portInt = Integer.parseInt(port);
            if(portInt < 0 || portInt > 65535)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
