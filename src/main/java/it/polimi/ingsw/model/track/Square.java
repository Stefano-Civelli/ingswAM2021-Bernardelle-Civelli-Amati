package it.polimi.ingsw.model.track;

public class Square {
  private int victoryPoints;
  private boolean red;
  private int active;



  public Square() {
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