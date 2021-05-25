package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.controllers.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SceneController {

    private static Stage activeStage;

    protected static void setActiveStage(Stage activeStage) {
        SceneController.activeStage = activeStage;
    }

    public static void changeScene(String fxml) {
        activeStage.hide();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            activeStage.hide();
            activeStage = stage;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
