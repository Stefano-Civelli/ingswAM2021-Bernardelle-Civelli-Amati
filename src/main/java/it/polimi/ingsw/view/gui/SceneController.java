package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.controllers.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage activeStage;
    private GUIController currentController;

    protected void setActiveStage(Stage activeStage) {
        this.activeStage = activeStage;
    }

    public void setCurrentController(GUIController controller) {
        this.currentController = controller;
    }

    public GUIController getCurrentController() {
        return this.currentController;
    }

    public Stage getActiveStage() {
        return this.activeStage;
    }

    public void changeScene(String fxml, Client client) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            this.currentController = loader.getController();
            if(this.currentController != null) {
                this.currentController.setClient(client);
            }
            this.activeStage.setScene(new Scene(root));
        } catch (IOException e) {
            //TODO gestire
            e.printStackTrace();
        }
    }

    public void changeStage(String fxml, Client client) {
        this.activeStage.hide();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            this.currentController = loader.getController();
            if(this.currentController != null)
                this.currentController.setClient(client);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/Logo.png")));
            stage.setTitle("Masters of Renaissance");
            this.activeStage = stage;
            stage.show();
        } catch (IOException e) {
            //TODO gestire
            e.printStackTrace();
        }
    }

}
