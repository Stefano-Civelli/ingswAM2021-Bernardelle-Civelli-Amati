package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.TrackUpdate;

public interface TrackObservable {

   void notifyTrackUpdate(TrackUpdate msg);

}
