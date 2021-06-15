package it.polimi.ingsw.model.updateContainers;

public class DevelopCardDeckUpdate {
   private final int row;
   private final int column;

   public DevelopCardDeckUpdate(int row, int column) {
      this.row = row;
      this.column = column;
   }

   public int getRow() {
      return row;
   }

   public int getColumn() {
      return column;
   }
}
