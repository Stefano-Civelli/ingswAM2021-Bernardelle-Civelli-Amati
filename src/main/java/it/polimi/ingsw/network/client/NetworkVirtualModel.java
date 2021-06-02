package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class NetworkVirtualModel implements VirtualModel{

   private ServerConnector serverConnector;

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
