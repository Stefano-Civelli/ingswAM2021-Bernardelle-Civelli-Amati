package it.polimi.ingsw.model.updateContainers;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class TrackUpdate {
   private final int playerPosition;

   public TrackUpdate(int playerPosition) {
      this.playerPosition = playerPosition;
   }

   /**
    * returns the current position in the track
    * @return the current position in the track
    */
   public int getPlayerPosition() {
      return playerPosition;
   }
}
