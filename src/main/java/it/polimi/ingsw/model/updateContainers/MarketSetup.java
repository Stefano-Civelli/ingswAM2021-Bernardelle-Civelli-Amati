package it.polimi.ingsw.model.updateContainers;

import it.polimi.ingsw.model.market.MarbleColor;

public class MarketSetup {
   private final MarbleColor[][] marbleMatrix;
   private final MarbleColor slide;

   public MarketSetup(MarbleColor[][] marbleMatrix, MarbleColor slide) {
      this.marbleMatrix = marbleMatrix;
      this.slide = slide;
   }

   public MarbleColor[][] getMarbleMatrix() {
      return marbleMatrix;
   }

   public MarbleColor getSlide() {
      return slide;
   }
}
