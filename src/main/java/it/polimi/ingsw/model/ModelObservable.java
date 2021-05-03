package it.polimi.ingsw.model;

import it.polimi.ingsw.network.messages.Message;

public interface ModelObservable {
  void notifyModelChange(Message msg);
}
