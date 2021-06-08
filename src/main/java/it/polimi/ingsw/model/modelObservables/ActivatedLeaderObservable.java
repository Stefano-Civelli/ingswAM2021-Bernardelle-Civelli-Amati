package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.PlayerBoard;

public interface ActivatedLeaderObservable {
  public void notifyActivatedLeader(PlayerBoard.LeaderUpdate msg);
}
