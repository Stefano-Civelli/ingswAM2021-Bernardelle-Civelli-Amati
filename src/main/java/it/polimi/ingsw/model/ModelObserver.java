package it.polimi.ingsw.model;


import it.polimi.ingsw.model.updatecontainers.*;

/**
 * Defines all the required method of class used to observe the model
 */
public interface ModelObserver {
  void chestUpdate(ChestUpdate stateUpdate);
  void warehouseUpdate(WarehouseUpdate stateUpdate);
  void leaderUpdate(LeaderUpdate stateUpdate);
  void leaderSetupUpdate(String username, LeaderSetup stateUpdate); // this update isn't sent broadcast
  void marketUpdate(MarketUpdate stateUpdate);
  void marketSetupUpdate(MarketSetup stateUpdate);
  void cardSlotUpdate(CardSlotUpdate stateUpdate);
  void trackUpdate(String username, TrackUpdate stateUpdate);
  void vaticanUpdate(String username, VaticanReport stateUpdate);
  void devDeckUpdate(DevelopCardDeckUpdate stateUpdate);
  void devDeckSetup(DevelopCardDeckSetup stateUpdate);
  void tempChestUpdate(ChestUpdate stateUpdate);
  void discardedLeaderUpdate(LeaderUpdate stateUpdate);
  void endGameUpdate(String stateUpdate);
  void lorenzoTrackUpdate(TrackUpdate stateUpdate);
  void lorenzoShuffleUpdate();
  void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate);
  void chestMergeUpdate();
}
