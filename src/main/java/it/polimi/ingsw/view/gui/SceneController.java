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

    private Stage activeStage = null;
    private GUIController currentController = null;
    private final Client client;

    public SceneController(Client client) {
        this.client = client;
    }

    public GUIController getCurrentController() {
        return this.currentController;
    }

    private void changeScene(String fxml, Client client) {
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

    private void changeStage(String fxml, Client client) {
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
            stage.setResizable(false);
            this.activeStage = stage;
            stage.show();
        } catch (IOException e) {
            //TODO gestire
            e.printStackTrace();
        }
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(GuiResources.connectionFXML));
        Parent root = loader.load();
        GUIController controller = loader.getController();
        this.currentController = controller;
        controller.setClient(client);
        this.activeStage = stage;
        this.activeStage.getIcons().add(GuiResources.logo);
        this.activeStage.setTitle("Masters of Renaissance");
        this.activeStage.setResizable(false);
        this.activeStage.setScene(new Scene(root));
        this.activeStage.setResizable(false);
        this.activeStage.show();
    }

    public void loadLogin() {
        this.changeScene(GuiResources.loginFXML, this.client);
    }

    public void loadLobby() {
        this.changeScene(GuiResources.lobbyFXML, this.client);
    }

    public void loadNumberOfPlayer() {
        this.changeScene(GuiResources.numberOfPlayerFXML, this.client);
    }

    public void loadGameboard() {
        this.changeStage(GuiResources.gameboardFXML, this.client);
    }

}
