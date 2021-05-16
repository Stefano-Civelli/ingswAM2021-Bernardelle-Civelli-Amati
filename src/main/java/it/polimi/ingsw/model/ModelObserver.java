package it.polimi.ingsw.model;

import it.polimi.ingsw.network.messages.Message;

public interface ModelObserver {
  void chestUpdate(String stateUpdate);
  void warehouseUpdate(String stateUpdate);
  void leaderUpdate(String stateUpdate);
  void leaderSetupUpdate(String username, String stateUpdate); // !! non è broadcast !!
  void marketUpdate(String stateUpdate);
  void marketSetupUpdate(String stateUpdate);
  void cardSlotUpdate(String stateUpdate);
  void trackUpdate(String stateUpdate);
  void vaticanUpdate(String stateUpdate);
  void devDeckUpdate(String stateUpdate);
  void devDeckSetup(String stateUpdate);
  void tempChestUpdate(String stateUpdate);
}
