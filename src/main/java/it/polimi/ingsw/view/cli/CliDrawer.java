package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.SimpleStateObserver;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CliDrawer implements SimpleStateObserver {

  private final int PLAYERBOARD_LENGTH = 90;
  private final int PLAYERBOARD_HEIGHT = 16;
  private final int TRACK_HEIGHT = 1;
  private final int TRACK_LENGTH = 25;
  private final int MARKET_LENGTH = 11;
  private final int MARKET_HEIGHT = 5;
  private final int MAX_DISPLAYABLE_LENGTH = 200;
  private final int MAX_DISPLAYABLE_HEIGHT = 16;
//  private static int MAX_COLUMN_TILES = 20;
//  private static int MAX_ROW_TILES = 5;

  private final Color marginColor = Color.RESET;
  private final String[][] canvas;

  private SimpleGameState gameState;
  //TODO usare quella nel clientr di cui ho il rifwerimento
  private Map<String, SimplePlayerState> playerState;

  public CliDrawer(SimpleGameState gameState, Map<String, SimplePlayerState> playerState) {
    this.gameState = gameState;
    this.playerState = playerState;
    this.canvas = new String[MAX_DISPLAYABLE_HEIGHT][MAX_DISPLAYABLE_LENGTH];

    for (int i=0; i<MAX_DISPLAYABLE_HEIGHT; i++)
      for (int j=0; j<MAX_DISPLAYABLE_LENGTH; j++)
        canvas[i][j] = " ";
  }

  //public interface
  public void displayPlainCanvas(){
    placeHereOnCanvas(0,0, canvas);
    displayCanvas();
  }

  public void displayDefaultCanvas(String username) {
    placeHereOnCanvas(0,0, buildMargins(PLAYERBOARD_HEIGHT, PLAYERBOARD_LENGTH));
    setUsernameOnCanvas(username);
    buildWarehouse(username);
    buildChest(username);
    placeHereOnCanvas(0,PLAYERBOARD_LENGTH+4, buildAndSetMarket());
    placeHereOnCanvas(1, PLAYERBOARD_LENGTH+4+MARKET_LENGTH+6, buildDevDeck());
    buildTrack(username);
    buildCardSlot(username);
    displayCanvas();
  }

  public void marketDisplay() {
    clearCanvas();
    String[][] market = buildAndSetMarket();
    for(int i=0; i<market.length; i++) {
      for (int j = 0; j < market[i].length; j++)
        System.out.print(market[i][j]);
      System.out.println();
    }
  }

  //TODO
  public void displayLeaderHand(String username) {
    //for (Integer l : playerState.get(username).getLeaderCards()) {
        //System.out.print(l.intValue() + " ");
    System.out.print(playerState.get(username).getLeaderCards());

    System.out.println();
    System.out.println("1 2 3 4");
      //buildLeaderHand();
    //}
  }

  public void displayResourcesChoice() {
    String[][] marbles = new String[2][8];

    for(int i=0; i<marbles.length; i++)
      for(int j=0; j<marbles[i].length; j++)
        marbles[i][j] = " ";

    marbles[0][0] = MarbleColor.BLUE.toString();
    marbles[0][2] = MarbleColor.PURPLE.toString();
    marbles[0][4] = MarbleColor.YELLOW.toString();
    marbles[0][6] = MarbleColor.GREY.toString();

    int a=1;
    for(int c=0; c<marbles[0].length; c++)
      if(c%2==0) {
        marbles[1][c] = Integer.toString(a);
        a++;
      }

    for(int i=0; i<marbles.length; i++) {
      for (int j = 0; j < marbles[i].length; j++)
        System.out.print(marbles[i][j]);
    System.out.println();
    }
  }

  public void drawTotalResourcesChoice(String username) {
    String[][] resources = new String[8][2];
    int row=0, col=0;
    for(int i=0; i<resources.length; i++)
      for(int j=0; j<resources[i].length; j++)
        resources[i][j] = " ";

    for(Map.Entry<ResourceType, Integer> entry : playerState.get(username).throwableResources().entrySet()) {
      resources[row][col] = entry.getKey().toString();
      switch (entry.getKey()) {
        case GOLD:
          resources[row+1][col] = "Y";
          break;
        case SERVANT:
          resources[row+1][col] = "P";
          break;
        case STONE:
          resources[row+1][col] = "G";
          break;
        case SHIELD:
          resources[row+1][col] = "B";
          break;
      }
      col += 2;
    }

    for(int i=0; i<resources.length; i++)
      for(int j=0; j<resources[i].length; j++)
        System.out.println(resources[i][j]);
  }

  public void drawDevelopCardDeck(){
    String[][] deck = skeletonCards();
    fillDeck(deck);

    for (int i=0; i<deck.length; i++) {
      for (int j = 0; j < deck[0].length; j++)
        System.out.print(deck[i][j]);
      System.out.println();
    }
    System.out.println(Color.RESET.escape());
  }


  //private methods
  private void displayCanvas() {
    for (int i = 0; i< MAX_DISPLAYABLE_HEIGHT; i++) {
      for (int j = 0; j < MAX_DISPLAYABLE_LENGTH; j++)
        System.out.print(canvas[i][j]);
      System.out.println();
    }
  }

  private void clearCanvas() {
    for (int i=0; i<canvas.length; i++)
      for (int j=0; j<canvas[i].length; j++)
        canvas[i][j] = " ";
  }

  private void placeHereOnCanvas(int r, int c, String[][] placeMe) {
    int startRow = r, startCol = c;
    for(int i=0 ; i<placeMe.length; i++, startRow++) {
      startCol = c;
      for (int j=0; j < placeMe[0].length; j++, startCol++)
        canvas[startRow][startCol] = placeMe[i][j];
    }
  }

  private void setUsernameOnCanvas(String username) {
    int row=0, col=3;
    for(char c : username.toUpperCase().toCharArray()) {
      canvas[row][col] = Character.toString(c);
      col += 1;
    }

    for(char c : "'S PLAYERBOARD".toCharArray()) {
      canvas[row][col] = Character.toString(c);
      col += 1;
    }

    canvas[0][2] = " ";
    canvas[0][col] = " ";
  }

  //------------------------- BUILD --------------------------------
  private String[][] buildMargins(int row_dim, int col_dim){
    int c, r;
    String[][] margins = new String[row_dim][col_dim];

    margins[0][0] = "╔";
    for (c = 1; c < col_dim - 1; c++)
      margins[0][c] = "═";
    margins[0][c] = "╗";

    for (r = 1; r < row_dim - 1; r++) {
      margins[r][0] = "║";
      for (c = 1; c < col_dim - 1; c++)
        margins[r][c] = " ";
      margins[r][c] = "║";
    }

    margins[r][0] = "╚";
    for (c = 1; c < col_dim - 1; c++)
      margins[r][c] = "═";
    margins[r][c] = "╝";
    return margins;
  }

  private void buildWarehouse(String username) {
    Pair<ResourceType, Integer>[] warehouse = playerState.get(username).getWarehouseLevels();
    for(int i=0, j=warehouse.length-1; i<warehouse.length; i++, j--) {
      skeletonWarehouse(i);
      fillWarehouse(i, warehouse[j].getKey(), (warehouse[j].getKey() == null) ? 0 : warehouse[j].getValue());
    }
  }

  private void buildChest(String username) {
    ResourceType[] resources = new ResourceType[]{ResourceType.GOLD, ResourceType.SERVANT, ResourceType.SHIELD, ResourceType.STONE};
    Map<ResourceType, Integer> chest = playerState.get(username).getChest();
    placeHereOnCanvas( 11, 3, skeletonChest());

    for(ResourceType r : resources) {
      if(!chest.containsKey(r))
        fillChest(r, 0);
      else
        fillChest(r, chest.get(r));
    }
  }

  private String[][] buildAndSetMarket() {
    MarbleColor[][] marketColor = gameState.getMarket();
    String[][] market = buildMargins(MARKET_HEIGHT, MARKET_LENGTH);
    String[][] marketAndSlide = new String[MARKET_HEIGHT+4][MARKET_LENGTH+2];

    for(int i=0; i<marketAndSlide.length; i++)
      for(int j=0; j<marketAndSlide[i].length; j++)
        marketAndSlide[i][j] = " ";

    int a=0, b;
    for(int i=1; a<marketColor.length; i++) {
      b=0;
      for (int j=1; b<marketColor[a].length; j++) {
        if(j%2==0) {
          market[i][j] = marketColor[a][b].toString();
          b++;
        }
      }
      a++;
    }

    for(int c=0; c<marketAndSlide[0].length; c++) {
      marketAndSlide[MARKET_HEIGHT+2][c] = " ";
      marketAndSlide[MARKET_HEIGHT+3][c] = "\u203E";
    }

    marketAndSlide[MARKET_HEIGHT+2][MARKET_LENGTH-1] = gameState.getSlide().toString();

    for(int i=0; i<market.length; i++)
      for(int j=0; j<market[i].length; j++)
        marketAndSlide[i+2][j] = market[i][j];

    int col=0;
    for(char c : "slide".toCharArray()) {
      marketAndSlide[MARKET_HEIGHT+2][col] = Character.toString(c);
      col ++;
    }
    col = col + 2;
    marketAndSlide[MARKET_HEIGHT+2][col] = ConfigParameters.arrowCharacter;

    col=0;
    for(char c : "MARKET".toCharArray()) {
      marketAndSlide[0][col] = Character.toString(c);
      col ++;
    }

    col = 2;
    int row = 3;
    for(int i=0; i<4; i++) {
      marketAndSlide[1][col] = Integer.toString(i+1);
      if(i<3)
        marketAndSlide[row][MARKET_LENGTH+1] = Integer.toString(i+1);
      col += 2;
      row++;
    }

    return marketAndSlide;
  }

  private void buildLeadersHand(String username) {
  }

  private void buildTrack(String username) {
    int position = playerState.get(username).getTrackPosition();
    boolean[] popeCards = playerState.get(username).getVaticanFlipped();
    //System.out.println("drawer" +popeCards[0]);
    int c;

    String[][] trackAndVatican = new String[TRACK_HEIGHT+2][TRACK_LENGTH*3];
    String[][] track = skeletonTrack();

    for(int i=0; i<trackAndVatican.length; i++)
      for(int j=0; j<trackAndVatican[i].length; j++)
        trackAndVatican[i][j] = " ";

    for (int i=0; i<TRACK_LENGTH*3; i++)
      trackAndVatican[1][i] = track[0][i];

    trackAndVatican[1][position*3+1] = Color.ANSI_RED.escape() + "+" + Color.RESET.escape();
    //to paint the track squares
    trackAndVatican[2][8*3] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    trackAndVatican[2][8*3+2] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    trackAndVatican[1][8*3] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3] + Color.RESET.escape();
    trackAndVatican[1][8*3+2] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3+2] + Color.RESET.escape();
    trackAndVatican[2][16*3] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    trackAndVatican[2][16*3+2] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    trackAndVatican[1][16*3] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3] + Color.RESET.escape();
    trackAndVatican[1][16*3+2] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3+2] + Color.RESET.escape();
    trackAndVatican[2][24*3] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    trackAndVatican[2][24*3+2] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    trackAndVatican[1][24*3] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3] + Color.RESET.escape();
    trackAndVatican[1][24*3+2] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3+2] + Color.RESET.escape();

    trackAndVatican[1][23*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][23*3] + Color.RESET.escape();
    trackAndVatican[1][23*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][23*3+2] + Color.RESET.escape();
    trackAndVatican[1][22*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][22*3] + Color.RESET.escape();
    trackAndVatican[1][22*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][22*3+2] + Color.RESET.escape();
    trackAndVatican[1][21*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][21*3] + Color.RESET.escape();
    trackAndVatican[1][21*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][21*3+2] + Color.RESET.escape();
    trackAndVatican[1][20*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][20*3] + Color.RESET.escape();
    trackAndVatican[1][20*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][20*3+2] + Color.RESET.escape();
    trackAndVatican[1][19*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][19*3] + Color.RESET.escape();
    trackAndVatican[1][19*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][19*3+2] + Color.RESET.escape();

    trackAndVatican[1][15*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][15*3] + Color.RESET.escape();
    trackAndVatican[1][15*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][15*3+2] + Color.RESET.escape();
    trackAndVatican[1][14*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][14*3] + Color.RESET.escape();
    trackAndVatican[1][14*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][14*3+2] + Color.RESET.escape();
    trackAndVatican[1][13*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][13*3] + Color.RESET.escape();
    trackAndVatican[1][13*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][13*3+2] + Color.RESET.escape();
    trackAndVatican[1][12*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][12*3] + Color.RESET.escape();
    trackAndVatican[1][12*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][12*3+2] + Color.RESET.escape();

    trackAndVatican[1][7*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][7*3] + Color.RESET.escape();
    trackAndVatican[1][7*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][7*3+2] + Color.RESET.escape();
    trackAndVatican[1][6*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][6*3] + Color.RESET.escape();
    trackAndVatican[1][6*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][6*3+2] + Color.RESET.escape();
    trackAndVatican[1][5*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][5*3] + Color.RESET.escape();
    trackAndVatican[1][5*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][5*3+2] + Color.RESET.escape();

    //to draw the vp number
    trackAndVatican[0][0] = "v";
    trackAndVatican[0][1] = "p";
    trackAndVatican[0][2] = "\u25C6";
    trackAndVatican[0][3*3+1] = "1";
    trackAndVatican[0][6*3+1] = "2";
    trackAndVatican[0][9*3+1] = "4";
    trackAndVatican[0][12*3+1] = "6";
    trackAndVatican[0][15*3+1] = "9";
    trackAndVatican[0][18*3+1] = "1";
    trackAndVatican[0][18*3+2] = "2";
    trackAndVatican[0][21*3+1] = "1";
    trackAndVatican[0][21*3+2] = "6";
    trackAndVatican[0][24*3+1] = "2";
    trackAndVatican[0][24*3+2] = "0";

    c=8;
    for(int i=0; i<popeCards.length; i++, c+=8) {
      //System.out.println(popeCards[i]);
      if (popeCards[i]) {
        trackAndVatican[2][c*3+1] = Integer.toString(i + 2);
      } else {
        trackAndVatican[2][c * 3 + 1] = Color.ANSI_RED.escape() + "X" + Color.RESET.escape();
      }
    }

    placeHereOnCanvas(1, 3, trackAndVatican);
  }

  private void buildCardSlot(String username) {
    String[][] cardSlots = new String[9][39];

    for (int i=0; i<cardSlots.length; i++)
      for (int j=0; j<cardSlots[0].length; j++)
        cardSlots[i][j] = " ";

    skeletonCardSlots(cardSlots);
    fillCardSlots(cardSlots, username);
    placeHereOnCanvas(5, 20, cardSlots);
  }

  private String[][] buildDevDeck() {
    int col = PLAYERBOARD_LENGTH+4+MARKET_LENGTH+6;
    String[][] deck = skeletonCards();
    fillDeck(deck);

    for (char c : "DEV DECK ".toCharArray()) {
      canvas[0][col] = Character.toString(c);
      col++;
    }
    return deck;
  }
  //------------------------- BUILD --------------------------------

  //------------------------- SKELETON & FILL --------------------------------
  private void skeletonWarehouse(int level) {
    int row=level+6, col=3;

    for(char c : "WAREHOUSE".toCharArray()) {
      canvas[5][col] = Character.toString(c);
      col ++;
    }

    col = 3;
    canvas[row][col] = "[";
    col++;
    for (int i=0; i<level; i++, col++) {
      canvas[row][col] = " ";
      col ++;
      canvas[row][col] = "|";
    }
    canvas[row][col] = " ";
    col++;
    canvas[row][col] = ("]");
  }

  private String[][] skeletonChest() {
    int rows = 4, columns = 9;
    int col = 3;
    for(char c : "CHEST".toCharArray()) {
      canvas[10][col] = Character.toString(c);
      col ++;
    }

    String[][] chest = new String[rows][columns];
    chest[0][0] = "\u25A0";
    chest[0][columns-1] = "\u25A0";
    chest[rows-1][0] = "\u25A0";
    chest[rows-1][columns-1] = "\u25A0";

    for (int r = 1; r < rows-1; r++) {
      chest[r][0] = "║";
      for (int c = 1; c < columns-1; c++) {
        if(c == 2 || c == 5)
          chest[r][c] = "0";
        else
          chest[r][c] = " ";
      }
      chest[r][columns-1] = "║";
    }

    for (int c = 1; c < columns-1; c++) {
      chest[0][c] = "\u25A0";
      chest[rows-1][c] = "\u25A0";
    }

    return chest;
  }

  private String[][] skeletonCards() {
    String[][] deck = new String[12][44];
    int r=4, c=11;

    for (int i=0; i<deck.length; i++)
      for (int j=0; j<deck[0].length; j++) {
        if(j<c)
          deck[i][j] = Color.ANSI_GREEN.escape() + buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
        else if(j<c*2)
          deck[i][j] = Color.ANSI_BLUE.escape() + buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
        else if(j<c*3)
          deck[i][j] = Color.ANSI_YELLOW.escape() + buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
        else
          deck[i][j] = Color.ANSI_PURPLE.escape() + buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
      }

    for (int i=0; i<deck.length; i+=r)
      for (int j=1; j<deck[0].length; j+=c) {
        deck[i][j] = " ";
        if(i == r*2) {
          deck[i][j+1] = "I";
          deck[i][j+2] = "I";
          deck[i][j+3] = "I";
          deck[i][j+4] = " ";
        }
        if(i == r) {
          deck[i][j+1] = "I";
          deck[i][j+2] = "I";
          deck[i][j+3] = " ";
        }
        if(i == 0) {
          deck[i][j+1] = "I";
          deck[i][j+2] = " ";
        }
      }//for
    return deck;
  }

  private String[][] skeletonTrack() {
    String[][] track = new String[TRACK_HEIGHT][TRACK_LENGTH*3];
    int i;

    track[0][0] = "[";
    for (i=1; i<TRACK_LENGTH*3-2; i++) {
      if(i%3==0)
        track[0][i] = "[";
      if(i%3==1)
        track[0][i] = " ";
      if(i%3==2)
        track[0][i] = "]";
    }
    track[0][i] = " ";
    i++;
    track[0][i] = ("]");

    return track;
  }

  private void skeletonCardSlots(String[][] cardSlots) {
    int col=4;
    String[][] margins = buildMargins(8, 13);

    for(int i=0; i<3; i++) {
      for (char c : "slot ".toCharArray()) {
        cardSlots[cardSlots.length-1][col] = Character.toString(c);
        col++;
      }
      cardSlots[cardSlots.length-1][col-1] = Integer.toString(i+1);
      col+=8;
    }

    for(int i=0; i<margins.length; i++)
      for(int j=0; j<margins[0].length; j++) {
        cardSlots[i][j] = margins[i][j];
        cardSlots[i][j+13] = margins[i][j];
        cardSlots[i][j+26] = margins[i][j];
      }
  }

  private void fillWarehouse(int level, ResourceType resource, int quantity) {
    int row = level+6;

    for(int col=4; quantity>0; col+=2, quantity--)
      canvas[row][col] = resource.toString();
  }

  private void fillChest(ResourceType resource, int quantity) {
    switch (resource){
      case GOLD:
        canvas[12][5] = Integer.toString(quantity);
        canvas[12][6] = resource.toString();
        break;
      case STONE:
        canvas[12][8] = Integer.toString(quantity);
        canvas[12][9] = resource.toString();
        break;
      case SHIELD:
        canvas[13][5] = Integer.toString(quantity);
        canvas[13][6] = resource.toString();
        break;
      case SERVANT:
        canvas[13][8] = Integer.toString(quantity);
        canvas[13][9] = resource.toString();
        break;
    }
  }

  private void fillDeck(String[][] deck) {
    Integer[][] cards = gameState.visibleCards();

    for (int i = 0, a = 1; i < cards.length; i++, a+=4) {
      for (int j = 0, b = 1; j < cards[0].length; j++, b+=11) {
        if (cards[i][j] != null) {
          try {
            DevelopCard d = Cli.getDevelopCardFromId(cards[i][j]);
            int victory = d.getVictoryPoints();
            int c=b;
            Map<ResourceType, Integer> cost = d.getCost();
            Map<ResourceType, Integer> requirements = d.getRequirement();
            Map<ResourceType, Integer> products = d.getProduct();

            for(Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
              deck[a][c] = entry.getKey().getColor().getColor() + entry.getValue().toString();
              c+=2;
            }

            c=b;
            for(Map.Entry<ResourceType, Integer> entry : requirements.entrySet()) {
              deck[a+1][c] = entry.getKey().getColor().getColor() + entry.getValue().toString();
              c+=2;
            }

            deck[a+1][c-1] = ConfigParameters.arrowCharacter;

            for(Map.Entry<ResourceType, Integer> entry : products.entrySet()) {
              deck[a+1][c] = entry.getKey().getColor().getColor() + entry.getValue().toString();
              c+=2;
            }

            if(victory > 9) {
              deck[a+2][b+4] = " ";
              deck[a+2][b+5] = "\u25C6";
              deck[a+2][b+6] = Integer.toString(victory/10);
              deck[a+2][b+7] = Integer.toString(victory%10);
              deck[a+2][b+8] = " ";
            }
            else {
              deck[a+2][b+5] = " ";
              deck[a+2][b+6] = "\u25C6";
              deck[a+2][b+7] = Integer.toString(victory);
              deck[a+2][b+8] = " ";
            }
          } catch (InvalidCardException e) {}
        }
      }
    }
  }

  private void fillCardSlots(String[][] cardSlots, String username) {
    List<Integer>[] slots = playerState.get(username).getCardSlots();
    int a=cardSlots.length-6, b=1;

    for(int i=0; i<slots.length; i++) {
      for (int j=0; j<slots[i].size(); j++) {
        try {
          DevelopCard d = Cli.getDevelopCardFromId(slots[i].get(j));
          DevelopCardColor color = d.getCardFlag().getColor();
          int level = d.getCardFlag().getLevel();
          String[][] card = buildMargins(4, 11);
          int victory = d.getVictoryPoints();
          Map<ResourceType, Integer> cost = d.getCost();
          Map<ResourceType, Integer> requirements = d.getRequirement();
          Map<ResourceType, Integer> products = d.getProduct();


          for (int k=0, c=a; k < card.length; k++, c++)
            for (int w=0, e=b; w < card[i].length; w++, e++) {
              switch (color) {
                case BLUE:
                  cardSlots[c][e] = Color.ANSI_BLUE.escape() + card[k][w] + Color.RESET.escape();
                  break;
                case GREEN:
                  cardSlots[c][e] = Color.ANSI_GREEN.escape() + card[k][w] + Color.RESET.escape();
                  break;
                case PURPLE:
                  cardSlots[c][e] = Color.ANSI_PURPLE.escape() + card[k][w] + Color.RESET.escape();
                  break;
                case YELLOW:
                  cardSlots[c][e] = Color.ANSI_YELLOW.escape() + card[k][w] + Color.RESET.escape();
                  break;
              }
            }

          switch (level) {
            case 1:
              cardSlots[a][b+1] = " ";
              cardSlots[a][b+2] = "I";
              cardSlots[a][b+3] = " ";
              break;
            case 2:
              cardSlots[a][b+1] = " ";
              cardSlots[a][b+2] = "I";
              cardSlots[a][b+3] = "I";
              cardSlots[a][b+4] = " ";
              break;
            case 3:
              cardSlots[a][b+1] = " ";
              cardSlots[a][b+2] = "I";
              cardSlots[a][b+3] = "I";
              cardSlots[a][b+4] = "I";
              cardSlots[a][b+5] = " ";
              break;
          }

          int c=a+1, e=b+1;
          for(Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            cardSlots[c][e] = entry.getKey().getColor().getColor() + entry.getValue().toString() + Color.RESET.escape();
            e+=2;
          }

          e=b+1;
          for(Map.Entry<ResourceType, Integer> entry : requirements.entrySet()) {
            cardSlots[c+1][e] = entry.getKey().getColor().getColor() + entry.getValue().toString() + Color.RESET.escape();
            e+=2;
          }

          cardSlots[c+1][e-1] = ConfigParameters.arrowCharacter;

          for(Map.Entry<ResourceType, Integer> entry : products.entrySet()) {
            cardSlots[c+1][e] = entry.getKey().getColor().getColor() + entry.getValue().toString() + Color.RESET.escape();
            e+=2;
          }

          if(victory > 9) {
            cardSlots[c+2][b+5] = " ";
            cardSlots[c+2][b+6] = "\u25C6";
            cardSlots[c+2][b+7] = Integer.toString(victory/10);
            cardSlots[c+2][b+8] = Integer.toString(victory%10);
            cardSlots[c+2][b+9] = " ";
          }
          else {
            cardSlots[c+2][b+6] = " ";
            cardSlots[c+2][b+7] = "\u25C6";
            cardSlots[c+2][b+8] = Integer.toString(victory);
            cardSlots[c+2][b+9] = " ";
          }

          a--;
        } catch (InvalidCardException e) {
        }
      }
      b=b+13;
      a=cardSlots.length-6;
    }
  }
  //------------------------- FILL --------------------------------
}
