package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;

public interface ActivatedLeaderObservable {

  /**
   * Notify the model observers that a leader card has been activated.
   * Along with the notification sends the proper update Object as further explained in project documentation
   *
   * @param msg the update object representing a leader card update
   */
  void notifyActivatedLeader(LeaderUpdate msg);

}
