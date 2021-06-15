package it.polimi.ingsw.view;

import it.polimi.ingsw.model.updateContainers.*;

import java.util.List;

public interface ClientModelUpdaterInterface {
  void setClientUsername(String username);
  void chestUpdate(String username, ChestUpdate stateUpdate);
  void warehouseUpdate(String username, WarehouseUpdate stateUpdate);
  void leaderUpdate(String username, LeaderUpdate stateUpdate);
  void leaderSetup(String username, LeaderSetup stateUpdate);
  void marketUpdate(MarketUpdate stateUpdate);
  void marketSetup(MarketSetup stateUpdate);
  void cardSlotUpdate(String username, CardSlotUpdate stateUpdate);
  void trackUpdate(String username, TrackUpdate stateUpdate);
  void vaticanUpdate(String username, VaticanReport stateUpdate);
  void devDeckUpdate(DevelopCardDeckUpdate stateUpdate);
  void devDeckSetup(DevelopCardDeckSetup stateUpdate);
  void tempChestUpdate(String username, ChestUpdate stateUpdate);
  void chestMergeUpdate(String username);
  void discardedLeaderUpdate(String username, LeaderUpdate stateUpdate);
  void lorenzoTrackUpdate(TrackUpdate stateUpdate);
  void lorenzoShuffleUpdate();
  void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate);
  void gameStartedSetup(List<String> stateUpdate);
}
