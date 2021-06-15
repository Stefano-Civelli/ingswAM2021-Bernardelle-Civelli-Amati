package it.polimi.ingsw.model.updateContainers;

public class MarketUpdate {
   private final boolean isRow;
   private final int index;

   public MarketUpdate(boolean isRow, int index) {
      this.isRow = isRow;
      this.index = index;
   }

   public boolean getIsRow() {
      return isRow;
   }

   public int getIndex() {
      return index;
   }
}
