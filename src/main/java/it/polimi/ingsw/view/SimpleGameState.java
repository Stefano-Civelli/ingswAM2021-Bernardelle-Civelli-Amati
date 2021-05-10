package it.polimi.ingsw.view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.lang.reflect.Type;
import java.util.List;


public class SimpleGameState {

   private List<Integer>[][] developCardDeck; //identified by ID
   private MarbleColor[][] market;
   private MarbleColor slide;


   public void constructDeck(String payload) {
      this.developCardDeck = GSON.getGsonBuilder().fromJson(payload, List[][].class);
   }


   public void updateDeck(String payload){
      Type token = new TypeToken<Pair<Integer, Integer>>(){}.getType();
      Pair<Integer, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      int row = pair.getKey();
      int column = pair.getValue();

      developCardDeck[row][column].remove(developCardDeck[row][column].size()-1);
   }


   public void constructMarket (String payload) {

      Type token = new TypeToken<Pair<MarbleColor[][], MarbleColor>>(){}.getType();
      Pair<MarbleColor[][], MarbleColor> pair = GSON.getGsonBuilder().fromJson(payload, token);
      this.market = pair.getKey();
      this.slide = pair.getValue();

//      JsonObject jsonObject = (JsonObject) JsonParser.parseString(jsonMarket);
//      this.market = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("key"), MarbleColor[][].class);
//      this.slide = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("value").getAsString(), MarbleColor.class);
   }

   public void updateMarket(String payload) {
      Type token = new TypeToken<Pair<Boolean, Integer>>(){}.getType();
      Pair<Boolean, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      boolean isRow = pair.getKey();
      int index = pair.getValue();
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



   //TODO clone
   public MarbleColor[][] getMarket() {
      return market;
   }

   public MarbleColor getSlide() {
      return slide;
   }
}
