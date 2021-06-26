package it.polimi.ingsw.utility;

import it.polimi.ingsw.view.cli.Color;

public final class ConfigParameters {
  public static final int MAX_CONSECUTIVE_MALFORMED_MESSAGE = 2;
  public static final int SERVER_TIMEOUT = 4 * 1000; // seconds
  public static final int CLIENT_TIMEOUT = 2 * 1000; // seconds
  public static final boolean TESTING = true;
  public static final int countDown = 10;
  public static String marbleCharacter = "\u25CF";
  public static String arrowCharacter = (Color.RESET.escape() + "\u25B6");
  public static String squareCharacter = "\u25A0";

  private ConfigParameters() {
    // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
  }

}