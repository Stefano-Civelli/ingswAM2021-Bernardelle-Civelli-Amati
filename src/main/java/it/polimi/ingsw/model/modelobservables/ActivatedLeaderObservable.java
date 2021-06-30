package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;

public interface ActivatedLeaderObservable {

  void notifyActivatedLeader(LeaderUpdate msg);

}
