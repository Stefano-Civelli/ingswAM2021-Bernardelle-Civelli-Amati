package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.updatecontainers.TurnState;

/**
 * Interface of the turn manager client side
 */
public interface ClientTurnManagerInterface {
   /**
    * Manages the possible actions based on the current phase
    */
   void currentPhasePrint();

   /**
    * Returns the username of the current player
    * @return the username of the current player
    */
   String getCurrentPlayer();

   /**
    * Sets the String passed as parameter as current player
    * @param currentPlayer, username of the player that will be the current
    */
   void setCurrentPlayer(String currentPlayer);

   /**
    * set new phase and new currentPlayer
    * @param newState the new Turn State
    */
   void setStateIsPlayerChanged(TurnState newState);
}
