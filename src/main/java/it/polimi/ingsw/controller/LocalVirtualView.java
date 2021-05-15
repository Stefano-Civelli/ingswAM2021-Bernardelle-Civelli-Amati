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
   public void singleUpdate(Message msg) {
      client.handleMessage(msg);
   }

   @Override
   public void broadcastUpdate(Message msg) {
      client.handleMessage(msg);
      //devo mandare qualcosa a lorenzo? altrimenti fa esattamente lo stesso di singleUpdate
   }


}
