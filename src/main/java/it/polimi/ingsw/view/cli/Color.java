package it.polimi.ingsw.view.cli;

public enum Color {

  ANSI_GREY("\u001B[37m"),
  ANSI_RED("\u001B[31m"),
  ANSI_GREEN("\u001B[32m"),
  ANSI_YELLOW("\u001B[33m"),
  ANSI_BLUE("\u001B[34m"),
  ANSI_PURPLE("\u001B[35m"),
  ANSI_BLACK("\u001B[30m"),
  RESET("\u001B[0m");

  private String escape;
  Color(String escape) {
    this.escape = escape;
  }

  public String escape() {
    return escape;
  }
}
