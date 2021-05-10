package it.polimi.ingsw.view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.GSON;

import java.util.List;


public class SimpleGameState {

   private List<Integer>[][] developCardDeck; //identified by ID
   private MarbleColor[][] market;
   private MarbleColor slide;


   public void constructDeck(String jsonDeck) {
      this.developCardDeck = GSON.getGsonBuilder().fromJson(jsonDeck, List[][].class);
   }

   public void constructMarket (String jsonMarket) {
      JsonObject jsonObject = (JsonObject) JsonParser.parseString(jsonMarket);
      this.market = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("key"), MarbleColor[][].class);
      this.slide = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("value").getAsString(), MarbleColor.class);

   }

   //TODO clone
   public MarbleColor[][] getMarket() {
      return market;
   }

   public MarbleColor getSlide() {
      return slide;
   }
}
