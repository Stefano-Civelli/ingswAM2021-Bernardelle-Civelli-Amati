package it.polimi.ingsw.model;

import it.polimi.ingsw.network.messages.Message;

public interface ModelObserver {
  public void singleUpdate(Message msg);
  public void broadcastUpdate(Message msg);
}
