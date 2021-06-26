package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Java controller of endGameWin.fxml and endGameLose.fxml files
 */
public class EndGameController extends GUIController {

    @FXML
    private Label score_label;

    @FXML
    private Label winner_label;

    @FXML
    private void quitButton(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Set which player is the winner
     * @param winner winner username
     * @param score score
     */
    public void setWinner(String winner, int score) {
        if(this.winner_label != null)
            this.winner_label.setText(winner);
        if(this.score_label != null)
            this.score_label.setText(Integer.toString(score));
    }

}
