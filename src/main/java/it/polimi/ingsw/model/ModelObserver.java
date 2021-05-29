package it.polimi.ingsw.model;


public interface ModelObserver {
  void chestUpdate(String stateUpdate);
  void warehouseUpdate(String stateUpdate);
  void leaderUpdate(String stateUpdate);
  void leaderSetupUpdate(String username, String stateUpdate); // !! non è broadcast !!
  void marketUpdate(String stateUpdate);
  void marketSetupUpdate(String stateUpdate);
  void cardSlotUpdate(String stateUpdate);
  void trackUpdate(String username, String stateUpdate);
  void vaticanUpdate(String username, String stateUpdate);
  void devDeckUpdate(String stateUpdate);
  void devDeckSetup(String stateUpdate);
  void tempChestUpdate(String stateUpdate);
  void discardedLeaderUpdate(String stateUpdate);
  void endGameUpdate(String stateUpdate);
  //TODO per riconnessione servono update con username e Stringa che chiamano singleUpdate
}
