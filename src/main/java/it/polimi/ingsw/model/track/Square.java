package it.polimi.ingsw.model.track;

public class Square {
  private final int victoryPoints;
  private boolean red;
  private final int active;



  public Square(int vp, boolean red, int active) {
    this.victoryPoints = vp;
    this.red = red;
    this.active = active;
  }

  public boolean getRed(){
    return red;
  }

  public int getActive(){
    return active;
  }

  public int getVictoryPoints(){
    return victoryPoints;
  }
}