package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.Server;

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
   public void trackUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.TRACK_UPDATED, stateUpdate));
   }

   @Override
   public void vaticanUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.VATICAN_REPORT, stateUpdate));
   }


   @Override
   public void devDeckUpdate(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DEVELOP_CARD_DECK_UPDATED, stateUpdate));
   }

   @Override
   public void devDeckSetup(String stateUpdate) {
      server.serverBroadcastUpdate(new Message(MessageType.DECK_SETUP, stateUpdate));
   }
}
