package it.polimi.ingsw.view;

import java.util.List;

public interface ClientStateViewer {
  /**
   * @return this client's simpleplayerstate
   */
  public SimplePlayerState getSimplePlayerState();

  /**
   * returns the simpleplayerstate belonging to the specified player
   *
   * @param username the username of the players whose simpleplayerstate is to be returned
   * @return the specified player's simpleplayerstate
   */
  public SimplePlayerState getSimplePlayerState(String username);
  public SimpleGameState getSimpleGameState();
  public List<String> usernameList();
  public List<String> otherPlayersUsername();
  public int getPlayerTurnPosition();
  public String getUsername();
}
