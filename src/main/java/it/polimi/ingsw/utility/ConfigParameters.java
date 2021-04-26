package it.polimi.ingsw.utility;

import java.io.File;
import java.util.Timer;

public class ConfigParameters {
  public static final int SERVER_TIMEOUT = 4 * 1000; // seconds
  public static final int CLIENT_TIMEOUT = 2 * 1000; // seconds
  public static final boolean TESTING = false;
  public static File cardConfigFile = new File("src/DevelopCardConfig.json");
  public static File leaderCardConfigFile = new File("src/LeaderCardConfig.json");
  public static int countDown = 10;
}
