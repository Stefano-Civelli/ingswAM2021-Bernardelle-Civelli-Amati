package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.BuyDevelopCardAction;
import it.polimi.ingsw.controller.action.ShopMarketAction;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.ViewInterface;

import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

public class Cli implements ViewInterface {

  /* ATTRIBUTES */
  private final Client client;
  private static final PrintWriter out = new PrintWriter(System.out, true);
  private static final Scanner in = new Scanner(System.in);
  //private boolean debug = ConfigParameters.DEBUG;
  private boolean debug = false;
  private int numOfPlayers = 0;
  private int playersJoinedTheLobby = 0;
  private int countDown = ConfigParameters.countDown;
  private CliDrawer drawer;

  /**
   * Constructor
   *
   * @param client where the CLI runs
   */
  public Cli(Client client, CliDrawer drawer) {
    this.client = client;
    this.drawer = drawer;
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

    if (ConfigParameters.TESTING) {
      ip = "localhost";
      port = 6754;
      out.println("DEBUG server: ip -> localhost, port -> 6754");
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
    Message loginMessage = new Message(username, MessageType.LOGIN);
    client.setUsername(username);
    client.sendToServer(loginMessage);
  }

    @Override
    public void displayPlayersNumberChoice() {
      out.println("How many people do you want to play with?");
      numOfPlayers = validateIntInput(1, 4);
      Message loginMessage = new Message(client.getUsername(), MessageType.NUMBER_OF_PLAYERS, Integer.toString(numOfPlayers));
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
  public void displayFailedLogin() {
    out.print("Username already taken, Please... ");
  }

  @Override
  public void displayLoginSuccessful() {
    out.println("You have been logged in successfully");
    playersJoinedTheLobby =+ 1;
  }

  @Override
  public void displayLobbyCreated(){
    int remainingPlayersToJoin = numOfPlayers - playersJoinedTheLobby;
    out.println("Lobby created! Waiting for " + remainingPlayersToJoin + " more player(s)...");
  }

  @Override
  public void displayOtherUserJoined(Message msg) {
    if(msg.getPayload().equals("0"))
      out.println("All player joined");
    if(Integer.parseInt(msg.getPayload()) > 0)
      out.println("Waiting for " + msg.getPayload() + " more player(s) ... ");
  }

  public void displayYouJoined(){
    out.println("You have been successfully added to the lobby");
  }

  public void displayWaiting(){
    //int secondsRemaining = this.countDown;
    System.out.println("There's a player creating a lobby, retry to login in a few seconds");
//    Timer myTimer = new Timer();
//    TimerTask countDownTimer = new TimerTask() {
//      @Override
//      public void run() {
//        while (secondsRemaining > 0) {
//          System.out.println("Waiting time: " + secondsRemaining + " second(s) ...");
//          secondsRemaining --;
//        }
//      }
//    };
//
//    myTimer.schedule(countDownTimer, 0, 1*1000);
//
//    if(secondsRemaining == 0)
      //out.println("Countdown terminated, retry to login");
      displayLogin();
  }

  @Override
  public void displayServerDown() {
    out.println("Disconnected");
  }

  @Override
  public void displayGameAlreadyStarted() {
    out.println("ERROR: Game has already started");
  }

  @Override
  public void displayReconnection() {
    out.println("You have been successfully RECONNECTED !");
    // TODO display dello stato aggiornato del gioco
  }

  @Override
  public void displayGameStarted() {

    List<String> otherUsernames = client.usernameList();
    otherUsernames.remove(client.getUsername());
    System.out.println("Game has Started. Your opponents are: ");
    for(String s: otherUsernames)
      System.out.println("-" + s);

    clearScreen();
    drawer.displayDefaultCanvas(client.getUsername());
    waitForInput();
  }

  @Override
  public void displayRecievedLeadercards() {
    client.getSimplePlayerState();
  }

  @Override
  public void displayMarketSetup() {
    System.out.println("That's the market: ");
    drawer.marketDisplay();
  }


  private void waitForInput() {
    Scanner in = new Scanner(System.in);
    Runnable threadInputTerminal = () -> {
      while(true){
        String line = in.nextLine();
        handleInput(line);
      }};
    new Thread(threadInputTerminal).start();
  }

  public void handleInput(String line){
    int id;
    switch (line){
      case "B":
        //TODO fare display del market e del magazzino/chest
        Action buyCardAction = createBuyCardAction();
        client.sendToServer(new Message(client.getUsername(), MessageType.ACTION, buyCardAction));
        break;
      case "M":
        Action marketAction = createMarketAction();
        client.sendToServer(new Message(client.getUsername(), MessageType.ACTION, marketAction));
        break;
      case "P":
        break;
      case "A": //activate leader card
        break;
      case "D": //discard leader card
        break;
    }
  }

  private Action createMarketAction() {
    drawer.marketDisplay();
    System.out.println("Do you want to push a " + Color.ANSI_RED.escape() + "R" + Color.RESET.escape() + "ow or a "
            + Color.ANSI_RED.escape() + "C" + Color.RESET.escape() + "olumn ? ");
    String choice = stringInputValidation(in,"r","c");
    boolean row = choice.equals("r");
    System.out.println("what number ?");
    int index = Integer.parseInt(in.nextLine());
    return new ShopMarketAction(row, index);
  }

  private Action createBuyCardAction() {
    System.out.println("Chose row and column of the card you want to buy separated by new line");
    int row = Integer.parseInt(in.nextLine());
    int column = Integer.parseInt(in.nextLine());
    //TODO display dei card slots con eventualmente sopra le carte
    System.out.println("Chose the card slot in which to place it");
    int cardSlot = Integer.parseInt(in.nextLine());
    return new BuyDevelopCardAction(row, column, cardSlot);
  }


  private static String stringInputValidation(Scanner in, String a, String b) {
    String input;
    input = in.nextLine().toLowerCase();

    while(!input.equals(a) && !input.equals(b)){
      System.out.print("input must be between " + a + " or " + b + ". try again: ");
        input = in.nextLine();
    }
    return input;
  }

  private void clearScreen(){
    drawer.displayPlainCanvas();
    drawer.displayPlainCanvas();
  }
}