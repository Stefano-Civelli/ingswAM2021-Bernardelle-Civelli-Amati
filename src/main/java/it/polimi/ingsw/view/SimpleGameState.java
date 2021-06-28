package it.polimi.ingsw.view;

import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckSetup;
import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;
import it.polimi.ingsw.model.updatecontainers.MarketSetup;
import it.polimi.ingsw.model.updatecontainers.MarketUpdate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing a simplified version of the game state.
 * Contains the state of the market and the state of the DevelopCard Deck.
 * It also contains Lorenzo's position.
 */
public class SimpleGameState {

   private final int NUMBER_OF_DECK_ROWS = 3;
   private final int NUMBER_OF_DECK_COLUMS = 4;

   private List<Integer>[][] developCardDeck; //identified by ID
   private MarbleColor[][] market;
   private MarbleColor slide;
   private List<MarbleColor> tempMarble;
   private int lorenzoTrackPosition = 0;

   /**
    * Constructor for SimpleGameState class
    */
   public SimpleGameState() {
      tempMarble = new ArrayList<>();
   }

   /**
    * Setups the DevelopCard Deck
    * @param stateSetup, setup content
    */
   public void constructDeck(DevelopCardDeckSetup stateSetup) {
      this.developCardDeck = stateSetup.getDevDeck();
   }

   /**
    * Setups the market
    * @param stateSetup, setup content
    */
   public void constructMarket (MarketSetup stateSetup) {
      this.market = stateSetup.getMarbleMatrix();
      this.slide = stateSetup.getSlide();
   }

   //----------UPDATE-----------
   /**
    * Updates the DevelopCard Deck
    * @param stateUpdate, update content
    */
   public void updateDeck(DevelopCardDeckUpdate stateUpdate) {
      int row = stateUpdate.getRow();
      int column = stateUpdate.getColumn();

      developCardDeck[row][column].remove(developCardDeck[row][column].size()-1);
   }

   /**
    * Updates the market
    * @param stateUpdate, update content
    */
   public void updateMarket(MarketUpdate stateUpdate) {
      boolean isRow = stateUpdate.getIsRow();
      int index = stateUpdate.getIndex();

      MarbleColor swap1, swap2;
      if(isRow) {
         swap1 = this.market[index][this.market[index].length - 1];
         this.market[index][this.market[index].length - 1] = this.slide;
         for(int i = this.market[index].length - 2; i >= 0; i--) {
            swap2 = this.market[index][i];
            this.market[index][i] = swap1;
            swap1 = swap2;
         }
      }
      else {
            swap1 = this.market[this.market.length - 1][index];
            this.market[this.market.length - 1][index] = this.slide;
            for(int i = this.market.length - 2; i >= 0; i--) {
               swap2 = this.market[i][index];
               this.market[i][index] = swap1;
               swap1 = swap2;
            }
      }
      this.slide = swap1;
   }

   /**
    * Sets into tempMarble list the marbles player got from the market
    * @param row, true to take marbles from a row, false to take marbles from a column
    * @param index, index of the row/column from which take the marbles
    */
   public void setTempMarble(boolean row, int index){
      if(row)
         for(int i=0; i<market[0].length; i++)
            this.tempMarble.add(market[index-1][i]);
      else
         for(int i=0; i<market.length; i++)
            this.tempMarble.add(market[i][index-1]);
   }

   /**
    * Discards by index the marble passed as parameter from the tempMarble list
    * @param marbleIndex, index of the marble to discard
    */
   public void removeTempMarble(int marbleIndex){
      this.tempMarble.remove(marbleIndex - 1);
   }

   /**
    * Updates Lorenzo's track position
    * @param lorenzoTrackPosition, updated position of Lorenzo
    */
   public void updateLorenzoPosition(int lorenzoTrackPosition) {
      this.lorenzoTrackPosition = lorenzoTrackPosition;
   }
   //----------UPDATE-----------


   //----------GETTERS----------
   public MarbleColor[][] getMarket() {
      MarbleColor[][] marketCloned = new MarbleColor[market.length][market[0].length];
      for(int i=0; i< market.length; i++)
         for(int j=0; j< market[0].length; j++)
            marketCloned[i][j] = MarbleColor.getMarbleColorByColor(market[i][j].getColor());

      return marketCloned;
   }

   public MarbleColor getSlide() {
      return MarbleColor.getMarbleColorByColor(slide.getColor());
   }

   public List<MarbleColor> getTempMarble() {
      return new ArrayList<>(tempMarble);
   }

   public int getLorenzoTrackPosition() {
      return lorenzoTrackPosition;
   }
   //----------GETTERS----------

   /**
    * Returns visible cards (the ones on top of the card square)
    * @return a matrix of DevelopCard
    */
   public Integer[][] visibleCards() {
      Integer[][] temp = new Integer[NUMBER_OF_DECK_ROWS][NUMBER_OF_DECK_COLUMS];
      for (int i = 0; i < developCardDeck.length; i++) {
         for (int j = 0; j < developCardDeck[i].length; j++) {
            if(!developCardDeck[i][j].isEmpty())
               temp[i][j] = developCardDeck[i][j].get(developCardDeck[i][j].size() - 1);
            else
               temp[i][j] = null;
         }
      }
      return temp;
   }
}
