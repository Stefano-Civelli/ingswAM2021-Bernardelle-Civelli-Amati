package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.model.updatecontainers.TurnState;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.GSON;

/**
 * Class instantiated once a game is played in local configuration.
 * This class connects the Client class with the TurnManager one
 */
public class LocalVirtualModel implements VirtualModel, PhaseChangedObservable{

   private TurnManager turnManager;
   private ClientTurnManagerInterface clientTurnManager;
   private Client client;

   /**
    * Constructor for LocalVirtualModel class
    * @param turnManager, instance of TurnManager used to forward actions
    * @param clientTurnManager, instance of ClientTurnManager
    * @param client, client's instance
    */
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
   public void notifyPhaseChanged(TurnState nextPhase) {
      client.phaseUpdate(nextPhase);
   }


   private void actionAnswereMessage(Message answerMessage){
      if(answerMessage == null)
         return;
      MessageType messageType = answerMessage.getMessageType();
      String payload = answerMessage.getPayload();
      switch (messageType){
         case ERROR:
            client.handleError(ErrorType.fromValue(GSON.getGsonBuilder().fromJson(payload, String.class)));
            break;
         case NEXT_TURN_STATE:
            notifyPhaseChanged(answerMessage.getPayloadByType(TurnState.class));
            break;
      }
   }
}