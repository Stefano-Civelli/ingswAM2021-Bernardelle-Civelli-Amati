package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.SimpleStateObserver;

import java.util.Arrays;
import java.util.Map;

public class CliDrawer implements SimpleStateObserver {

  private final int PLAYERBOARD_LENGTH = 120;
  private final int PLAYERBOARD_HEIGHT = 16;
  private final int TRACK_HEIGHT = 1;
  private final int TRACK_LENGTH = 25;
  private final int MARKET_LENGTH = 11;
  private final int MARKET_HEIGHT = 5;
  private final int MAX_DISPLAYABLE_LENGTH = 200;
  private final int MAX_DISPLAYABLE_HEIGHT = 20;
  private static int MAX_COLUMN_TILES = 20;
  private static int MAX_ROW_TILES = 5;

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
    placeHereOnCanvas(2,PLAYERBOARD_LENGTH+7, buildAndSetMarket());
    buildTrack();
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

  private void fillDeck(String[][] deck) {
    Integer[][] cards = gameState.visibleCards();

    for (int i = 0, a = 1; i < cards.length; i++, a+=5) {
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
              deck[a+3][b+4] = " ";
              deck[a+3][b+5] = "\u25C6";
              deck[a+3][b+6] = Integer.toString(victory/10);
              deck[a+3][b+7] = Integer.toString(victory%10);
              deck[a+3][b+8] = " ";
            }
            else {
              deck[a+3][b+5] = " ";
              deck[a+3][b+6] = "\u25C6";
              deck[a+3][b+7] = Integer.toString(victory);
              deck[a+3][b+8] = " ";
            }
          } catch (InvalidCardException e) {}
        }
      }
    }
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

  private void skeletonWarehouse(int level) {
    int row=level+5, col=3;

    for(char c : "WAREHOUSE".toCharArray()) {
      canvas[4][col] = Character.toString(c);
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
    String[][] deck = new String[15][44];
    int r=5, c=11;

    for (int i=0; i<deck.length; i++)
      for (int j=0; j<deck[0].length; j++) {
        if(j<11)
          deck[i][j] = Color.ANSI_GREEN.escape() + buildMargins(r, c)[i % 5][j % 11];
        else if(j<22)
          deck[i][j] = Color.ANSI_BLUE.escape() + buildMargins(r, c)[i % 5][j % 11];
        else if(j<33)
          deck[i][j] = Color.ANSI_YELLOW.escape() + buildMargins(r, c)[i % 5][j % 11];
        else
          deck[i][j] = Color.ANSI_PURPLE.escape() + buildMargins(r, c)[i % 5][j % 11];
      }

    for (int i=0; i<deck.length; i+=5)
      for (int j=1; j<deck[0].length; j+=11) {
        deck[i][j] = " ";
        switch (i) {
          case 0:
            deck[i][j+1] = "I";
            deck[i][j+2] = "I";
            deck[i][j+3] = "I";
            deck[i][j+4] = " ";
            break;
          case 5:
            deck[i][j+1] = "I";
            deck[i][j+2] = "I";
            deck[i][j+3] = " ";
            break;
          case 10:
            deck[i][j+1] = "I";
            deck[i][j+2] = " ";
            break;
        }
      }


    return deck;
  }

  private void fillWarehouse(int level, ResourceType resource, int quantity) {
    int row = level+5;

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

  private void buildTrack() {
    String[][] trackAndVatican = new String[TRACK_HEIGHT+2][TRACK_LENGTH*3];
    String[][] track = skeletonTrack();

    for(int i=0; i<trackAndVatican.length; i++)
      for(int j=0; j<trackAndVatican[i].length; j++)
        trackAndVatican[i][j] = " ";

    for (int i=0; i<TRACK_LENGTH*3; i++)
      trackAndVatican[0][i] = track[0][i];

    trackAndVatican[1][6*3] = "│";
    trackAndVatican[2][6*3] = "└";
    trackAndVatican[2][6*3+1] = "─";
    trackAndVatican[1][6*3+2] = "│";
    trackAndVatican[2][6*3+2] = "┘";


    placeHereOnCanvas(2, 40, trackAndVatican);
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

  public void printCard(int devCardId) {

    String[][] innerOfCard = cardMargin();

    //DevelopCard dev = deck.getCard(1); //1 is the id
    //dev.getRequirements
//    for(ResourceType r : dev.getRequirements.getKey()) {
//      innerOfCard[3][5]
//    }
//    innerOfCard[3][4] = ;
    innerOfCard[2][4] = "⚫";
    innerOfCard[2][5] = "v";
    innerOfCard[2][6] = "p";

    innerOfCard[3][5] = "→";
    innerOfCard[3][6] = "⚫";
    printCard(innerOfCard);
  }

  private String[][] cardMargin() {
    int c, r;
    String[][] margin = new String[MAX_ROW_TILES][MAX_COLUMN_TILES];

    margin[0][0] = "╔";
    for (c = 1; c < MAX_COLUMN_TILES - 1; c++)
      margin[0][c] = "═";
    margin[0][c] = "╗";

    for (r = 1; r < MAX_ROW_TILES - 1; r++) {
      margin[r][0] = "║";
      for (c = 1; c < MAX_COLUMN_TILES - 1; c++)
        margin[r][c] = " ";
      margin[r][c] = "║";
    }

    margin[r][0] = "╚";
    for (c = 1; c < MAX_COLUMN_TILES - 1; c++)
      margin[r][c] = "═";
    margin[r][c] = "╝";

    return margin;
  }

  private void printCard(String[][] card) {
    for (int r = 0; r < MAX_ROW_TILES; r++) {
      for (int c = 0; c < MAX_COLUMN_TILES; c++)

        //System.out.print(marginColor.escape() + card[r][c] + resetColor.escape());
      System.out.println();
    }
  }
}
