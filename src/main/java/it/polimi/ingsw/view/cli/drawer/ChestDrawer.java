package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.view.SimplePlayerState;

import java.util.Map;

public class ChestDrawer implements Buildable, Fillable{
  private static final int CHEST_LENGTH = 11;
  private static final int CHEST_HEIGHT = 4;

  @Override
  public String[][] build() {
    String[][] chestSkeleton = new String[CHEST_HEIGHT+1][CHEST_LENGTH];
    int col = 0;

    for(int c = 0; c < CHEST_LENGTH-1; c++)
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
  public String[][] fill(String[][] fillMe, SimplePlayerState playerState) {
    Map<ResourceType, Integer> chest = playerState.getChest();

    for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
      for(int r = 0; r < fillMe.length; r++)
        for(int c = 0; c < fillMe[r].length; c++)
          if(fillMe[r][c].equals(entry.getKey().toString())) {
            if(entry.getValue() <= 9)
              fillMe[r][c-1] = Integer.toString(entry.getValue());
            else {
              fillMe[r][c-1] = Integer.toString(entry.getValue()%10);
              fillMe[r][c-1] = Integer.toString(entry.getValue()/10);
            }
          }

    return fillMe;
  }
}
