package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.network.messages.Message;

import java.util.List;

public interface ClientModelUpdaterInterface {
  void setClientUsername(String username);
  void chestUpdate(String username, Chest.ChestUpdate stateUpdate);
  void warehouseUpdate(String username, Warehouse.WarehouseUpdate stateUpdate);
  void leaderUpdate(String username, PlayerBoard.LeaderUpdate stateUpdate);
  void leaderSetup(String username, Game.LeaderSetup stateUpdate);
  void marketUpdate(Market.MarketUpdate stateUpdate);
  void marketSetup(Market.MarketSetup stateUpdate);
  void cardSlotUpdate(String username, CardSlots.CardSlotUpdate stateUpdate);
  void trackUpdate(String username, Track.TrackUpdate stateUpdate);
  void vaticanUpdate(String username, Track.VaticanReport stateUpdate);
  void devDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate);
  void devDeckSetup(DevelopCardDeck.DevelopCardDeckSetup stateUpdate);
  void tempChestUpdate(String username, Chest.ChestUpdate stateUpdate);
  void chestMergeUpdate(String username);
  void discardedLeaderUpdate(String username, PlayerBoard.LeaderUpdate stateUpdate);
  void lorenzoTrackUpdate(Track.TrackUpdate stateUpdate);
  void lorenzoShuffleUpdate();
  void lorenzoDevDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate);
  void gameStartedSetup(List<String> stateUpdate);
}
