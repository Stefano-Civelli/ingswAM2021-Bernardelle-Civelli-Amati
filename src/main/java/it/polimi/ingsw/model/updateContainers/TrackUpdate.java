package it.polimi.ingsw.model.updateContainers;

public class TrackUpdate {
   private final int playerPosition;

   public TrackUpdate(int playerPosition) {
      this.playerPosition = playerPosition;
   }

   public int getPlayerPosition() {
      return playerPosition;
   }
}
