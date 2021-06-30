package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Class instantiated once a game is played in network configuration.
 * This class connects the Client class with the ServerConnector one
 */
public class NetworkVirtualModel implements VirtualModel{

   private final ServerConnector serverConnector;

   /**
    * Constructor for NetworkVirtualModel class
    * @param serverConnector, instance of ServerConnector used to send messages to server
    */
   public NetworkVirtualModel(ServerConnector serverConnector) {
      this.serverConnector = serverConnector;
   }

   @Override
   public void handleAction(Action action) {
      this.serverConnector.sendToServer(new Message(MessageType.ACTION, action));
   }

   public void handleMessage(Message message){
      this.serverConnector.sendToServer(message);
   }

   @Override
   public void stop() {
      serverConnector.stop();
   }
}
