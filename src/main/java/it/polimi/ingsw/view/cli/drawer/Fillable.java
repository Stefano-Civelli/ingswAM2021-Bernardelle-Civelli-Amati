package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

/**
 * Interface that allows an Object to fill its Cli representation
 */
public interface Fillable {

  /**
   * Fill the Cli representation of the Object passed as parameter with its current state
   * Used to fill Objects related to the player board
   * @param fillMe, the representation of the Object who needs to be filled
   * @param playerState, state of the player
   */
  void fill(String[][] fillMe, SimplePlayerState playerState);

  /**
   * Fill the Cli representation of the Object passed as parameter with its current state
   * Used to fill Objects related to the game
   * @param fillMe, the representation of the Object who needs to be filled
   * @param gameState, state of the game
   */
  void fill(String[][] fillMe, SimpleGameState gameState);

}
