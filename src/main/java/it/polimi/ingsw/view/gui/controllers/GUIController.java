package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

/**
 * A generic fxml file controller
 */
public abstract class GUIController {

    protected Client client = null;

    public Client setClient(Client client) {
        if(this.client == null)
            this.client = client;
        return this.client;
    }

}
