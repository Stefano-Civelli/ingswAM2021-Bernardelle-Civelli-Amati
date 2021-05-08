package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CliDrawer {

  private static int MAX_COLUMN_TILES = 9;
  private static int MAX_ROW_TILES = 6;
  private static final Color marginColor = Color.ANSI_GREEN;
  private static final Color resetColor = Color.RESET;

  private SimpleGameState gameState;
  private Map<String, SimplePlayerState> playerState;

  public CliDrawer(SimpleGameState gameState, Map<String, SimplePlayerState> playerState) {
    this.gameState = gameState;
    this.playerState = playerState;
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
    innerOfCard[3][5] = "→";
    innerOfCard[3][6] = "⚫";
    //printCard(innerOfCard);
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

        System.out.print(marginColor.escape() + card[r][c] + resetColor.escape());
      System.out.println();
    }
  }

  public void printWarehouse(String username) {
    Pair<ResourceType, Integer>[] warehouse = playerState.get(username).getWarehouseLevels();
    for(int i=0; i<warehouse.length; i++) {
      List<String> level = skeletonWarehouse(i);
      int quantity = warehouse[i].getValue();
      int k = 1;
      ResourceType resource = warehouse[i].getKey();
      for(int j=0; j<quantity; j++) {
        level.add(j + k, resource.toString());
        k += 1;
      }
      System.out.println(resource);
    }
  }

  private List<String> skeletonWarehouse(int level) {
    List<String> skeleton = new ArrayList<>();
    skeleton.add("[");
    for (int i=0; i<level; i ++) {
      skeleton.add(" ");
      skeleton.add("|");
    }
    skeleton.add(" ");
    skeleton.add("]");

    return skeleton;
  }

  public void marketDisplay() {
    MarbleColor[][] market = gameState.getMarket();
    for(int i=0; i<market.length; i++) {
      for (int j = 0; j < market[i].length; j++)
        System.out.print(market[i][j] + " " + Color.RESET);
      System.out.println();
    }
  }
}
