package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

public abstract class GUIController {

    protected Client client = null;

    public Client setClient(Client client) {
        if(this.client == null)
            this.client = client;
        return this.client;
    }

    protected void sendMessage(Message message) {
        //FIXME messagi nella gui??
        if(this.client.getUsername() != null)
            message.setUsername(this.client.getUsername());
        this.client.forwardMessage(message);
    }

    protected void setSocket(String ip, int port) {
            this.client.connectToServer(ip, port);
            this.client.getView().displayLogin();
    }

}
