package it.polimi.ingsw.model.track;

public class Square {

  @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
  private int victoryPoints;
  @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
  private boolean red;
  @SuppressWarnings("UnusedDeclaration") // Because the field value is assigned using reflection
  private int active;

  /**
   * constructor of the class
   * instances of this class are made with a json file
   */
  public Square() {
  }

  public Square(int victoryPoints, boolean red, int active){
    this.victoryPoints = victoryPoints;
    this.red = red;
    this.active = active;
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