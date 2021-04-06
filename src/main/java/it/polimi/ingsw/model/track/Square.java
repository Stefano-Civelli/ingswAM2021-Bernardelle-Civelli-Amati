package it.polimi.ingsw.model.track;

public class Square {
  private int victoryPoints;
  private boolean red;
  private int active;

  /**
   * constructor of the class
   * instances of this class are made with a json file
   */
  public Square() {
  }

  /**
   * getter of red
   * @return if the current square is red or isn't
   */
  public boolean getRed(){
    return red;
  }

  /**
   * getter of active
   * @return an active zone value (it could be 0,1,2,3)
   */
  public int getActive(){
    return active;
  }

  /**
   * getter of victoryPoints
   * @return victory point's value related to the current square
   */
  public int getVictoryPoints(){
    return victoryPoints;
  }
}