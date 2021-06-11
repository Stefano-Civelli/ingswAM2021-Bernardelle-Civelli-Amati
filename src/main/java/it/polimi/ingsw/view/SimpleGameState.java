package it.polimi.ingsw.view;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SimpleGameState {

   private final int NUMBER_OF_DECK_ROWS = 3;
   private final int NUMBER_OF_DECK_COLUMS = 4;

   private List<Integer>[][] developCardDeck; //identified by ID
   private MarbleColor[][] market;
   private MarbleColor slide;
   private List<MarbleColor> tempMarble;
   private int lorenzoTrackPosition = 0;
   private LorenzoState lorenzoState;

   public SimpleGameState() {
      tempMarble = new ArrayList<>();
   }

   public void constructDeck(DevelopCardDeck.DevelopCardDeckSetup stateSetup) {
      this.developCardDeck = stateSetup.getDevDeck();
   }

   public void constructMarket (Market.MarketSetup stateSetup) {
      this.market = stateSetup.getMarbleMatrix();
      this.slide = stateSetup.getSlide();
   }

   //----------UPDATE-----------
   public void updateDeck(DevelopCardDeck.DevelopCardDeckUpdate stateUpdate) {
      int row = stateUpdate.getRow();
      int column = stateUpdate.getColumn();

      developCardDeck[row][column].remove(developCardDeck[row][column].size()-1);
   }

   public void updateMarket(Market.MarketUpdate stateUpdate) {
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

   public void setTempMarble(boolean row, int index){
      if(row)
         for(int i=0; i<market[0].length; i++)
            this.tempMarble.add(market[index-1][i]);
      else
         for(int i=0; i<market.length; i++)
            this.tempMarble.add(market[i][index-1]);
   }

   public void removeTempMarble(int marbleIndex){
      this.tempMarble.remove(marbleIndex - 1);
   }

   public void updateLorenzoPosition(int lorenzoTrackPosition) {
      this.lorenzoTrackPosition = lorenzoTrackPosition;
   }
   //----------UPDATE-----------


   //----------GETTERS----------
   //TODO clone
   public MarbleColor[][] getMarket() {
      return market;
   }

   public MarbleColor getSlide() {
      return slide;
   }

   public List<MarbleColor> getTempMarble() {
      return new ArrayList<>(tempMarble);
   }

   public int getLorenzoTrackPosition() {
      return lorenzoTrackPosition;
   }
   //----------GETTERS----------

   /**
    * returns the visible cards (the ones on top of the card square)
    *
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
