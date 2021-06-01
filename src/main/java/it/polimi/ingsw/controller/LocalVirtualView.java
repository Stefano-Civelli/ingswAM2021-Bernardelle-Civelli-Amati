package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ClientModelUpdaterInterface;


/**
 * to instanciate instead of NetworkViretualView
 */
public class LocalVirtualView implements ModelObserver {
   private ClientModelUpdaterInterface state;
   private String username;

   public LocalVirtualView(ClientModelUpdaterInterface state, String username) {
      this.state = state;
      this.username = username;
   }


   @Override
   public void chestUpdate(String stateUpdate) {

   }

   @Override
   public void warehouseUpdate(String stateUpdate) {
      //client.handleMessage(new Message(username, MessageType.WAREHOUSE_UPDATE, stateUpdate));
      state.warehouseUpdate(username, stateUpdate);
      //   client.getSimplePlayerState().warehouseUpdate(stateUpdate);
   }

   @Override
   public void leaderUpdate(String stateUpdate) {

   }

   @Override
   public void leaderSetupUpdate(String username, String stateUpdate) {

   }

   @Override
   public void marketUpdate(String stateUpdate) {

   }

   @Override
   public void marketSetupUpdate(String stateUpdate) {

   }

   @Override
   public void cardSlotUpdate(String stateUpdate) {

   }

   @Override
   public void trackUpdate(String username, String stateUpdate) {

   }

   @Override
   public void vaticanUpdate(String username, String stateUpdate) {

   }

   @Override
   public void devDeckUpdate(String stateUpdate) {

   }

   @Override
   public void devDeckSetup(String stateUpdate) {

   }

   @Override
   public void tempChestUpdate(String stateUpdate) {

   }

   @Override
   public void discardedLeaderUpdate(String stateUpdate) {

   }

   @Override
   public void endGameUpdate(String stateUpdate) {

   }

   @Override
   public void lorenzoTrackUpdate(String stateUpdate) {

   }

   @Override
   public void lorenzoShuffleUpdate() {

   }

   @Override
   public void lorenzoDevDeckUpdate(String msg) {

   }

   @Override
   public void chestMergeUpdate() {

   }
}
