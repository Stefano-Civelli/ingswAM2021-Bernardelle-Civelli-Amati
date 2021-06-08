package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.track.Track;

public interface TrackObservable {
   void notifyTrackUpdate(Track.TrackUpdate msg);
}
