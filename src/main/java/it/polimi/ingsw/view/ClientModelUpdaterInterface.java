package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.Message;

public interface ClientModelUpdaterInterface {
  void setClientUsername(String username);
  void chestUpdate(String username, String stateUpdate);
  void warehouseUpdate(String username, String stateUpdate);
  void leaderUpdate(String username, String stateUpdate);
  void leaderSetup(String username, String stateUpdate);
  void marketUpdate(String stateUpdate);
  void marketSetup(String stateUpdate);
  void cardSlotUpdate(String username, String stateUpdate);
  void trackUpdate(String username, String stateUpdate);
  void vaticanUpdate(String username, String stateUpdate);
  void devDeckUpdate(String stateUpdate);
  void devDeckSetup(String stateUpdate);
  void tempChestUpdate(String username, String stateUpdate);
  void discardedLeaderUpdate(String username, String stateUpdate);
  void lorenzoTrackUpdate(String stateUpdate);
  void lorenzoShuffleUpdate();
  void lorenzoDevDeckUpdate(String stateUpdate);
  void gameStartedSetup(Message msg);
}
