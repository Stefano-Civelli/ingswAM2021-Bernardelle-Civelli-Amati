package it.polimi.ingsw.utility;

import java.io.File;

public final class ConfigParameters {

  public static final int SERVER_TIMEOUT = 4 * 1000; // seconds
  public static final int CLIENT_TIMEOUT = 2 * 1000; // seconds
  public static final boolean TESTING = true;
  public static final File cardConfigFile = new File("src/DevelopCardConfig.json");
  public static final File leaderCardConfigFile = new File("src/LeaderCardConfig.json");
  public static final File trackConfigFile = new File("src/SquareConfig.json");
  public static final File lorenzoTrackConfigFile = ConfigParameters.trackConfigFile; // it may be different
  public static final int countDown = 10;

  private ConfigParameters() {
    // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
  }

}
