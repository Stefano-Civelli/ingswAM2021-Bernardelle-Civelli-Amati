package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.network.messages.Message;

public interface ModelObservable {
  void notifyModelChange(String msg);
}
