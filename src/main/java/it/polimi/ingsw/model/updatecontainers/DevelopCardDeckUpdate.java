package it.polimi.ingsw.model.updatecontainers;

/**
 * A model update represents an update happened in a {@link it.polimi.ingsw.model.DevelopCardDeck develop card deck}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class DevelopCardDeckUpdate implements ModelUpdate {

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
