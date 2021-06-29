package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FatalErrorController extends GUIController {

    @FXML
    private Label errorDescription_label;

    public void setErrorDescription(String error) {
        this.errorDescription_label.setText(error);
    }

    @FXML
    private void closeGame(ActionEvent actionEvent) {
        System.exit(0);
    }

}
