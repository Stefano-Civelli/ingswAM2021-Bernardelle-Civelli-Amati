package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.controllers.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static Stage activeStage;
    private static GUIController currentController;

    protected static void setActiveStage(Stage activeStage) {
        SceneController.activeStage = activeStage;
    }

    public static void changeScene(String fxml) {
//        activeStage.hide();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            activeStage.hide();
//            activeStage = stage;
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            currentController = loader.getController();
            activeStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeStage(String fxml) {
        activeStage.hide();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            currentController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            activeStage = stage;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GUIController getController() {
        return currentController;
    }

    public static void setCurrentController(GUIController controller) {
        currentController = controller;
    }
}
