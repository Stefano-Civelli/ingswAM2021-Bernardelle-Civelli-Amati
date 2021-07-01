package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.ClientStateViewer;
import it.polimi.ingsw.view.cli.drawer.*;

import java.util.Arrays;

/**
 * Class that manage the Cli representation of the game state
 */
public class CliDrawer {

  private final int PLAYERBOARD_LENGTH = 90;
  private final int PLAYERBOARD_HEIGHT = 16;
  private final int MAX_DISPLAYABLE_LENGTH = 200;
  private final int MAX_DISPLAYABLE_HEIGHT = 20;

  private final String[][] canvas;
  private String[][] market;
  private String[][] warehouse;
  private String[][] track;
  private String[][] chest;
  private String[][] slots;
  private String[][] deck;
  private String[][] leaders;
  private String[][] activatedLeaders;
  private final String[][] lorenzo = lorenzoDrawer.build();
  private final ClientStateViewer state;

  private static final CardSlotsDrawer slotsDrawer = new CardSlotsDrawer();
  private static final ChestDrawer chestDrawer = new ChestDrawer();
  private static final DeckDrawer deckDrawer = new DeckDrawer();
  private static final MarketDrawer marketDrawer = new MarketDrawer();
  private static final TrackDrawer trackDrawer = new TrackDrawer();
  private static final WarehouseDrawer warehouseDrawer = new WarehouseDrawer();
  private static final LeaderHandDrawer leaderHandDrawer = new LeaderHandDrawer();
  private static final ActivatedLeaderDrawer activatedLeaderDrawer = new ActivatedLeaderDrawer();
  private static final LorenzoDrawer lorenzoDrawer = new LorenzoDrawer();

  /**
   * Constructor for CliDrawer class
   * @param state, contains the state of the game
   */
  public CliDrawer(ClientStateViewer state) {
    this.state = state;
    this.canvas = new String[MAX_DISPLAYABLE_HEIGHT][MAX_DISPLAYABLE_LENGTH];

    for (int i = 0; i < MAX_DISPLAYABLE_HEIGHT; i++)
      for (int j = 0; j < MAX_DISPLAYABLE_LENGTH; j++)
        canvas[i][j] = " ";
  }

  /**
   * Prints a plain canvas
   */
  public void displayPlainCanvas() {
    clearCanvas();
    placeHereOnCanvas(0, 0, canvas);
    displayCanvas();
    displayCanvas();
  }

  /**
   * Builds, fills and prints the default representation of the game.
   * Default representation includes the Market, the DevelopCard Deck and the PlayerBoard of the player which as
   * the username that is passed as parameter
   * @param username, username of the player
   */
  public void displayDefaultCanvas(String username) {
    clearCanvas();
    placeHereOnCanvas(0, 0, MarginConstructor.buildMargins(PLAYERBOARD_HEIGHT, PLAYERBOARD_LENGTH));
    setUsernameOnCanvas(username);

    deck = deckDrawer.build();
    market = marketDrawer.build();
    chest = chestDrawer.build();
    slots = slotsDrawer.build();
    warehouse = warehouseDrawer.build();
    track = trackDrawer.build();
    leaders = leaderHandDrawer.build();
    activatedLeaders = activatedLeaderDrawer.build();

    deckDrawer.fill(deck, state.getSimpleGameState());
    marketDrawer.fill(market, state.getSimpleGameState());
    chestDrawer.fill(chest, state.getSimplePlayerState(username));
    trackDrawer.fill(track, state.getSimplePlayerState(username));
    warehouseDrawer.fill(warehouse, state.getSimplePlayerState(username));
    slotsDrawer.fill(slots, state.getSimplePlayerState(username));
    leaderHandDrawer.fill(leaders, state.getSimplePlayerState(username));
    activatedLeaderDrawer.fill(activatedLeaders, state.getSimplePlayerState(username));

    placeHereOnCanvas(1, 3, track);
    placeHereOnCanvas(0, PLAYERBOARD_LENGTH + 4, market);
    placeHereOnCanvas(0, PLAYERBOARD_LENGTH + 21, deck);
    placeHereOnCanvas(5, 3, warehouse);
    placeHereOnCanvas(10, 3, chest);
    placeHereOnCanvas(5, 20, slots);
    placeHereOnCanvas(PLAYERBOARD_HEIGHT, 0, leaders);
    placeHereOnCanvas(5, 75, activatedLeaders);
    displayCanvas();
  }

  /**
   * Builds, fills and prints the Market
   */
  public void marketDisplay() {
    market = marketDrawer.build();
    marketDrawer.fill(market, state.getSimpleGameState());

    for (int i = 1; i < market.length; i++) {
      for (int j = 0; j < market[i].length; j++)
        System.out.print(market[i][j]);
      System.out.println();
    }
  }

  /**
   * Builds and prints the leader hand of the player with the username passed as parameter
   * @param username, username of the player
   */
  public void displayLeaderHand(String username) {
    String[][] leaderHand = new String[5][state.getSimplePlayerState(username).getNotActiveLeaderCards().size() * 11];
    int a = 0, b = 0, d = 5, index = 1;

    for (int i = 0; i < leaderHand[0].length; i++)
      leaderHand[4][i] = " ";

    for (Integer id : state.getSimplePlayerState(username).getNotActiveLeaderCards()) {
      String[][] leader = LeaderConstructor.constructLeaderFromId(id);
      for (int i = 0; i < leader.length; i++, a++)
        for (int j = 0, c = b; j < leader[0].length; j++, c++)
          leaderHand[a][c] = leader[i][j];

      leaderHand[4][d] = Integer.toString(index);

      index++;
      d += 11;
      a = 0;
      b = b + 11;
    }

    for (int i = 0; i < leaderHand.length; i++) {
      for (int j = 0; j < leaderHand[0].length; j++)
        System.out.print(leaderHand[i][j]);
      System.out.println();
    }
  }

  /**
   * Builds and prints the active leaders of the player with the username passed as parameter
   * @param username, username of the player
   */
  public void displayActiveLeaders(String username) {
    String[][] activated = new String[5][state.getSimplePlayerState(username).getActiveLeaderCards().size() * 11];
    int a = 0, b = 0, d = 5, index = 1;

    for (int i = 0; i < activated[0].length; i++)
      activated[4][i] = " ";

    for (Integer id : state.getSimplePlayerState(username).getActiveLeaderCards()) {
      String[][] leader = LeaderConstructor.constructLeaderFromId(id);
      for (int i = 0; i < leader.length; i++, a++)
        for (int j = 0, c = b; j < leader[0].length; j++, c++)
          activated[a][c] = leader[i][j];

      activated[4][d] = Integer.toString(index);

      index++;
      d += 11;
      a = 0;
      b = b + 11;
    }

    for (int i = 0; i < activated.length; i++) {
      for (int j = 0; j < activated[0].length; j++)
        System.out.print(activated[i][j]);
      System.out.println();
    }
  }


//  public void displayProducibleCards() {
//    List<Integer> producibleLeader = state.getSimplePlayerState().getProducibleLeaders();
//    List<Integer> producibleSlots = state.getSimplePlayerState().getProducibleCardSlotsId();
//    int productionLength = producibleLeader.size() + producibleLeader.size();
//    int c=0, b, p=1;
//    String[][] produce = new String[5][productionLength*11];
//
//    for (int i = 0; i < produce.length; i++)
//      for (int j = 0; j < produce[i].length; j++)
//        produce[i][j] = " ";
//
//    for (c = 0, b=5; c < productionLength; c++, b+=11, p++)
//        produce[4][b] = Integer.toString(p);
//
//    c=0;
//
//    for(Integer id : producibleSlots) {
//      String[][] card = DevelopCardConstructor.constructDevelopFromId(id);
//      for(int r=0; r<card.length; r++) {
//        for (int col=0, e=c; col < card[r].length; e++, col++)
//          produce[r][e] = card[r][col];
//      }
//      c+=11;
//    }
//
//    for(Integer id : producibleLeader) {
//      String[][] card = LeaderConstructor.constructLeaderFromId(id);
//      for(int r=0; r<card.length; r++) {
//          for (int col=0, e=c; col < card[r].length; e++, col++)
//            produce[r][e] = card[r][col];
//        }
//        c+=11;
//    }
//
//    if(productionLength>0)
//      for (int i = 0; i < produce.length; i++) {
//        for (int j = 0; j < produce[i].length; j++)
//          System.out.print(produce[i][j]);
//        System.out.println();
//      }
//  }

  /**
   * Prints the four marbles associated to resources
   */
  public void displayResourcesChoice() {
    String[][] marbles = new String[2][8];

    for (int i = 0; i < marbles.length; i++)
      for (int j = 0; j < marbles[i].length; j++)
        marbles[i][j] = " ";

    marbles[0][0] = MarbleColor.BLUE.toString();
    marbles[0][2] = MarbleColor.PURPLE.toString();
    marbles[0][4] = MarbleColor.YELLOW.toString();
    marbles[0][6] = MarbleColor.GREY.toString();

    int a = 1;
    for (int c = 0; c < marbles[0].length; c++)
      if (c % 2 == 0) {
        marbles[1][c] = Integer.toString(a);
        a++;
      }

    for (int i = 0; i < marbles.length; i++) {
      for (int j = 0; j < marbles[i].length; j++)
        System.out.print(marbles[i][j]);
      System.out.println();
    }
  }

  /**
   * Builds, fills and prints Lorenzo track and last token flipped
   */
  public void displayLorenzoHasMoved() {
    clearLorenzo();
    String[][] token = lastTokenMoved();
    lorenzoDrawer.fill(lorenzo, state.getSimpleGameState());

    for (int i = 0; i < token.length; i++)
      for (int j = 0; j < token[0].length; j++)
        lorenzo[i][j + 96] = token[i][j];

    for (int i = 0; i < lorenzo.length; i++) {
      for (int j = 0; j < lorenzo[0].length; j++)
        System.out.print(lorenzo[i][j]);
      System.out.println();
    }
  }

  /**
   * Builds, fills and prints Lorenzo track and last token flipped
   */
  public void displayLorenzoHasShuffled() {
    clearLorenzo();
    String[][] token = lastTokenShuffle();
    lorenzoDrawer.fill(lorenzo, state.getSimpleGameState());

    for (int i = 0; i < token.length; i++)
      for (int j = 0; j < token[0].length; j++)
        lorenzo[i][j + 96] = token[i][j];

    for (int i = 0; i < lorenzo.length; i++) {
      for (int j = 0; j < lorenzo[0].length; j++)
        System.out.print(lorenzo[i][j]);
      System.out.println();
    }
  }

  /**
   * Builds, fills and prints Lorenzo track and last token flipped
   * @param discardUpdate, update of the develop card Lorenzo has discarded
   */
  public void displayLorenzoHasDiscarded(DevelopCardDeckUpdate discardUpdate) {
    clearLorenzo();

    int column = discardUpdate.getColumn();

    String[][] tokenCard = lastTokenDiscarded(column);
    lorenzoDrawer.fill(lorenzo, state.getSimpleGameState());

    for (int i = 0; i < tokenCard.length; i++)
      for (int j = 0; j < tokenCard[0].length; j++)
        lorenzo[i][j + 96] = tokenCard[i][j];

    for (int i = 0; i < lorenzo.length; i++) {
      for (int j = 0; j < lorenzo[0].length; j++)
        System.out.print(lorenzo[i][j]);
      System.out.println();
    }
  }

//  public void drawTotalResourcesChoice(String username) {
//    String[][] resources = new String[8][2];
//    int row=0, col=0;
//    for(int i=0; i<resources.length; i++)
//      for(int j=0; j<resources[i].length; j++)
//        resources[i][j] = " ";
//
//    for(Map.Entry<ResourceType, Integer> entry : playerState.get(username).throwableResources().entrySet()) {
//      resources[row][col] = entry.getKey().toString();
//      switch (entry.getKey()) {
//        case GOLD:
//          resources[row+1][col] = "Y";
//          break;
//        case SERVANT:
//          resources[row+1][col] = "P";
//          break;
//        case STONE:
//          resources[row+1][col] = "G";
//          break;
//        case SHIELD:
//          resources[row+1][col] = "B";
//          break;
//      }
//      col += 2;
//    }
//
//    for(int i=0; i<resources.length; i++)
//      for(int j=0; j<resources[i].length; j++)
//        System.out.println(resources[i][j]);
//  }

  /**
   * Builds, fills and prints the DevelopCard Deck
   */
  public void drawDevelopCardDeck() {
    String[][] deckWithIndexes = new String[13][46];
    deck = deckDrawer.build();
    deckDrawer.fill(deck, state.getSimpleGameState());

    for (int r = 0; r < deckWithIndexes.length; r++)
      for (int c = 0; c < deckWithIndexes[0].length; c++)
        deckWithIndexes[r][c] = " ";

    deckWithIndexes[0][7] = "1";
    deckWithIndexes[0][7 + 11] = "2";
    deckWithIndexes[0][7 + 22] = "3";
    deckWithIndexes[0][7 + 33] = "4";

    deckWithIndexes[1][0] = "1";
    deckWithIndexes[1 + 4][0] = "2";
    deckWithIndexes[1 + 8][0] = "3";
    deckWithIndexes[1][1] = ".";
    deckWithIndexes[1 + 4][1] = ".";
    deckWithIndexes[1 + 8][1] = ".";

    for (int r = 1; r < deck.length; r++)
      for (int c = 0; c < deck[0].length; c++)
        deckWithIndexes[r][c + 2] = deck[r][c];

    for (int i = 0; i < deckWithIndexes.length; i++) {
      for (int j = 0; j < deckWithIndexes[0].length; j++)
        System.out.print(deckWithIndexes[i][j]);
      System.out.println();
    }
    System.out.println(Color.RESET.escape());
  }

  private void displayCanvas() {
    for (int i = 0; i < MAX_DISPLAYABLE_HEIGHT; i++) {
      for (int j = 0; j < MAX_DISPLAYABLE_LENGTH; j++)
        System.out.print(canvas[i][j]);
      System.out.println();
    }
  }

  private void clearCanvas() {
    for (int i = 0; i < MAX_DISPLAYABLE_HEIGHT; i++)
      for (int j = 0; j <  MAX_DISPLAYABLE_LENGTH; j++)
        canvas[i][j] = " ";
  }

  private void placeHereOnCanvas(int r, int c, String[][] placeMe) {
    int startRow = r, startCol;
    for (int i = 0; i < placeMe.length; i++, startRow++) {
      startCol = c;
      for (int j = 0; j < placeMe[0].length; j++, startCol++)
        canvas[startRow][startCol] = placeMe[i][j];
    }
  }

  private void setUsernameOnCanvas(String username) {
    int row = 0, col = 3;
    for (char c : username.toUpperCase().toCharArray()) {
      canvas[row][col] = Character.toString(c);
      col += 1;
    }

    for (char c : "'S PLAYERBOARD".toCharArray()) {
      canvas[row][col] = Character.toString(c);
      col += 1;
    }

    canvas[0][2] = " ";
    canvas[0][col] = " ";
  }

  private String[][] lastTokenMoved() {
    String[][] margin = MarginConstructor.buildMargins(4, 11);
    int col = 3;

    for (char c : "moved".toCharArray()) {
      margin[1][col] = Character.toString(c);
      col++;
    }

    margin[2][5] = Color.ANSI_RED.escape() + "2" + Color.RESET.escape();
    return margin;
  }

  private String[][] lastTokenShuffle() {
    String[][] margin = MarginConstructor.buildMargins(4, 11);
    int col = 2;

    for (char c : "shuffle".toCharArray()) {
      margin[1][col] = Character.toString(c);
      col++;
    }

    margin[2][4] = "\u2191";
    margin[2][6] = "\u2193";

    return margin;
  }

  private String[][] lastTokenDiscarded(int column) {
    String[][] margin = MarginConstructor.buildMargins(4, 11);
    int col = 2;

    for(char c :"discard".toCharArray()) {
      margin[1][col] = Character.toString(c);
      col++;
    }

    margin[2][4] = "-";
    margin[2][5] = "2";
    margin[2][6] = colorSwitch(column) + ConfigParameters.squareCharacter + Color.RESET.escape();

    return margin;
  }

  private void clearLorenzo() {
      for(int j=1; j<75; j+=3)
        lorenzo[2][j] = " ";
  }

  private String colorSwitch(int column) {
    String color = Color.RESET.escape();
    switch (column){
      case 0:
        color = Color.ANSI_GREEN.escape();
        break;
      case 1:
        color = Color.ANSI_BLUE.escape();
        break;
      case 2:
        color = Color.ANSI_YELLOW.escape();
        break;
      case 3:
        color = Color.ANSI_PURPLE.escape();
        break;
    }
    return color;
  }
}