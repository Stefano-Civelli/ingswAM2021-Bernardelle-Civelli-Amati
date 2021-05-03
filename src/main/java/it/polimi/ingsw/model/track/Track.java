package it.polimi.ingsw.model.track;

public class Track extends LorenzoTrack implements VaticanReportObserver {
  private final int[] popeCards = new int[]{-1, -1, -1};

  /**
   * constructor of the class
   * instances of this class are made with a json file
   */
  public Track() {
  }

  /**
   * Calculate track's points
   * @return total score of the track
   */
  public int calculateTrackScore(){
    int points = 0;
    //total score is obtained by summing the pope cards that the player has flipped...
    for(int x : popeCards)
      if(x != -1)
        points += x;
      else
        points += 0;
    //...and the victory points of the square he is in
    points += track[playerPosition].getVictoryPoints();
    return points;
  }

  /**
   * Let the player move his faith marker along the track
   * Every time a player steps on a red Square the track's observers are notified
   * When a player reaches the end (Square 24) the observer that manage the end game is notified
   * @param faith number of steps to do on the track from the current positiondata:image/pjpeg;base64,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
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
   * Method called by update() of VaticanReportObserver interface
   * once it's called it check if the player is into the active zone that it receives from the update
   * Set the correct value in popeCards array, depending on active and position of the player
   * @param active indicate which is the activeZone i should be in to flip the popeCard related to that zone
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

  public Integer getCurrentPosition(){
    return playerPosition;
  }

  @Override
  public void update(int active) {
    this.checkIfCurrentPositionIsActive(active);
  }

  @Override
  public void addToVaticanReportObserverList(VaticanReportObserver observerToAdd) {
    if(vaticanReportObserverList.add(observerToAdd))
      observerToAdd.addToVaticanReportObserverList(this);
  }
}