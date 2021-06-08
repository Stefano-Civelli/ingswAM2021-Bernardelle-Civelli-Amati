package it.polimi.ingsw.controller;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.ClientModelUpdaterInterface;
import it.polimi.ingsw.view.LorenzoViewInterface;

import java.lang.reflect.Type;


/**
 * to instanciate instead of NetworkViretualView
 * calls method directly on ClientModelUpdaterInterface
 */
public class LocalVirtualView implements ModelObserver {
   private ClientModelUpdaterInterface state;
   private String username;
   private LorenzoViewInterface view;

   public LocalVirtualView(ClientModelUpdaterInterface state, String username, LorenzoViewInterface view) {
      this.state = state;
      this.username = username;
      this.view = view;
   }


   @Override
   public void chestUpdate(Chest.ChestUpdate stateUpdate) {
      state.chestUpdate(username, stateUpdate);
   }

   @Override
   public void warehouseUpdate(Warehouse.WarehouseUpdate stateUpdate) {
      state.warehouseUpdate(username, stateUpdate);
   }

   @Override
   public void leaderUpdate(PlayerBoard.LeaderUpdate stateUpdate) {
      state.leaderUpdate(username, stateUpdate);
   }

   @Override
   public void leaderSetupUpdate(String username, Game.LeaderSetup stateUpdate) {
      state.leaderSetup(username, stateUpdate);
   }

   @Override
   public void marketUpdate(Market.MarketUpdate stateUpdate) {
      state.marketUpdate(stateUpdate);
   }

   @Override
   public void marketSetupUpdate(Market.MarketSetup stateUpdate) {
      state.marketSetup(stateUpdate);
   }

   @Override
   public void cardSlotUpdate(CardSlots.CardSlotUpdate stateUpdate) {
      state.cardSlotUpdate(username, stateUpdate);
   }

   @Override
   public void trackUpdate(String username, Track.TrackUpdate stateUpdate) {
      state.trackUpdate(username, stateUpdate);
   }

   @Override
   public void vaticanUpdate(String username, Track.VaticanReport stateUpdate) {
      state.vaticanUpdate(username, stateUpdate);
   }

   @Override
   public void devDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate) {
      state.devDeckUpdate(stateUpdate);
   }

   @Override
   public void devDeckSetup(DevelopCardDeck.DevelopCardDeckSetup stateUpdate) {
      state.devDeckSetup(stateUpdate);
   }

   @Override
   public void tempChestUpdate(Chest.ChestUpdate stateUpdate) {
      state.tempChestUpdate(username, stateUpdate);
   }

   @Override
   public void discardedLeaderUpdate(PlayerBoard.LeaderUpdate stateUpdate) {
      state.discardedLeaderUpdate(username, stateUpdate);
   }

   @Override
   public void endGameUpdate(String stateUpdate) {
      view.displayGameEnded(stateUpdate);
   }

   @Override
   public void lorenzoTrackUpdate(Track.TrackUpdate stateUpdate) {
      state.lorenzoTrackUpdate(stateUpdate);
      view.displayLorenzoMoved();
   }

   @Override
   public void lorenzoShuffleUpdate() {
      state.lorenzoShuffleUpdate();
      view.displayLorenzoShuffled();
   }

   @Override
   public void lorenzoDevDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate) {
      state.devDeckUpdate(stateUpdate);
      view.displayLorenzoDiscarded(stateUpdate);
   }

   @Override
   public void chestMergeUpdate() {
      state.chestMergeUpdate(username);
   }
}
