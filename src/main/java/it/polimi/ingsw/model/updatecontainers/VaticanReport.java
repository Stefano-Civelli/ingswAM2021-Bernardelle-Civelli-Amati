package it.polimi.ingsw.model.updatecontainers;

/**
 * A model update represents the update of player that reaches a vatican report cell on the {@link it.polimi.ingsw.model.track.Track track}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class VaticanReport implements ModelUpdate {

   private final int zone;
   private final boolean active;

   public VaticanReport(int zone, boolean active) {
      this.zone = zone;
      this.active = active;
   }

   public int getZone() {
      return zone;
   }

   public boolean isActive() {
      return active;
   }

}
