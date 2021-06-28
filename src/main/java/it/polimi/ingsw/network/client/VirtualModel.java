package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.network.messages.Message;

/**
 * Interface that hides client side if the game is running in local or network
 */
public interface VirtualModel {

   /**
    * Forward action to serve
    * @param action, action to forward
    */
   void handleAction(Action action);

   /**
    * Forward message to server
    * @param message, message to forward
    */
   void handleMessage(Message message);

   /**
    * Stops action and message forwarding
    */
   void stop();
}
