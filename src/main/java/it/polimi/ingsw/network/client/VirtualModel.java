package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.network.messages.Message;

public interface VirtualModel {

   public void handleAction(Action action);
   public void handleMessage(Message message);
   public void stop();
}
