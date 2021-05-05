package it.polimi.ingsw.view;

import it.polimi.ingsw.utility.GSON;

import java.util.List;

public class SimpleGameState {

   private List<Integer>[][] developCardDeck; //identified by ID
   private Integer[][] market;


   public void constructDeck(String jsonDeck) {
      developCardDeck = GSON.getGsonBuilder().fromJson(jsonDeck, List[][].class);
      System.out.println(developCardDeck);
   }

   public void constructMarket() {

   }
}
