package it.polimi.ingsw.view.cli.drawer;

public class MarginConstructor {

  public static String[][] buildMargins(int row_dim, int col_dim){
    String[][] margins = new String[row_dim][col_dim];
    int c, r;

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
}
