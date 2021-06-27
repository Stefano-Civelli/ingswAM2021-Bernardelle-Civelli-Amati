package it.polimi.ingsw.model.updatecontainers;

/**
 * A model update represents an update happened in a {@link it.polimi.ingsw.model.market.Market market}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class MarketUpdate implements ModelUpdate {

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
