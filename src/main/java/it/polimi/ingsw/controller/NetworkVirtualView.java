package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;

import java.security.MessageDigestSpi;

public class NetworkVirtualView implements ModelObserver {

   public Server server;


   public NetworkVirtualView(Server server) {
      this.server = server;
   }


   @Override
   public void chestUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.CHEST_UPDATE, stateUpdate));
   }

   @Override
   public void warehouseUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.WAREHOUSE_UPDATE, stateUpdate));
   }

   @Override
   public void leaderUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.ACTIVATED_LEADERCARD_UPDATE, stateUpdate));
   }

   @Override
   public void marketUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.MARKET_UPDATED, stateUpdate));
   }

   @Override
   public void marketSetupUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.MARKET_SETUP, stateUpdate));
   }

   @Override
   public void leaderSetupUpdate(String username, String stateUpdate) {
      server.serverSingleUpdate(new Message(username, MessageType.LEADERCARD_SETUP, stateUpdate));
   }

   @Override
   public void cardSlotUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.CARD_SLOT_UPDATE, stateUpdate));
   }

   @Override
   public void trackUpdate(String username, String stateUpdate) {
      server.serverBroadcastUpdate(new Message(username, MessageType.TRACK_UPDATED, stateUpdate));
   }

   @Override
   public void vaticanUpdate(String username, String stateUpdate) {
      server.serverBroadcastUpdate(new Message(username, MessageType.VATICAN_REPORT, stateUpdate));
   }


   @Override
   public void devDeckUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DEVELOP_CARD_DECK_UPDATED, stateUpdate));
   }

   @Override
   public void devDeckSetup(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DECK_SETUP, stateUpdate));
   }

   @Override
   public void tempChestUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.TEMP_CHEST_UPDATE, stateUpdate));
   }

   @Override
   public void discardedLeaderUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DISCARDED_LEADERCARD, stateUpdate));
   }

   @Override
   public void endGameUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.GAME_ENDED, stateUpdate));
   }

   @Override
   public void lorenzoTrackUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.LORENZO_TRACK_UPDATE, stateUpdate));
   }

   @Override
   public void lorenzoShuffleUpdate() {
      server.serverBroadcastUpdate(new Message(MessageType.LORENZO_SHUFFLE_UPDATE));
   }

   @Override
   public void lorenzoDevDeckUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.LORENZO_DECK_UPDATE, stateUpdate));
   }

}
