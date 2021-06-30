package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;


public interface ActivatedLeaderObservable {

  /**
   * notify the model observers that a leader card has been activated.
   * Along with the notification sends the proper update Object as further explained in project documentation
   */
  void notifyActivatedLeader(LeaderUpdate msg);

}
