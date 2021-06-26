package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.controllers.ConnectController;
import it.polimi.ingsw.view.gui.controllers.GUIController;
import it.polimi.ingsw.view.gui.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Used to manages GUI's stages loading fxml files and conserve their root controller
 * (Note that only one stage will be active at a time)
 */
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

    /**
     * Initialize the first stage and ask the user for server connection
     */
    public void start(Stage stage) throws IOException {
        ConnectController a = null;
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

    /**
     * Ask the user to insert username and gameID
     * (using the current stage)
     */
    public void loadLogin() {
        this.changeScene(GuiResources.loginFXML, this.client);
        LoginController controller = (LoginController) this.currentController;
        controller.checkLocal();
    }

    /**
     * Show a loading screen waiting for other players connection
     * (using the current stage)
     */
    public void loadLobby() {
        this.changeScene(GuiResources.lobbyFXML, this.client);
    }

    /**
     * Ask the user to insert the number of player of the game
     * (using the current stage)
     */
    public void loadNumberOfPlayer() {
        this.changeScene(GuiResources.numberOfPlayerFXML, this.client);
    }

    /**
     * Load the game board
     * (closing the current stage and show in a new stage)
     */
    public void loadGameboard() {
        this.changeStage(GuiResources.gameboardFXML, this.client);
    }

    /**
     * Display victory
     * (using the current stage)
     */
    public void loadEndGameWin() {
        this.changeScene(GuiResources.endGameWinFXML, this.client);
    }

    /**
     * Display lost
     * (using the current stage)
     */
    public void loadEndGameLose() {
        this.changeScene(GuiResources.endGameLoseFXML, this.client);
    }

}
