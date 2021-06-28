package it.polimi.ingsw.view.cli;

/**
 * Enumeration of the colors used in Cli
 */
public enum Color {
  ANSI_GREY("\u001B[37m"),
  ANSI_RED("\u001B[31m"),
  ANSI_GREEN("\u001B[32m"),
  ANSI_YELLOW("\u001B[33m"),
  ANSI_BLUE("\u001B[34m"),
  ANSI_PURPLE("\u001B[35m"),
  ANSI_BLACK("\u001B[30m"),
  ANSI_BRIGHT_YELLOW("\u001B[93m"),
  RESET("\u001B[0m");

  private String escape;
  Color(String escape) {
    this.escape = escape;
  }

  /**
   * Returns the String associated to the enumeration value
   * @return the String associated to the enumeration value
   */
  public String escape() {
    return escape;
  }
}
