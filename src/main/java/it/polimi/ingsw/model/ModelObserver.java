package it.polimi.ingsw.model;

import it.polimi.ingsw.network.messages.Message;

public interface ModelObserver {
  void singleUpdate(Message msg);
  void broadcastUpdate(Message msg);

}
