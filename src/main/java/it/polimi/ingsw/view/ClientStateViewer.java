package it.polimi.ingsw.view;

import java.util.List;

/**
 * Interface that enables querying on client side model state
 */
public interface ClientStateViewer {
  /**
   * Returns this client's SimplePlayerState
   * @return this client's SimplePlayerState
   */
  SimplePlayerState getSimplePlayerState();

  /**
   * Returns the SimplePlayerState belonging to the specified player
   * @param username, the username of the players whose SimplePlayerState is to be returned
   * @return the specified player's SimplePlayerState
   */
  SimplePlayerState getSimplePlayerState(String username);

  /**
   * Returns the SimpleGameState
   * @return the SimpleGameState
   */
  SimpleGameState getSimpleGameState();

  /**
   * Returns the username list of the players in the game
   * @returnthe username list of the players in the game
   */
  List<String> usernameList();

  /**
   * Returns the username list of the players in the game excluded this client's one
   * @return the username list of the players in the game excluded this client's one
   */
  List<String> otherPlayersUsername();

  /**
   * Returns the position that this client has in the turn list
   * @return the position that this client has in the turn list
   */
  int getPlayerTurnPosition();

  /**
   * Returns this client's username
   * @return this client's username
   */
  String getUsername();
}
