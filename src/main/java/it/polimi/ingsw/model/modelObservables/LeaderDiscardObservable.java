package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.PlayerBoard;

public interface LeaderDiscardObservable {
   void notifyLeaderDiscard(PlayerBoard.LeaderUpdate msg);
}
