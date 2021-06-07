package it.polimi.ingsw.model;


import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.track.Track;

public interface ModelObserver {
  void chestUpdate(Chest.ChestUpdate stateUpdate);
  void warehouseUpdate(Warehouse.WarehouseUpdate stateUpdate);
  void leaderUpdate(PlayerBoard.LeaderUpdate stateUpdate);
  void leaderSetupUpdate(String username, Game.LeaderSetup stateUpdate); // !! non è broadcast !!
  void marketUpdate(Market.MarketUpdate stateUpdate);
  void marketSetupUpdate(Market.MarketSetup stateUpdate);
  void cardSlotUpdate(CardSlots.CardSlotUpdate stateUpdate);
  void trackUpdate(String username, Track.TrackUpdate stateUpdate);
  void vaticanUpdate(String username, Track.VaticanReport stateUpdate);
  void devDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate);
  void devDeckSetup(DevelopCardDeck.DevelopCardDeckSetup stateUpdate);
  void tempChestUpdate(Chest.ChestUpdate stateUpdate);
  void discardedLeaderUpdate(PlayerBoard.LeaderUpdate stateUpdate);
  void endGameUpdate(String stateUpdate);
  void lorenzoTrackUpdate(Track.TrackUpdate stateUpdate);
  void lorenzoShuffleUpdate();
  void lorenzoDevDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate);
  void chestMergeUpdate();
  //TODO per riconnessione servono update con username e Stringa che chiamano singleUpdate
}
