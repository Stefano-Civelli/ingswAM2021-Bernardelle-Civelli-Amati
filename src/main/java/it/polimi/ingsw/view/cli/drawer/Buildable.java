package it.polimi.ingsw.view.cli.drawer;

/**
 * Interface that allows an Object to build its Cli representation
 */
public interface Buildable {

  /**
   * Build a Cli representation of the Object in which is implemented
   * @return the representation in matrix of String format
   */
  public String[][] build();
}
