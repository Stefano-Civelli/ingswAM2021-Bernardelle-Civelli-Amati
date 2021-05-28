package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.messages.Message;

public interface ViewObserver {
    void setServerIP(String ip);
    void setServerPort(int port);
    void sendMessage(Message message);
    void connectToServer();
    void setUsername(String username);
}
