package it.polimi.ingsw.view;

import java.util.List;

public interface ClientStateViewer {
  /**
   * @return this client's simpleplayerstate
   */
  SimplePlayerState getSimplePlayerState();

  /**
   * returns the simpleplayerstate belonging to the specified player
   *
   * @param username the username of the players whose simpleplayerstate is to be returned
   * @return the specified player's simpleplayerstate
   */
  SimplePlayerState getSimplePlayerState(String username);
  SimpleGameState getSimpleGameState();
  List<String> usernameList();
  List<String> otherPlayersUsername();
  int getPlayerTurnPosition();
  String getUsername();

}
