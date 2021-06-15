package it.polimi.ingsw.model.updateContainers;

public class CardSlotUpdate {
   private final int devCardID;
   private final int slotNumber;

   public CardSlotUpdate(int devCardID, int slotNumber) {
      this.devCardID = devCardID;
      this.slotNumber = slotNumber;
   }

   public int getDevCardID() {
      return devCardID;
   }

   public int getSlotNumber() {
      return slotNumber;
   }
}
