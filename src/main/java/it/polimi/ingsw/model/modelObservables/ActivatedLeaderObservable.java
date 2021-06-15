package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.LeaderUpdate;

public interface ActivatedLeaderObservable {
  public void notifyActivatedLeader(LeaderUpdate msg);
}
