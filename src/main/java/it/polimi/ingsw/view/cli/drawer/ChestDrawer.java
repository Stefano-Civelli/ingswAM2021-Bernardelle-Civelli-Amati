package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

import java.util.Map;

/**
 * Class that builds and fills the Cli representation of the Chest
 */
public class ChestDrawer implements Buildable, Fillable{
  private static final int CHEST_LENGTH = 11;
  private static final int CHEST_HEIGHT = 4;

  @Override
  public String[][] build() {
    String[][] chestSkeleton = new String[CHEST_HEIGHT+1][CHEST_LENGTH];
    int col = 0;

    for(int c = 0; c < CHEST_LENGTH; c++)
      chestSkeleton[0][c] = " ";

    for(char c : "CHEST".toCharArray()) {
      chestSkeleton[0][col] = Character.toString(c);
      col ++;
    }

    chestSkeleton[1][0] = "\u25A0";
    chestSkeleton[1][CHEST_LENGTH-1] = "\u25A0";
    chestSkeleton[CHEST_HEIGHT][0] = "\u25A0";
    chestSkeleton[CHEST_HEIGHT][CHEST_LENGTH-1] = "\u25A0";

    for(int r = 2; r < CHEST_HEIGHT; r++) {
      chestSkeleton[r][0] = "║";
      for(int c = 1; c < CHEST_LENGTH-1; c++) {
        if(c == 3 || c == 7)
          chestSkeleton[r][c] = "0";
        else
          chestSkeleton[r][c] = " ";
      }
      chestSkeleton[r][CHEST_LENGTH-1] = "║";
    }

    for (int c = 1; c < CHEST_LENGTH-1; c++) {
      chestSkeleton[1][c] = "\u25A0";
      chestSkeleton[CHEST_HEIGHT][c] = "\u25A0";
    }

    return chestSkeleton;
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {
    Map<ResourceType, Integer> chest = playerState.getChest();

    for(Map.Entry<ResourceType, Integer> entry : chest.entrySet()) {
      ResourceType resource = entry.getKey();
      int quantity = entry.getValue();

      switch (resource) {
        case GOLD:
          fillMe[2][4] = resource.toString();
          if (quantity < 10)
            fillMe[2][3] = Integer.toString(quantity);
          else {
            fillMe[2][3] = Integer.toString(quantity % 10);
            fillMe[2][2] = Integer.toString(quantity / 10);
          }
          break;
        case STONE:
          fillMe[2][8] = resource.toString();
          if (quantity < 10)
            fillMe[2][7] = Integer.toString(quantity);
          else {
            fillMe[2][7] = Integer.toString(quantity % 10);
            fillMe[2][6] = Integer.toString(quantity / 10);
          }
          break;
        case SHIELD:
          fillMe[3][4] = resource.toString();
          if (quantity < 10)
            fillMe[3][3] = Integer.toString(quantity);
          else {
            fillMe[3][3] = Integer.toString(quantity % 10);
            fillMe[3][2] = Integer.toString(quantity / 10);
          }
          break;
        case SERVANT:
          fillMe[3][8] = resource.toString();
          if (quantity < 10)
            fillMe[3][7] = Integer.toString(quantity);
          else {
            fillMe[3][7] = Integer.toString(quantity % 10);
            fillMe[3][6] = Integer.toString(quantity / 10);
          }
          break;
      }
    }
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {/*does nothing*/}
}
