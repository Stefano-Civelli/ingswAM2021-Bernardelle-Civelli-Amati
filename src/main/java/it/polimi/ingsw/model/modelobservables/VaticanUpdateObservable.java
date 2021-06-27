package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.VaticanReport;

public interface VaticanUpdateObservable {

   void notifyVaticanChange(VaticanReport msg);

}
