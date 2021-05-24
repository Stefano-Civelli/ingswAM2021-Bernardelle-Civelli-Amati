package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.ModelObserver;

public interface ModelObservable {

  void notifyModelChange(String msg);

  //void notifyReconnection(String username);

  //void setController(ModelObserver controller);

}
