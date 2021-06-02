package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.utility.GSON;

public class LocalVirtualModel implements VirtualModel{

   private TurnManager turnManager;
   private ClientTurnManagerInterface clientTurnManager;

   public LocalVirtualModel(TurnManager turnManager, ClientTurnManagerInterface clientTurnManager) {
      this.turnManager = turnManager;
      this.clientTurnManager = clientTurnManager;
   }

   @Override
   public void handleAction(Action action) {
      Message nextPhaseMessage = null;
      try {
        nextPhaseMessage = this.turnManager.handleAction(action);
      } catch (NoConnectedPlayerException e) {e.printStackTrace();}
      System.out.println(nextPhaseMessage.getPayload());
      TurnManager.TurnState nextPhase = GSON.getGsonBuilder().fromJson(nextPhaseMessage.getPayload(), TurnManager.TurnState.class);
      clientTurnManager.setStateIsPlayerChanged(nextPhase);
   }

   @Override
   public void handleMessage(Message message) { /*does nothing*/ }

   @Override
   public void stop() { /*does nothing*/ }
}