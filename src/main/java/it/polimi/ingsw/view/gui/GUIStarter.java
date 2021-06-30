package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.client.GuiTurnManager;
import it.polimi.ingsw.view.gui.controllers.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class used by javafx to start GUI
 */
public class GUIStarter extends Application {

    Client client = null;

    /**
     * Method called by javafx when the gui starts:
     * instantiates: a client to receives messages from the server, a gui to receive update from the client and a scene controller to load and manages gui windows
     */
    @Override
    public void start(Stage stage) {
        this.client = new Client();
        GUI gui = new GUI(this.client);
        this.client.setView(gui);
        ClientTurnManagerInterface turnManager = new GuiTurnManager(this.client, gui);
        this.client.setClientTurnManager(turnManager);
        gui.setClientTurnManager(turnManager);
        this.client.setState(gui);
        gui.getSceneController().start(stage);
    }

    @Override
    public void stop() {
        this.client.close();
    }

}