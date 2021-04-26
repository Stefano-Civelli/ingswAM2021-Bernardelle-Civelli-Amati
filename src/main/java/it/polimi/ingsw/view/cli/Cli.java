package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.ViewInterface;

import java.io.PrintWriter;
import java.sql.Time;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class Cli implements ViewInterface {

  /* ATTRIBUTES */
  private final Client client;
  private static final PrintWriter out = new PrintWriter(System.out, true);
  private static final Scanner in = new Scanner(System.in);
  //private boolean debug = ConfigParameters.DEBUG;
  private boolean debug = false;
  private int numOfPlayers = 0;
  private int countDown = ConfigParameters.countDown;

  /**
   * Constructor
   *
   * @param client where the CLI runs
   */
  public Cli(Client client) {
    this.client = client;
  }

  private void showTitle() {
    out.println("Welcome to Masters of Renaissance");
  }

  /**
   * when the player joins the game that's the first thing that is displayed (with the CLI)
   */
  @Override
  public void displaySetup() {
    int port;
    String ip;
    showTitle();

    if (debug) {
      ip = "localhost";
      port = 1234;
      out.println("DEBUG server: ip -> localhost, port -> 1234");
    } else {
      out.println("IP address of server?");
      ip = in.nextLine();
      while (!isValidIp(ip)) {
        System.out.println("This is not a valid IPv4 address. Please, try again:");
        ip = in.nextLine();
      }
      //ip = utils.readIp();
      System.out.println("Port number?");
      //port = utils.validateIntInput(TheirClient.MIN_PORT, TheirClient.MAX_PORT);
      port = validateIntInput(Client.MIN_PORT, Client.MAX_PORT);
    }
    client.setServerIP(ip);
    client.setServerPort(port);
    client.connectToServer();
  }

  private static boolean isValidIp(String input) {
    Pattern p = Pattern.compile("^"
            + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
            + "|"
            + "localhost" // localhost
            + "|"
            + "((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?))$"); // Ip

    return p.matcher(input).matches();
  }

  private int validateIntInput(int minValue, int maxValue) {
    int output;
    try {
      output = in.nextInt();
    } catch (InputMismatchException e) {
      output = minValue - 1;
      in.nextLine();
    }
    while (output > maxValue || output < minValue) {
      System.out.println("Value must be between " + minValue + " and " + maxValue + ". Please, try again:");
      try {
        output = in.nextInt();
      } catch (InputMismatchException e) {
        output = minValue - 1;
        in.nextLine();
      }
    }
    in.nextLine();
    return output;
  }

  @Override
  public void displayLogin() {
    String username;

    out.println("Choose your username:");
    username = in.nextLine();
    Message loginMessage = new Message(MessageType.LOGIN, username);
    client.setUsername(username);
    client.sendToServer(loginMessage);
  }
//    if (debug)
//      numOfPlayers = 2;
//        username = new Date().toString();
//        DateFormat dateFormat = new SimpleDateFormat(Configuration.formatDate);
//        try {
//          date = dateFormat.parse(Configuration.minDate);
//        } catch (ParseException e) {
//          e.printStackTrace();
//        }
//      } else {



//        date = utils.readDate("birthdate");
//
    @Override
    public void displayPlayersNumberChoice() {
      out.println("How many people do you want to play with?");
      numOfPlayers = validateIntInput(1, 4); //1 for singlePlayer game -> check when opened in the server

      //meglio metterla quando ricevo il messaggio di avvenuta ricezione e username non already presente
      Message loginMessage = new Message(MessageType.NUMBER_OF_PLAYERS, client.getUsername(), numOfPlayers);
      client.sendToServer(loginMessage);
    }

  @Override
  public void displaySetupFailure() {
    out.println("Can not reach the server, please try again");
    displaySetup();
  }

  /**
   * Method used to show disconnection from server
   */
  @Override
  public void displayDisconnected() {
    out.println("I'm sorry, the connection to the server was lost");
    client.close();
  }

  @Override
  public void displayFailedLogin(Message msg) {
    out.println(msg.getPayload());
  }

  @Override
  public void displayLoginSuccessful() {
    out.println("You have been logged in successfully");
  }

  @Override
  public void displayLobbyCreated(){
    out.println("Lobby created! Waiting for " + (numOfPlayers - 1) + " other player(s)...");
  }

  @Override
  public void displayOtherUserJoined(Message msg) {
    if(msg.getPayload().equals("0"))
      out.println("All player joined, let's play");
    out.println("Waiting for " + msg.getPayload() + " more player(s) ... ");
  }

  public void displayYouJoined(){
    out.println("You have been successfully added to the lobby");
  }

  public void displayWaiting(){
    final int[] secondsRemaining = {this.countDown};
    System.out.println("There is a player creating a lobby, try to login in a few seconds");
    Timer myTimer = new Timer();
    TimerTask countDownTimer = new TimerTask() {
      @Override
      public void run() {
          System.out.println("Waiting time: " + secondsRemaining[0] + " second(s) ...");
      }
    };

    while(secondsRemaining[0] > 0){
      myTimer.scheduleAtFixedRate(countDownTimer, 0, 1000);
      secondsRemaining[0] -= 1;
    }

    displayLogin();
  }
}