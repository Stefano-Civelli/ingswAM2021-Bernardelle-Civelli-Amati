package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimplePlayerState;

public class WarehouseDrawer implements Buildable, Fillable{
  private static final int WAREHOUSE_LENGTH = 7;
  private static final int WAREHOUSE_HEIGHT = 4;

  @Override
  public String[][] build() {
    String[][] warehouseSkeleton = new String[WAREHOUSE_LENGTH][WAREHOUSE_HEIGHT];
    int col = 0;

    for(int c = 0; c < warehouseSkeleton[0].length; c++)
      warehouseSkeleton[0][c] = " ";

    for(char c : "WAREHOUSE".toCharArray()) {
      warehouseSkeleton[0][col] = Character.toString(c);
      col ++;
    }

    for(int r = 1; r < WAREHOUSE_HEIGHT-1; r++) {
      col = 0;
      warehouseSkeleton[r][col] = "[";
      col++;
      for(int i = 0; i < r-1; i++, col++) {
        warehouseSkeleton[r][col] = " ";
        col++;
        warehouseSkeleton[r][col] = "|";
      }
      warehouseSkeleton[r][col] = " ";
      col++;
      warehouseSkeleton[r][col] = ("]");
    }
    return warehouseSkeleton;
  }

  @Override
  public String[][] fill(String[][] fillMe, SimplePlayerState playerState) {
    
    return new String[0][];
  }
}
