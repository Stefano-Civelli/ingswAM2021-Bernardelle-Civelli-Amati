package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

/**
 * A generic fxml file controller
 */
public abstract class GUIController {

    protected Client client = null;

    /**
     * Set the client of this controller.
     * The client will be set only once.
     *
     * @param client the client to set
     * @return the client of this controller, can be the player just set or another if it was already set.
     */
    public Client setClient(Client client) {
        if(this.client == null)
            this.client = client;
        return this.client;
    }

}
