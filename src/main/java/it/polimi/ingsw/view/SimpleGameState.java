package it.polimi.ingsw.view;

import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.utility.GSON;

import java.util.List;

public class SimpleGameState {

   private List<Integer>[][] developCardDeck; //identified by ID
   private Integer[][] market;


   public void constructDeck(String jsonDeck) {
      developCardDeck = GSON.getGsonBuilder().fromJson(jsonDeck, List[][].class);
      for (List<Integer>[] x : developCardDeck)
         for (List<Integer> y : x)
            System.out.println(y);
      }

      public void constructMarket () {

      }
}
