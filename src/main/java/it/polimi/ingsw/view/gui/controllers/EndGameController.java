package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndGameController extends GUIController {

    @FXML
    public Label score_label;

    @FXML
    public Label winner_label;

    @FXML
    private void quitButton(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void setWinner(String winner, int score) {
        if(this.winner_label != null)
            this.winner_label.setText(winner);
        if(this.score_label != null)
            this.score_label.setText(Integer.toString(score));
    }

}
