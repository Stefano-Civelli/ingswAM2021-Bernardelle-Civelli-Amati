package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.cli.drawer.SimpleModelObservable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientVirtualView implements ModelObserver, SimpleModelObservable {
   private Client client;

   public ClientVirtualView(Client client) {
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
}
