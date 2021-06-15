package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.LeaderUpdate;

public interface LeaderDiscardObservable {
   void notifyLeaderDiscard(LeaderUpdate msg);
}
