package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

public interface MessageHandler {

   void handleServerConnection();

   void stop();

   void sendToServer(Message msg);

}
