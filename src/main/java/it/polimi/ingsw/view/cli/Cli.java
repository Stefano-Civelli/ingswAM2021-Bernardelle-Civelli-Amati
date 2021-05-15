package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardDeck;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientTurnManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
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
  private ClientTurnManager clientTurnManager;

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
  }

  public void setClientTurnManager(ClientTurnManager clientTurnManager) {
    this.clientTurnManager = clientTurnManager;
  }

  @Override
  public void displayLogin() {
    String username;

    out.println("Choose your username:");
    username = in.nextLine();
    Message loginMessage = new Message(username, MessageType.LOGIN);
    client.setUsername(username);
    client.sendMessage(loginMessage);
  }

    @Override
    public void displayPlayersNumberChoice() {
      out.println("How many people do you want to play with?");
      numOfPlayers = validateIntInput(1, 4);
      Message loginMessage = new Message(client.getUsername(), MessageType.NUMBER_OF_PLAYERS, Integer.toString(numOfPlayers));
      client.sendMessage(loginMessage);
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

  public void displayMarbleChoice() {
    drawer.displayResourcesChoice();
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

    //clearScreen();
    //drawer.displayDefaultCanvas(client.getUsername());
    waitForInput();
  }

  @Override
  public void displayRecievedLeadercards() {
    client.getSimplePlayerState();
  }

  @Override
  public void displayMarket() {
    System.out.println("That's the market: ");
    drawer.marketDisplay();
  }

  //TODO
  @Override
  public void displayPlayerTurn(String player) {
    System.out.println("It's " + clientTurnManager.getCurrentPlayer() + "'s turn.");
  }

  @Override
  public void displayYourTurn(String username) {
    System.out.println("It's your turn.");
  }

  private void waitForInput() {

    Scanner in = new Scanner(System.in);
    Runnable threadInputTerminal = () -> {
      while (true) {
        String line = in.nextLine();
        if(clientTurnManager.getCurrentPhase().isSetup() && client.getUsername().equals(clientTurnManager.getCurrentPlayer())) {
          switch (clientTurnManager.getCurrentPhase()) {
            case SETUP_DISCARDING_LEADERS:
              createInitialDiscardLeaderAction(line);
              break;
            case SETUP_CHOOSING_RESOURCES:
              createChooseResourcesAction(line);
              break;
          }
        }
        else {
          if (!client.getUsername().equals(clientTurnManager.getCurrentPlayer()))
            System.out.println("It's " + clientTurnManager.getCurrentPlayer() + "'s turn. Wait yours ...");
          else {
            if (clientTurnManager.validateInput(line))
              handleInput(line);
            else {
              System.out.println("Command you gave me is not allowed in this phase of the game or doesn't exists");
              clientTurnManager.handleNextPossiblePhases();
            }
          }
        }
      }
    };
    new Thread(threadInputTerminal).start();
  }

  private void handleInput(String line){
    drawer.displayDefaultCanvas(client.getUsername());
    switch (line.toUpperCase().charAt(0)) {
      case 'B':
        //TODO fare display del market e del magazzino/chest
        Action buyCardAction = createBuyCardAction();
        client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, buyCardAction));
        break;
      case 'S':
        Action marketAction = createMarketAction();
        client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, marketAction));
        break;
      case 'P':
        Action produceAction = createProduceAction();
        while(produceAction == null)
          produceAction = createProduceAction();
        client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, produceAction));
        break;
      case 'A': //activate leader card
        Action activateLeaderAction = createActivateLeaderAction();
        client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, activateLeaderAction));
        break;
      case 'D': //discard leader card
        Action discardLeaderAction = createDiscardLeaderAction();
        client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, discardLeaderAction));
        break;
      default:
        System.out.println("Command you gave me is not allowed in this phase of the game");
        clientTurnManager.handleNextPossiblePhases();
    }
  }

  private Action createActivateLeaderAction() {
    int activateLeaderIndex;
    System.out.println("That's your leader card hand: ");
    drawer.displayLeaderHand(client.getUsername());
    System.out.println("Which do you want to activate?");
    activateLeaderIndex = validateIntInput(1, client.getSimplePlayerState().getLeaderCards().size());
    return new DiscardLeaderAction(activateLeaderIndex);
  }

  private Action createDiscardLeaderAction() {
    int discardLeaderIndex;
    System.out.println("That's your leader card hand: ");
    drawer.displayLeaderHand(client.getUsername());
    System.out.println("Which do you want to discard?");
    discardLeaderIndex = validateIntInput(1, client.getSimplePlayerState().getLeaderCards().size());
    return new DiscardLeaderAction(discardLeaderIndex);
  }

  private void createChooseResourcesAction(String line) {
    int i = client.getPlayerTurnPosition()/2;
    Map<ResourceType, Integer> resources = new HashMap<>();

    while(i>0) {
      int resource = validateIntInput(Integer.parseInt(line), 1, 4);
      if(resources.containsKey(parsIntToResource(resource)))
        resources.compute(parsIntToResource(resource), (k,v) -> (v==null) ? 1 : v + 1);
      else
        resources.put(parsIntToResource(resource), 1);
      i--;
    }
    client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, new ChooseInitialResourcesAction(resources)));
  }

  private ResourceType parsIntToResource(int value){
    switch(value){
      case 1:
        return ResourceType.SHIELD;
      case 2:
        return ResourceType.SERVANT;
      case 3:
        return ResourceType.GOLD;
      case 4:
        return ResourceType.STONE;
      default: return null;
    }
  }

  private ResourceType parsStringToResource(String value){
    switch(value){
      case "B":
        return ResourceType.SHIELD;
      case "P":
        return ResourceType.SERVANT;
      case "Y":
        return ResourceType.GOLD;
      case "G":
        return ResourceType.STONE;
      default: return null;
    }
  }

  private void createInitialDiscardLeaderAction(String line) {
    int firstDiscard = validateIntInput(Integer.parseInt(line), 1, client.getSimplePlayerState().getLeaderCards().size());
    client.getSimplePlayerState().discardLeader(firstDiscard);
    drawer.displayLeaderHand(client.getUsername());
    int secondDiscard = validateIntInput(1, client.getSimplePlayerState().getLeaderCards().size());
    client.sendMessage(new Message(client.getUsername(), MessageType.ACTION, new DiscardInitialLeaderAction(client.getUsername(), firstDiscard-1, secondDiscard-1)));
    client.getSimplePlayerState().discardLeader(secondDiscard);
  }

  public void displayLeaderHand() {
    drawer.displayLeaderHand(client.getUsername());
  }

  private Action createMarketAction() {
    drawer.marketDisplay();
    System.out.println("Do you want to push a " + Color.ANSI_RED.escape() + "R" + Color.RESET.escape() + "ow or a "
            + Color.ANSI_RED.escape() + "C" + Color.RESET.escape() + "olumn ? ");
    String choice = stringInputValidation(in,"r","c");
    boolean row = choice.equals("r");
    System.out.println("what number ?");
    int index;
    if(row)
      index = validateIntInput(1,3);
    else
      index = validateIntInput(1,4);
    return new ShopMarketAction(row, index - 1);
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

  private Action createProduceAction(){
    System.out.println("Choose a develop card you want to activate production on.");
    int index = validateIntInput(0, 3);
    if(index == 0 && client.getSimplePlayerState().isBaseProductionActivatable()) {
      ResourceType consumed1, consumed2;
      int produced;
      Map<ResourceType, Integer> throwableResources = client.getSimplePlayerState().throwableResources();

      System.out.println("This are the resources u can exchange:");
      drawer.drawTotalResourcesChoice(client.getUsername());
      System.out.println("Choose the first resource");
      //validazione
      consumed1 = validateInputForResources(throwableResources);
      throwableResources.put(consumed1, throwableResources.get(consumed1) == 1 ?
              throwableResources.remove(consumed1) : throwableResources.get(consumed1)-1);
      System.out.println("Choose the second resource");
      //validazione
      consumed2 = validateInputForResources(throwableResources);

      System.out.println("choose the resource to produce");
      drawer.displayResourcesChoice();
      produced = validateIntInput(0, 4);
      return new BaseProductionAction(consumed1, consumed2, parsIntToResource(produced));
    }
    else if(index>0)
      return new ProductionAction(index);
    else
      System.out.println("you don't have enough exchangeable resources to activate base production");
    return null;
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

  public static DevelopCard getDevelopCardFromId(int cardId) throws InvalidCardException {
    DevelopCardDeck developCardDeck = null;
    try {
      FileInputStream inputStream = new FileInputStream(ConfigParameters.cardConfigFile);
      InputStreamReader reader = new InputStreamReader(inputStream);
      developCardDeck = GSON.getGsonBuilder().fromJson(reader, DevelopCardDeck.class);
      reader.close();
    }catch (IOException e){}
    return developCardDeck.getCardFromId(cardId);
  }

  public static LeaderCard getLeaderCardFromId(int cardId) throws InvalidCardException {
    LeaderCardDeck leaderCardDeck = null;
    try {
      FileInputStream inputStream = new FileInputStream(ConfigParameters.cardConfigFile);
      InputStreamReader reader = new InputStreamReader(inputStream);
      leaderCardDeck = GSON.getGsonBuilder().fromJson(reader, LeaderCardDeck.class);
      reader.close();
    }catch (IOException e){}
    return leaderCardDeck.getCardFromId(cardId);
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
    //in.nextLine();
    return output;
  }

  private ResourceType validateInputForResources(Map<ResourceType, Integer> validResources) {
    String input;

    input = in.nextLine();
    ResourceType resource = parsStringToResource(input.toUpperCase());
    while(resource == null || !validResources.containsKey(resource)) {
      System.out.println("Input you gave me is not valid, choose the resource to throw from the following");
      drawer.drawTotalResourcesChoice(client.getUsername());
      input = in.nextLine();
      resource = parsStringToResource(input.toUpperCase());
    }

    return resource;
  }

  private int validateIntInput(int line, int minValue, int maxValue) {
    int output = line;
    while (output > maxValue || output < minValue) {
      System.out.println("Value must be between " + minValue + " and " + maxValue + ". Please, try again:");
      try {
        output = in.nextInt();
      } catch (InputMismatchException e) {
        output = minValue - 1;
        in.nextLine();
      }
    }
    //in.nextLine();
    return output;
  }

}