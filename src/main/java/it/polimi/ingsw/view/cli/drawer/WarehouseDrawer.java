package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

public class WarehouseDrawer implements Buildable, Fillable{
  private static final int WAREHOUSE_LENGTH = 9;
  private static final int WAREHOUSE_HEIGHT = 4;

  @Override
  public String[][] build() {
    String[][] warehouseSkeleton = new String[WAREHOUSE_HEIGHT][WAREHOUSE_LENGTH];
    int col = 0;

    for(int i=0; i<warehouseSkeleton.length; i++)
      for(int j=0; j<warehouseSkeleton[0].length; j++)
        warehouseSkeleton[i][j] = " ";

    for(int c = 0; c < warehouseSkeleton[0].length; c++)
      warehouseSkeleton[0][c] = " ";

    for(char c : "WAREHOUSE".toCharArray()) {
      warehouseSkeleton[0][col] = Character.toString(c);
      col ++;
    }

    for(int r = 1; r < WAREHOUSE_HEIGHT; r++) {
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
  public void fill(String[][] fillMe, SimplePlayerState playerState) {
    Pair<ResourceType, Integer>[] warehouse = playerState.getWarehouseLevels();
    int quantity;
    ResourceType resource;

    for(int i=0, j=warehouse.length-1; i<warehouse.length; i++, j--) {
      quantity = (warehouse[j].getKey() == null) ? 0 : warehouse[j].getValue();
      resource = warehouse[j].getKey();

      for (int col = 1; quantity > 0; col += 2, quantity--)
        fillMe[i+1][col] = resource.toString();
    }
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {}
}