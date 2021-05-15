package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

public class LocalVirtualView implements ModelObserver {

   private Client client;

   public LocalVirtualView(Client client) {
      this.client = client;
   }


   @Override
   public void chestUpdate(String stateUpdate) {

   }

   @Override
   public void warehouseUpdate(String stateUpdate) {

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
   public void trackUpdate(String stateUpdate) {

   }

   @Override
   public void vaticanUpdate(String stateUpdate) {

   }

   @Override
   public void devDeckUpdate(String stateUpdate) {

   }

   @Override
   public void devDeckSetup(String stateUpdate) {

   }
}
