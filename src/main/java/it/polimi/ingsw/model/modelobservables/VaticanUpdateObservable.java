package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.VaticanReport;

public interface VaticanUpdateObservable {

   /**
    * notify the model observers that a pope card has been flipped
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyVaticanChange(VaticanReport msg);

}
