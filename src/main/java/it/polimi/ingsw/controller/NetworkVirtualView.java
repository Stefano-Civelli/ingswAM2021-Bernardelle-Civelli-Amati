package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.updateContainers.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;

public class NetworkVirtualView implements ModelObserver {

   public Server server;


   public NetworkVirtualView(Server server) {
      this.server = server;
   }


   @Override
   public void chestUpdate(ChestUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.CHEST_UPDATE, stateUpdate));
   }

   @Override
   public void warehouseUpdate(WarehouseUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.WAREHOUSE_UPDATE, stateUpdate));
   }

   @Override
   public void leaderUpdate(LeaderUpdate stateUpdate) { //when a leadercard gets activated
      server.serverBroadcastUpdate(new Message(MessageType.ACTIVATED_LEADERCARD_UPDATE, stateUpdate));
   }

   @Override
   public void marketUpdate(MarketUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.MARKET_UPDATED, stateUpdate));
   }

   @Override
   public void marketSetupUpdate(MarketSetup stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.MARKET_SETUP, stateUpdate));
   }

   @Override
   public void leaderSetupUpdate(String username, LeaderSetup stateUpdate) {
      server.serverSingleUpdate(new Message(username, MessageType.LEADERCARD_SETUP, stateUpdate));
   }

   @Override
   public void cardSlotUpdate(CardSlotUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.CARD_SLOT_UPDATE, stateUpdate));
   }

   @Override
   public void trackUpdate(String username, TrackUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(username, MessageType.TRACK_UPDATED, stateUpdate));
   }

   @Override
   public void vaticanUpdate(String username, VaticanReport stateUpdate) {
      server.serverBroadcastUpdate(new Message(username, MessageType.VATICAN_REPORT, stateUpdate));
   }


   @Override
   public void devDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DEVELOP_CARD_DECK_UPDATED, stateUpdate));
   }

   @Override
   public void devDeckSetup(DevelopCardDeckSetup stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DECK_SETUP, stateUpdate));
   }

   @Override
   public void tempChestUpdate(ChestUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.TEMP_CHEST_UPDATE, stateUpdate));
   }

   @Override
   public void discardedLeaderUpdate(LeaderUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DISCARDED_LEADERCARD, stateUpdate));
   }

   @Override
   public void endGameUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.GAME_ENDED, stateUpdate));
   }

   @Override
   public void lorenzoTrackUpdate(TrackUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.LORENZO_TRACK_UPDATE, stateUpdate));
   }

   @Override
   public void lorenzoShuffleUpdate() {
      server.serverBroadcastUpdate(new Message(MessageType.LORENZO_SHUFFLE_UPDATE));
   }

   @Override
   public void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.LORENZO_DECK_UPDATE, stateUpdate));
   }


   @Override
   public void chestMergeUpdate() {
      server.serverBroadcastUpdate(new Message(MessageType.CHEST_MERGED));
   }

}
