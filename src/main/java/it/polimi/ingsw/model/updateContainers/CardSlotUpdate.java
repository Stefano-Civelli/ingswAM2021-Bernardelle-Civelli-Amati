package it.polimi.ingsw.model.updateContainers;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class CardSlotUpdate {
   private final int devCardID;
   private final int slotNumber;

   public CardSlotUpdate(int devCardID, int slotNumber) {
      this.devCardID = devCardID;
      this.slotNumber = slotNumber;
   }

   /**
    * returns ID of the card to be placed in the slot
    * @returnID of the card to be placed in the slot
    */
   public int getDevCardID() {
      return devCardID;
   }

   /**
    * returns the slot number in which to put the card
    * @return the slot number in which to put the card
    */
   public int getSlotNumber() {
      return slotNumber;
   }
}
