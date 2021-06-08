package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.track.Track;

public interface VaticanUpdateObservable {

   void notifyVaticanChange(Track.VaticanReport msg);

}
