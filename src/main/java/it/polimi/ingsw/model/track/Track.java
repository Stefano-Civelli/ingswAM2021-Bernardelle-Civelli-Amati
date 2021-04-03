package it.polimi.ingsw.model.track;

public class Track extends LorenzoTrack implements VaticanReportObserver {
  private final int[] popeCards = new int[]{-1, -1, -1};

  public Track() {
  }

  /**
   * Calculate track's points
   * @return total score of the track
   */
  public int calculateTrackScore(){
    int points = 0;
    //total score is obtained by summing the popecards that the player has flipped...
    for(int x : popeCards)
      if(x != -1)
        points += x;
      else
        points += 0;
    //...and the victorypoints of the square he is in
    points += track[playerPosition].getVictoryPoints();
    return points;
  }

  /**
   * Let the player move his faith marker along the track
   * Every time a player steps on a red Square the track's observers are notified
   * When a player reaches the end (Square 24) the observer that manage the end game is notified
   * @param faith number of steps to do on the track from the current position
   */
  public void moveForward(int faith){
    for(int i=0; i<faith; i++){
      if(playerPosition < 24) {

        playerPosition += 1;
        if(track[playerPosition].getRed()) {
          int active = track[playerPosition].getActive()-1;
          //if the popeCard value related to that red Square is -1 then i'm the first that has reached it
          if(popeCards[active] == -1) {
            //set the popeCard of this activeZone to his actual value
            switchPopeCardsActivation(active);

            //notify the observers that have to see if they can "flip the popeCard related to that activeZone"
            notifyForVaticanReport(active);
          }
        }
      }

      if(playerPosition == 24)
        //if a player reaches the end of the track he notifies the observer of the endGame
        notifyForEndGame();
    }
  }

  /**
   * Set the popeCards value
   * @param active indicate which is the activeZone i should be in to flip the popeCard
   */
  public void checkIfCurrentPositionIsActive(int active) {
    if (popeCards[active] == -1) {
      if (track[playerPosition].getActive() == active + 1) {
        switchPopeCardsActivation(active);
      } else
        popeCards[active] = 0;
    }
  }

  /**
   * Convert an activeZone onto the value that the popeCard related to it has
   * @param active indicate which is the activeZone i'm in
   */
  private void switchPopeCardsActivation(int active){
    switch (active){
      case 0:
        popeCards[active] = 2;
        break;
      case 1:
        popeCards[active] = 3;
        break;
      case 2:
        popeCards[active] = 4;
        break;
    }
  }

  @Override
  public void update(int active) {
    this.checkIfCurrentPositionIsActive(active);
  }
}