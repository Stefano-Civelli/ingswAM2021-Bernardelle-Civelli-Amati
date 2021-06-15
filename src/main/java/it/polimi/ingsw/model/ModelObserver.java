package it.polimi.ingsw.model;


import it.polimi.ingsw.model.updateContainers.*;

public interface ModelObserver {
  void chestUpdate(ChestUpdate stateUpdate);
  void warehouseUpdate(WarehouseUpdate stateUpdate);
  void leaderUpdate(LeaderUpdate stateUpdate);
  void leaderSetupUpdate(String username, LeaderSetup stateUpdate); // !! non è broadcast !!
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
  //TODO per riconnessione servono phaseUpdate con username e Stringa che chiamano singleUpdate
}
