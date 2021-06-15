package it.polimi.ingsw.model.updateContainers;

import java.util.List;

public class DevelopCardDeckSetup {
   private final List<Integer>[][] devDeck;

   public DevelopCardDeckSetup(List<Integer>[][] devDeck) {
      this.devDeck = devDeck;
   }

   public List<Integer>[][] getDevDeck() {
      return devDeck;
   }
}
