package it.polimi.ingsw.utility;

import it.polimi.ingsw.view.cli.Color;

import java.io.File;

public final class ConfigParameters {

  public static final int SERVER_TIMEOUT = 4 * 1000; // seconds
  public static final int CLIENT_TIMEOUT = 2 * 1000; // seconds
  public static final boolean TESTING = true;
  public static final File cardConfigFile = new File("src/main/resources/configfiles/DevelopCardConfig.json");
  //public static final File leaderCardConfigFile = new File("src/main/resources/configfiles/LeaderCardConfig.json");
  //public static final File leaderCardConfigFile = new File("src/main/resources/configfiles/LeaderCardConfig0Requirements.json");
  public static final File leaderCardConfigFile;
  public static final File trackConfigFile = new File("src/main/resources/configfiles/SquareConfig.json");
  public static final File lorenzoTrackConfigFile = ConfigParameters.trackConfigFile; // it may be different
  public static final int countDown = 10;
  public static String marbleCharacter = "\u25CF";
  public static String arrowCharacter = (Color.RESET.escape() + "\u25B6");
  public static String squareCharacter = "\u25A0";

  static {
    if(TESTING)
      leaderCardConfigFile = new File("src/main/resources/configfiles/LeaderCardConfig0Requirements.json");
    else
      leaderCardConfigFile = new File("src/main/resources/configfiles/LeaderCardConfig.json");
  }


  private ConfigParameters() {
    // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
  }
}
