package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updateContainers.TurnState;

public interface ClientTurnManagerInterface {

   public void currentPhasePrint();
   public String getCurrentPlayer();
   public void setCurrentPlayer(String currentPlayer);
   /**
    * set new phase and new currentPlayer
    *
    * @param newState the new Turn State
    */
   public void setStateIsPlayerChanged(TurnState newState);
}
