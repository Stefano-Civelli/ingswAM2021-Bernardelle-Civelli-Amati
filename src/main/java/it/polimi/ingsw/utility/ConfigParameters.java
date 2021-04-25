package it.polimi.ingsw.utility;

import java.io.File;
import java.util.Timer;

public class ConfigParameters {
  public static final int SERVER_TIMEOUT = 12; // set me correctly (in seconds!)
  public static final int CLIENT_TIMEOUT = 6; // set me correctly (in seconds!)
  public static final boolean DEBUG = false;
  public static File cardConfigFile = new File("src/DevelopCardConfig.json");
  public static File leaderCardConfigFile = new File("src/LeaderCardConfig.json");
  public static int countDown = 30;
}
