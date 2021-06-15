package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.TrackUpdate;

public interface TrackObservable {
   void notifyTrackUpdate(TrackUpdate msg);
}
