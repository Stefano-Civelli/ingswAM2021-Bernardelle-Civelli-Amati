package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManager;
import it.polimi.ingsw.view.gui.controllers.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIStarter extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Client client = new Client();
        GUI gui = new GUI(client);
        client.setView(gui);
        client.setTurnManager(new ClientTurnManager(client, gui));
        //client.setUpdater(gui);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/connection.fxml"));
        Parent root = loader.load();
        GUIController controller = loader.getController();
        gui.getSceneController().setCurrentController(controller);
        controller.setClient(client);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/logo.png")));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        gui.getSceneController().setActiveStage(stage);
        stage.setResizable(false);
        stage.show();
    }

}
