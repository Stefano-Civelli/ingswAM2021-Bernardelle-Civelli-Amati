package it.polimi.ingsw.model.updatecontainers;

import java.util.List;

/**
 * A special model update used to communicate to clients or views the initial state of a {@link it.polimi.ingsw.model.DevelopCardDeck develop card deck}.
 * Model updates contain information to notify clients or views of an update happened on the model
 */
public class DevelopCardDeckSetup implements ModelUpdate {

   private final List<Integer>[][] devDeck;

   public DevelopCardDeckSetup(List<Integer>[][] devDeck) {
      this.devDeck = devDeck;
   }

   /**
    * returns the card deck as a matrix of ID lists
    * @return the card deck as a matrix of ID lists
    */
   public List<Integer>[][] getDevDeck() {
      return devDeck;
   }

}
