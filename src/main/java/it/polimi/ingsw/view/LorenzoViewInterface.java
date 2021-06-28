package it.polimi.ingsw.view;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

/**
 * Interface containing the possible displays that can be done after Lorenzo plays
 */
public interface LorenzoViewInterface {
  /**
   * Displays that Lorenzo's last move hase been a discard
   * @param state, update content
   */
  void displayLorenzoDiscarded(DevelopCardDeckUpdate state);

  /**
   * Displays that Lorenzo has moved his faith mark
   */
  void displayLorenzoMoved();

  /**
   * Displays that Lorenzo's last move hase been a shuffle
   */
  void displayLorenzoShuffled();

  /**
   * Displays that the game has ended
   * @param payload, contains the winner of the game and its score
   */
  void displayGameEnded(String payload);
}