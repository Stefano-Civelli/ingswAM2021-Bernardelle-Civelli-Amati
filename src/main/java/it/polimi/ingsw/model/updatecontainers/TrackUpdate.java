package it.polimi.ingsw.model.updatecontainers;

/**
 * A model update represents the update of player that changes their position on the {@link it.polimi.ingsw.model.track.Track track}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class TrackUpdate implements ModelUpdate {
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
