package it.polimi.ingsw.model.updateContainers;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class LeaderUpdate {
   private final int cardId;

   public LeaderUpdate(int cardId) {
      this.cardId = cardId;
   }

   /**
    * returns the ID of the card that has been activated or discarded (these 2 cases are distinguished by message type)
    * @return the ID of the card that has been activated or discarded
    */
   public int getCardId() {
      return cardId;
   }
}
