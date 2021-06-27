package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;

public interface ActivatedLeaderObservable {
  public void notifyActivatedLeader(LeaderUpdate msg);
}
