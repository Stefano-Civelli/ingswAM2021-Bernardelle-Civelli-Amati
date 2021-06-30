package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.TrackUpdate;

public interface TrackObservable {

   /**
    * notify the model observers that Lorenzo moved on the track
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyTrackUpdate(TrackUpdate msg);

}
