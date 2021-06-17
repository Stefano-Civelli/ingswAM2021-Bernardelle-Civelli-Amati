package it.polimi.ingsw.model.updateContainers;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class VaticanReport {
   private final int zone;
   private final boolean active;

   public VaticanReport(int zone, boolean active) {
      this.zone = zone;
      this.active = active;
   }

   /**
    * //TODO
    * @return
    */
   public int getZone() {
      return zone;
   }

   /**
    * //TODO
    * @return
    */
   public boolean isActive() {
      return active;
   }
}
