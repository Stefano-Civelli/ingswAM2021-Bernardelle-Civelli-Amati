package it.polimi.ingsw.model.updateContainers;

/**
 * A model update represents an update concerning a {@link it.polimi.ingsw.model.leadercard.LeaderCard leader card}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class LeaderUpdate implements ModelUpdate {
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
