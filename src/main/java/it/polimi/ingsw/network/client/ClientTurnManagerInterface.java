package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.TurnManager;

public interface ClientTurnManagerInterface {

   public void currentPhasePrint();
   public String getCurrentPlayer();
   public void setCurrentPlayer(String currentPlayer);
   /**
    * set new phase and new currentPlayer
    *
    * @param newState the new Turn State
    * @return true if the currentPlayer is changed
    */
   public boolean setStateIsPlayerChanged(TurnManager.TurnState newState);
}
