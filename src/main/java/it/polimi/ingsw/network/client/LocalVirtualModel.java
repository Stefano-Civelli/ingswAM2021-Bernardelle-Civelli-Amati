package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
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
      Message answerMessage = null;
      try {
         answerMessage = this.turnManager.handleAction(action);
      } catch (NoConnectedPlayerException e) {e.printStackTrace();}
      actionAnswereMessage(answerMessage);

   }

   @Override
   public void handleMessage(Message message) { /*does nothing*/ }

   @Override
   public void stop() { /*does nothing*/ }

   @Override
   public void notifyPhaseChanged(TurnManager.TurnState nextPhase) {
      client.phaseUpdate(nextPhase);
   }


   private void actionAnswereMessage(Message answerMessage){
      MessageType messageType = answerMessage.getMessageType();
      String payload = answerMessage.getPayload();
      switch (messageType){
         case ERROR:
            client.handleError(ErrorType.fromValue(GSON.getGsonBuilder().fromJson(payload, String.class)));
            break;
         case NEXT_TURN_STATE:
            notifyPhaseChanged(answerMessage.getPayloadByType(TurnManager.TurnState.class));
            break;
      }
   }
}