package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.view.ClientModelUpdaterInterface;
import it.polimi.ingsw.view.LorenzoViewInterface;


/**
 * A model observer that calls method directly on ClientModelUpdaterInterface when an update is received: used in local game
 * (to instantiate instead of NetworkVirtualView)
 */
public class LocalVirtualView implements ModelObserver {

   private final ClientModelUpdaterInterface state;
   private final String username;
   private final LorenzoViewInterface view;

   public LocalVirtualView(ClientModelUpdaterInterface state, String username, LorenzoViewInterface view) {
      this.state = state;
      this.username = username;
      this.view = view;
   }


   @Override
   public void chestUpdate(ChestUpdate stateUpdate) {
      state.chestUpdate(username, stateUpdate);
   }

   @Override
   public void warehouseUpdate(WarehouseUpdate stateUpdate) {
      state.warehouseUpdate(username, stateUpdate);
   }

   @Override
   public void leaderUpdate(LeaderUpdate stateUpdate) {
      state.leaderUpdate(username, stateUpdate);
   }

   @Override
   public void leaderSetupUpdate(String username, LeaderSetup stateUpdate) {
      state.leaderSetup(username, stateUpdate);
   }

   @Override
   public void marketUpdate(MarketUpdate stateUpdate) {
      state.marketUpdate(stateUpdate);
   }

   @Override
   public void marketSetupUpdate(MarketSetup stateUpdate) {
      state.marketSetup(stateUpdate);
   }

   @Override
   public void cardSlotUpdate(CardSlotUpdate stateUpdate) {
      state.cardSlotUpdate(username, stateUpdate);
   }

   @Override
   public void trackUpdate(String username, TrackUpdate stateUpdate) {
      state.trackUpdate(username, stateUpdate);
   }

   @Override
   public void vaticanUpdate(String username, VaticanReport stateUpdate) {
      state.vaticanUpdate(username, stateUpdate);
   }

   @Override
   public void devDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      state.devDeckUpdate(stateUpdate);
   }

   @Override
   public void devDeckSetup(DevelopCardDeckSetup stateUpdate) {
      state.devDeckSetup(stateUpdate);
   }

   @Override
   public void tempChestUpdate(ChestUpdate stateUpdate) {
      state.tempChestUpdate(username, stateUpdate);
   }

   @Override
   public void discardedLeaderUpdate(LeaderUpdate stateUpdate) {
      state.discardedLeaderUpdate(username, stateUpdate);
   }

   @Override
   public void endGameUpdate(String stateUpdate) {
      view.displayGameEnded(stateUpdate);
   }

   @Override
   public void lorenzoTrackUpdate(TrackUpdate stateUpdate) {
      state.lorenzoTrackUpdate(stateUpdate);
      view.displayLorenzoMoved();
   }

   @Override
   public void lorenzoShuffleUpdate() {
      state.lorenzoShuffleUpdate();
      view.displayLorenzoShuffled();
   }

   @Override
   public void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      state.lorenzoDevDeckUpdate(stateUpdate);
      view.displayLorenzoDiscarded(stateUpdate);
   }

   @Override
   public void chestMergeUpdate() {
      state.chestMergeUpdate(username);
   }
}
