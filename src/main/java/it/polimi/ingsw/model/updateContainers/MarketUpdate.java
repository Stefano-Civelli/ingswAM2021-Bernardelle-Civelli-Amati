package it.polimi.ingsw.model.updateContainers;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class MarketUpdate {
   private final boolean isRow;
   private final int index;

   public MarketUpdate(boolean isRow, int index) {
      this.isRow = isRow;
      this.index = index;
   }

   /**
    * returns true if a row has been bushed, false otherwise
    * @return true if a row has been bushed, false otherwise
    */
   public boolean getIsRow() {
      return isRow;
   }

   /**
    * return index of the pushed row/column
    * @return index of the pushed row/column
    */
   public int getIndex() {
      return index;
   }
}
