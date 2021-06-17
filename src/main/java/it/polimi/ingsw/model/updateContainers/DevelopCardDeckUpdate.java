package it.polimi.ingsw.model.updateContainers;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class DevelopCardDeckUpdate {
   private final int row;
   private final int column;

   public DevelopCardDeckUpdate(int row, int column) {
      this.row = row;
      this.column = column;
   }

   /**
    * returns the row where a card has been removed
    * @return the row where a card has been removed
    */
   public int getRow() {
      return row;
   }

   /**
    * returns the column where a card has been removed
    * @return the column where a card has been removed
    */
   public int getColumn() {
      return column;
   }
}
