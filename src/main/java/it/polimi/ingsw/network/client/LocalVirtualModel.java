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
   public void notifyPhaseChanged(String nextPhase) {
      client.update(nextPhase);
   }


   private void actionAnswereMessage(Message answerMessage){
      MessageType messageType = answerMessage.getMessageType();
      String payload = answerMessage.getPayload();
      switch (messageType){
         case ERROR:
            payload = payload.replaceAll("\\\"", "");
            client.handleError(ErrorType.fromValue(payload));
            break;
         case NEXT_TURN_STATE: //questa cosa non serve dirla al client, basta dirgli tipo turno finito a tutti e basta, poi loro con la logica capiscono cosa fare
            System.out.println(answerMessage.getPayload());
            notifyPhaseChanged(answerMessage.getPayload());
            break;
      }
   }
}