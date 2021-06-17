package it.polimi.ingsw.model.updateContainers;

import java.util.List;

/**
 * one of the updateContainers classes.
 * They contain the update information to be stored in the message payload
 */
public class DevelopCardDeckSetup {
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
