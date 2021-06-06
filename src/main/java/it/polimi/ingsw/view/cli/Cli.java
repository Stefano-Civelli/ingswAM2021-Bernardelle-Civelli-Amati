package it.polimi.ingsw.view.cli;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.*;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.CliTurnManager;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.ClientStateViewer;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

public class Cli implements ViewInterface {

  /* ATTRIBUTES */
  private final ClientStateViewer stateViewer;
  private final CliDrawer drawer;
  private CliTurnManager cliTurnManager;
  private final Client client;

  private static final PrintWriter out = new PrintWriter(System.out, true);
  private static final Scanner in = new Scanner(System.in);
  //private boolean debug = ConfigParameters.DEBUG;
  private boolean debug = false;
  private int numOfPlayers = 0;
  private int playersJoinedTheLobby = 0;
  private int countDown = ConfigParameters.countDown;


  /**
   * Constructor
   *
   * @param stateViewer where the CLI runs
   */
  public Cli(ClientStateViewer stateViewer, CliDrawer drawer, Client client) {
    this.stateViewer = stateViewer;
    this.drawer = drawer;
    this.client = client;
  }

  private void showTitle() {
    out.println("Welcome to Masters of Renaissance");
  }

  /**
   * when the player joins the game that's the first thing that is displayed (with the CLI)
   */
  @Override
  public void displayNetworkSetup() {
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
      System.out.println("Port number?");
      port = validateIntInput(Client.MIN_PORT, Client.MAX_PORT);
      in.nextLine();
    }
    client.connectToServer(ip, port);
  }

  @Override
  public void setClientTurnManager(ClientTurnManagerInterface cliTurnManager) {
    this.cliTurnManager = (CliTurnManager) cliTurnManager;
  }

  @Override
  public void displayLogin() {
    String username;
    out.println("Choose your username:");
    username = in.nextLine();
    client.setUsername(username);
    client.sendLogin();
  }

    @Override
    public void displayPlayersNumberChoice() {
      out.println("How many people do you want to play with?");
      numOfPlayers = validateIntInput(1, 4);
      Message loginMessage = new Message(client.getUsername(), MessageType.NUMBER_OF_PLAYERS, Integer.toString(numOfPlayers));
      client.forwardMessage(loginMessage);
    }

  @Override
  public void displaySetupFailure() {
    out.println("Can not reach the server, please try again");
    displayNetworkSetup();
  }

  /**
   * Method used to show disconnection from server
   */
  @Override
  public void displayDisconnected() {
    out.println("I'm sorry, the connection to the server was lost");
    out.println(Color.ANSI_RED.escape() + "quit" + Color.RESET.escape() + " to close the App");
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
  public void displayLobbyCreated() {
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

  public void displayMarbleShopping(){
    for(MarbleColor m : stateViewer.getSimpleGameState().getTempMarble())
      System.out.print(m.toString() + " ");
    System.out.println();
    System.out.print("index of the marble to insert: ");
  }

  @Override
  public void displayServerDown() {
    for(int i=0; i<5; i++)
      System.out.println();
    out.println(Color.ANSI_RED.escape() + "Server has crashed, you will be diconnected." + Color.RESET.escape());
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

    List<String> otherUsernames = stateViewer.usernameList();
    otherUsernames.remove(stateViewer.getUsername());
    System.out.println("Game has Started. Your opponents are: ");
    for(String s: otherUsernames)
      System.out.println("-" + s);

    //clearScreen();
    //drawer.displayDefaultCanvas(client.getUsername());
    waitForInput();
  }

  @Override
  public void displayRecievedLeadercards() {
    stateViewer.getSimplePlayerState();
  }

  @Override
  public void displayMarket() {
    System.out.println("That's the market: ");
    drawer.marketDisplay();
  }

  //TODO
  @Override
  public void displayPlayerTurn(String player) {
    System.out.println("It's " + cliTurnManager.getCurrentPlayer() + "'s turn.");
  }

  @Override
  public void displayYourTurn(String username) {
    clearScreen();
    System.out.println("It's your turn.");
  }

  @Override
  public void displayDefaultCanvas(String username) {
    drawer.displayDefaultCanvas(stateViewer.getUsername());
  }

  @Override
  public void displayGameEnded(String payload) {
    Type token = new TypeToken<Pair<String, Integer>>(){}.getType();
    Pair<String, Integer> winnerAndScore = GSON.getGsonBuilder().fromJson(payload, token);
    String winner = winnerAndScore.getKey();
    int score = winnerAndScore.getValue();

    System.out.println("\n\n\nGame has ended.");
    if(!winner.equals("")) {
      System.out.println("The winner is... " + Color.ANSI_RED.escape() + winner.toUpperCase() + Color.RESET.escape());
    }
    else{
      System.out.println("Sorry mate, you have lost. You can't beat the CPU power but ... This time you have scored: " + score + " points");
      System.out.println("Congrats");
    }

    out.println(Color.ANSI_RED.escape() + "quit" + Color.RESET.escape() + " to close the App");
  }

  @Override
  public void displayPlainCanvas() {
    clearScreen();
  }

  @Override
  public void startingSetupUpdate() {}

  @Override
  public void displayLorenzoDiscarded(String state) {
    clearScreen();
    drawer.displayLorenzoHasDiscarded(state);
  }

  @Override
  public void displayLorenzoMoved() {
    clearScreen();
    drawer.displayLorenzoHasMoved();
  }

  @Override
  public void displayLorenzoShuffled() {
    clearScreen();
    drawer.displayLorenzoHasShuffled();
  }

  private void waitForInput() {

    Scanner in = new Scanner(System.in);
    Runnable threadInputTerminal = () -> {
      boolean actionAlreadyPerformed;
      while (true) {
        String line = in.nextLine();
        actionAlreadyPerformed = false;

        //this cases can be performed every moment of the game
        switch(line) {
          case "cheat":
            if (ConfigParameters.TESTING) {
              client.forwardMessage(new Message(stateViewer.getUsername(), MessageType.CHEAT));
              System.out.println("cheats activated");
              actionAlreadyPerformed = true;
              try {
                Thread.sleep(100);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
            break;
          case "quit":
            client.forwardMessage(new Message(stateViewer.getUsername(), MessageType.QUIT));
            client.close();
            actionAlreadyPerformed = true;
            break;
          case "print":
            System.out.println("That's your opponents: ");
            for (String s : stateViewer.otherPlayersUsername())
              System.out.println(" -" + s);

            String user;
            do {
              System.out.println("Which playerBoard do you want to look at?");
              user = in.nextLine();
            } while (!stateViewer.otherPlayersUsername().contains(user) && !user.equals("all"));

            if (user.equals("all")) {
              for (String s : stateViewer.otherPlayersUsername())
                drawer.displayDefaultCanvas(s);
            }
            else
              drawer.displayDefaultCanvas(user);
            actionAlreadyPerformed = true;
            break;
        }


        if (!stateViewer.getUsername().equals(cliTurnManager.getCurrentPlayer()))
          System.out.println("It's " + cliTurnManager.getCurrentPlayer() + "'s turn. Wait yours ...");
        else {
          if(!actionAlreadyPerformed) {
            switch (cliTurnManager.getCurrentPhase()) {
              case SETUP_DISCARDING_LEADERS:
                createInitialDiscardLeaderAction(line);
                break;
              case SETUP_CHOOSING_RESOURCES:
                createChooseResourcesAction(line);
                break;
              case SHOPPING:
                Action insertMarbleAction = createInsertMarbleAction(line);
                client.forwardAction(insertMarbleAction);
                break;
              case SHOPPING_LEADER:
                int index = validateIntInput(line, 1, 2);
                int leaderId = stateViewer.getSimplePlayerState().getActiveLeaders().get(index-1);
                client.forwardAction(new ChooseLeaderOnWhiteMarbleAction(leaderId));
                break;
              default:
                if (cliTurnManager.isValidInCurrenPhase(line)) //to see if the input is valid in this turnPhase
                  handleInput(line);
                else {
                  System.out.println("Command you gave me is not allowed in this phase of the game or doesn't exists");
                  cliTurnManager.currentPhasePrint();
                }
            }//switch
          }
          else
            cliTurnManager.currentPhasePrint();
        }
      }//while
    };
    Thread thread = new Thread(threadInputTerminal);
    thread.setName("inputReader");
    thread.start();
  }

  private Action createInsertMarbleAction(String line) {
    int maxValue = stateViewer.getSimpleGameState().getTempMarble().size();
    int marbleIndex = validateIntInput(line, 1, maxValue);
    stateViewer.getSimpleGameState().removeTempMarble(marbleIndex);
    return new InsertMarbleAction(marbleIndex-1);
  }

  private void handleInput(String line){
    switch (line) {
      case "B": case "b":
        //TODO fare display del market e del magazzino/chest
        drawer.drawDevelopCardDeck();
        Action buyCardAction = createBuyCardAction();
        client.forwardAction(buyCardAction);
        break;
      case "S": case "s":
        Action marketAction = createMarketAction();
        client.forwardAction(marketAction);
        break;
      case "P": case "p":
        Action produceAction = createProduceAction();
        client.forwardAction(produceAction);
        break;
      case "L": case "l"://leaderProduce

        break;
      case "A": case "a"://activate leader card
        Action activateLeaderAction = createActivateLeaderAction();
        client.forwardAction(activateLeaderAction);
        break;
      case "D": case "d"://discard leader card
        Action discardLeaderAction = createDiscardLeaderAction();
        client.forwardAction(discardLeaderAction);
        break;
      case "E": case "e"://end turn
        client.forwardAction(new EndTurnAction(stateViewer.getUsername()));
        break;
      default:
        System.out.println("Command you gave me is not allowed in this phase of the game");
        cliTurnManager.currentPhasePrint();
    }
  }

  private Action createActivateLeaderAction() {
    int activateLeaderIndex;
    System.out.println("That's your leader card hand: ");
    drawer.displayLeaderHand(stateViewer.getUsername());
    System.out.println("Which do you want to activate?");
    activateLeaderIndex = validateIntInput(1, stateViewer.getSimplePlayerState().getNotActiveLeaderCards().size());
    int id = stateViewer.getSimplePlayerState().getNotActiveLeaderCards().get(activateLeaderIndex-1);
    return new ActivateLeaderAction(id);
  }

  private Action createDiscardLeaderAction() {
    int discardLeaderIndex;
    System.out.println("That's your leader card hand: ");
    drawer.displayLeaderHand(stateViewer.getUsername());
    System.out.println("Which do you want to discard?");
    discardLeaderIndex = validateIntInput(1, stateViewer.getSimplePlayerState().getNotActiveLeaderCards().size());
    int id = stateViewer.getSimplePlayerState().getNotActiveLeaderCards().get(discardLeaderIndex-1);
    return new DiscardLeaderAction(id);
  }

  private void createChooseResourcesAction(String line) {
    int i = stateViewer.getPlayerTurnPosition()/2;
    Map<ResourceType, Integer> resources = new HashMap<>();
    int resource = validateIntInput(line, 1, 4);
    while(i>0) {
      if(stateViewer.getPlayerTurnPosition()==4 && i==1)
        resource = validateIntInput(1, 4);
      if(resources.containsKey(parsIntToResource(resource)))
        resources.compute(parsIntToResource(resource), (k,v) -> (v==null) ? 1 : v + 1);
      else
        resources.put(parsIntToResource(resource), 1);
      i--;
    }
    client.forwardAction(new ChooseInitialResourcesAction(resources));
  }

  private ResourceType parsIntToResource(int value) {
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

  private ResourceType parsStringToResource(String value) {
    switch(value){
      case "B": case "b":
        return ResourceType.SHIELD;
      case "P": case "p":
        return ResourceType.SERVANT;
      case "Y": case "y":
        return ResourceType.GOLD;
      case "G": case "g":
        return ResourceType.STONE;
      default: return null;
    }
  }

  private void createInitialDiscardLeaderAction(String line) {
    int firstDiscard = validateIntInput(line, 1, stateViewer.getSimplePlayerState().getNotActiveLeaderCards().size());
    stateViewer.getSimplePlayerState().discardLeader(firstDiscard - 1);
    drawer.displayLeaderHand(stateViewer.getUsername());
    int secondDiscard = validateIntInput(1, stateViewer.getSimplePlayerState().getNotActiveLeaderCards().size());
    client.forwardAction(new DiscardInitialLeaderAction(stateViewer.getUsername(), firstDiscard-1, secondDiscard-1));
    stateViewer.getSimplePlayerState().discardLeader(secondDiscard - 1);
  }

  public void displayLeaderHand() {
    drawer.displayLeaderHand(stateViewer.getUsername());
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
    stateViewer.getSimpleGameState().setTempMarble(row, index);
    return new ShopMarketAction(row, index - 1);
  }

  private Action createBuyCardAction() {
    System.out.println("Chose row and column of the card you want to buy separated by new line");
    int row = validateIntInput(1,3);
    int column = validateIntInput(1,4);
    //TODO display dei card slots con eventualmente sopra le carte
    System.out.println("Chose the card slot in which to place it");
    int cardSlot = validateIntInput(1,3);
    return new BuyDevelopCardAction(row-1, column-1, cardSlot-1);
  }

  private Action createProduceAction(){
    System.out.println("Choose a Card you want to activate production on. (0 for base Production)");
//    drawer.displayProducibleCards();
    int index = validateIntInput(0, 5);
    if(index == 0) {
      String input;
      ResourceType output1, output2, output3;
      do {
        in.nextLine();
        System.out.println("Choose the first resource to consume (first character of the resource color)");
        input = in.nextLine();
        output1 = parsStringToResource(input);
      }while(output1 == null);

      do {
        System.out.println("Choose the second resource to consume (first character of the resource color)");
        input = in.nextLine();
        output2 = parsStringToResource(input);
      }while(output2 == null);

      do {
        System.out.println("choose the resource to produce (first character of the resource color)");
        input = in.nextLine();
        output3 = parsStringToResource(input);
      }while(output3 == null);
      return new BaseProductionAction(output1, output2, output3);
    }
    else if(index<4)
      return new ProductionAction(index-1);
    else {
      ResourceType output;
      System.out.println("choose the resource to produce (first character of the resource color)");
      in.nextLine();
      String line = in.nextLine();
      output = parsStringToResource(line);
      return new LeaderProductionAction(index - 4, output);
    }
  }


  private static String stringInputValidation(Scanner in, String a, String b) {
    String input;
    in.nextLine();
    input = in.nextLine().toLowerCase();

    while(!input.equals(a) && !input.equals(b)){
      System.out.print("input must be " + a + " or " + b + ". try again: ");
        input = in.nextLine();
    }
    return input;
  }

  private void clearScreen(){
    drawer.displayPlainCanvas();
    drawer.displayPlainCanvas();
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

//  private ResourceType validateInputForResources(Map<ResourceType, Integer> validResources) {
//    String input;
//
//    input = in.nextLine();
//    ResourceType resource = parsStringToResource(input.toUpperCase());
//    while(resource == null || !validResources.containsKey(resource)) {
//      System.out.println("Input you gave me is not valid, choose the resource to throw from the following");
//      drawer.drawTotalResourcesChoice(client.getUsername());
//      input = in.nextLine();
//      resource = parsStringToResource(input.toUpperCase());
//    }
//
//    return resource;
//  }

  private int validateIntInput(String line, int minValue, int maxValue) {
    int output;
    try {
      output = Integer.parseInt(line);
    } catch (NumberFormatException e) {
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
}