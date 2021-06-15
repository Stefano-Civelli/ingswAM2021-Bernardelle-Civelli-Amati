package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.updateContainers.VaticanReport;

public interface VaticanUpdateObservable {

   void notifyVaticanChange(VaticanReport msg);

}
