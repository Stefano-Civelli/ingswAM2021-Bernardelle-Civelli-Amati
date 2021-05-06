package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelObserver;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;

public class Controller implements ModelObserver {

   public Server server;


   public Controller(Server server) {
      this.server = server;
   }


   @Override
   public void singleUpdate(Message msg) {
      server.serverSingleUpdate(msg);
   }

   @Override
   public void broadcastUpdate(Message msg) {
      server.serverBroadcastUpdate(msg);
   }

}
