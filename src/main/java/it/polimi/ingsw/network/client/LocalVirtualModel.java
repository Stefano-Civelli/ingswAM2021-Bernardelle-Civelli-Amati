package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.network.messages.Message;

public class LocalVirtualModel implements VirtualModel{

   private TurnManager turnManager;

   public LocalVirtualModel(TurnManager turnManager) {
      this.turnManager = turnManager;
   }

   @Override
   public void handleAction(Action action) {
      try {
         this.turnManager.handleAction(action);
      } catch (NoConnectedPlayerException e) {e.printStackTrace();}
   }

   @Override
   public void handleMessage(Message message) {
      //does nothing
   }

   @Override
   public void stop() {
      //does nothing ?
   }


}
