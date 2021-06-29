package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Used to manages GUI's stages loading fxml files and conserve their root controllers
 * (Note that only one stage will be active at a time)
 */
public class SceneController {

    private final Client client;

    private Stage activeStage = null;

    private ConnectionController connectionController = null;
    private LoginController loginController = null;
    private NumberOfPlayersController numberOfPlayersController = null;
    private GameboardController gameboardController = null;
    private EndGameController endGameController = null;

    public SceneController(Client client) {
        this.client = client;
    }

    private FXMLLoader changeScene(String fxml, Client client) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            GUIController controller = loader.getController();
            if(controller != null) {
                controller.setClient(client);
            }
            this.activeStage.setScene(new Scene(root));
            return loader;
        } catch (IOException e) {
            //TODO gestire
            e.printStackTrace();
        }
        return null;
    }

    private FXMLLoader changeStage(String fxml, Client client) {
        this.activeStage.hide();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();
            GUIController controller = loader.getController();
            if(controller != null)
                controller.setClient(client);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/Logo.png")));
            stage.setTitle("Masters of Renaissance");
            stage.setResizable(false);
            this.activeStage = stage;
            stage.show();
            return loader;
        } catch (IOException e) {
            //TODO gestire
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Initialize the first stage and ask the user for server connection
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(GuiResources.connectionFXML));
        Parent root = loader.load();
        this.connectionController = loader.getController();
        this.connectionController.setClient(client);
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
        this.clearController();
        this.loginController = this.changeScene(GuiResources.loginFXML, this.client).getController();
        this.loginController.checkLocal();

    }

    /**
     * Show a loading screen waiting for other players connection
     * (using the current stage)
     */
    public void loadLobby() {
        this.clearController();
        this.changeScene(GuiResources.lobbyFXML, this.client);
    }

    /**
     * Ask the user to insert the number of player of the game
     * (using the current stage)
     */
    public void loadNumberOfPlayer() {
        this.clearController();
        this.numberOfPlayersController = this.changeScene(GuiResources.numberOfPlayerFXML, this.client).getController();
    }

    /**
     * Load the game board
     * (closing the current stage and show in a new stage)
     */
    public void loadGameboard() {
        this.clearController();
        this.gameboardController = this.changeStage(GuiResources.gameboardFXML, this.client).getController();
    }

    /**
     * Display victory
     * (using the current stage)
     */
    public void loadEndGameWin() {
        this.clearController();
        this.endGameController = this.changeScene(GuiResources.endGameWinFXML, this.client).getController();
    }

    /**
     * Display lost
     * (using the current stage)
     */
    public void loadEndGameLose() {
        this.clearController();
        this.endGameController = this.changeScene(GuiResources.endGameLoseFXML, this.client).getController();
    }

    private void clearController() {
            this.connectionController = null;
            this.loginController = null;
            this.numberOfPlayersController = null;
            this.gameboardController = null;
            this.endGameController = null;
    }

    public ConnectionController getConnectionController() {
        return connectionController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public NumberOfPlayersController getNumberOfPlayersController() {
        return numberOfPlayersController;
    }

    public GameboardController getGameboardController() {
        return gameboardController;
    }

    public EndGameController getEndGameController() {
        return endGameController;
    }

}
