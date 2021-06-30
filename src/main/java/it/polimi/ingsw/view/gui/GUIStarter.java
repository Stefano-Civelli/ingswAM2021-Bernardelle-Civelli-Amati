package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.client.GuiTurnManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class used by javafx to start GUI
 */
public class GUIStarter extends Application {

    private Client client = null;

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

    /**
     * Method called by javafx when the last stage will be closed:
     * close the server socket and the process
     */
    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.client.close();
    }

}