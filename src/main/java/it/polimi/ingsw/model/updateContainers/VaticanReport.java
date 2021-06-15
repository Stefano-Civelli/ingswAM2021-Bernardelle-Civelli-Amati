package it.polimi.ingsw.model.updateContainers;

public class VaticanReport {
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
