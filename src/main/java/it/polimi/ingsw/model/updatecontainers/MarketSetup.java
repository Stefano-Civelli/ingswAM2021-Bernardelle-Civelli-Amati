package it.polimi.ingsw.model.updatecontainers;

import it.polimi.ingsw.model.market.MarbleColor;

/**
 * A special model update used to communicate to clients or views the initial state of a {@link it.polimi.ingsw.model.market.Market market}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class MarketSetup implements ModelUpdate {

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
