package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.utility.GSON;

public class LocalVirtualModel implements VirtualModel, PhaseChangedObservable{

   private TurnManager turnManager;
   private ClientTurnManagerInterface clientTurnManager;
   private Client client;

   public LocalVirtualModel(TurnManager turnManager, ClientTurnManagerInterface clientTurnManager, Client client) {
      this.turnManager = turnManager;
      this.clientTurnManager = clientTurnManager;
      this.client = client;
   }

   @Override
   public void handleAction(Action action) {
      Message nextPhaseMessage = null;
      try {
        nextPhaseMessage = this.turnManager.handleAction(action);
      } catch (NoConnectedPlayerException e) {e.printStackTrace();}
      System.out.println(nextPhaseMessage.getPayload());
      notifyPhaseChanged(nextPhaseMessage.getPayload());
   }

   @Override
   public void handleMessage(Message message) { /*does nothing*/ }

   @Override
   public void stop() { /*does nothing*/ }

   @Override
   public void notifyPhaseChanged(String nextPhase) {
      client.update(nextPhase);
   }
}