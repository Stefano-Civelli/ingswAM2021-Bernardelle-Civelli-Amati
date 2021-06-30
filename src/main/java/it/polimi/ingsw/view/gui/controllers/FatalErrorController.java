package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Java controller of the fatalError.fxml file
 */
public class FatalErrorController extends GUIController {

    @FXML
    private Label errorDescription_label;

    /**
     * Set the error description that must be shown to the client
     *
     * @param error the description of the error
     */
    public void setErrorDescription(String error) {
        this.errorDescription_label.setText(error);
    }

    @FXML
    private void closeGame() {
        super.client.close();
    }

}
