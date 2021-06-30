package it.polimi.ingsw.model.updatecontainers;

/**
 * A model update represents an update happened in a {@link it.polimi.ingsw.model.CardSlots card slot}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class CardSlotUpdate implements ModelUpdate {

   private final int devCardID;
   private final int slotNumber;

   public CardSlotUpdate(int devCardID, int slotNumber) {
      this.devCardID = devCardID;
      this.slotNumber = slotNumber;
   }

   /**
    * returns ID of the card to be placed in the slot
    * @return ID of the card to be placed in the slot
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
