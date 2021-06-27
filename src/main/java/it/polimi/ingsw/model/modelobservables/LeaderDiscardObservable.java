package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;

public interface LeaderDiscardObservable {
   void notifyLeaderDiscard(LeaderUpdate msg);
}
