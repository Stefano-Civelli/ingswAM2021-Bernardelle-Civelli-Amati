package it.polimi.ingsw.utility;

import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.leadercard.*;
import it.polimi.ingsw.model.track.LorenzoTrack;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.controller.action.*;

import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;


public class GSON {

   private static final Gson gsonBuilder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

   public static Gson getGsonBuilder() {
      return gsonBuilder;
   }

   public static DevelopCardDeck cardParser() throws IOException {
      InputStreamReader reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/DevelopCardConfig.json"), StandardCharsets.UTF_8);
      DevelopCardDeck developCardDeck = gsonBuilder.fromJson(reader, DevelopCardDeck.class);
      reader.close();
      developCardDeck.setupClass();
      return developCardDeck;
   }

   public static Track trackParser() throws IOException {
      InputStreamReader reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/SquareConfig.json"), StandardCharsets.UTF_8);
      Track track = gsonBuilder.fromJson(reader, Track.class);
      reader.close();
      return track;
   }

   public static LorenzoTrack lorenzoTrackParser() throws IOException {
      InputStreamReader reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/SquareConfig.json"), StandardCharsets.UTF_8);
      LorenzoTrack lorenzoTrack = gsonBuilder.fromJson(reader, LorenzoTrack.class);
      reader.close();
      return lorenzoTrack;
   }

   //"requiredResources": "NONE" creates a null ResourceType
   //"requiredCardFlags": [] creates an empty Map
   public static LeaderCardDeck leaderCardParser() throws IOException {
      RuntimeTypeAdapterFactory<CardBehaviour> cardBehaviourAdapter = RuntimeTypeAdapterFactory.of(CardBehaviour.class, "type");
      cardBehaviourAdapter
              .registerSubtype(MarbleModifierBehaviour.class, "MarbleModifierBehaviour")
              .registerSubtype(LeaderProduceBehaviour.class, "LeaderProduceBehaviour")
              .registerSubtype(DiscountBehaviour.class, "DiscountBehaviour")
              .registerSubtype(StorageBehaviour.class, "StorageBehaviour");

      GsonBuilder builder = new GsonBuilder()
              .enableComplexMapKeySerialization()
              .registerTypeAdapterFactory(cardBehaviourAdapter);
      Gson gson = builder.create();
      InputStreamReader reader;
      if(ConfigParameters.TESTING) {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/LeaderCardConfig0Requirements.json"), StandardCharsets.UTF_8);
      }
      else {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/LeaderCardConfig.json"), StandardCharsets.UTF_8);
      }
      LeaderCardDeck leaderCardDeck = gson.fromJson(reader, LeaderCardDeck.class);
      reader.close();
      leaderCardDeck.shuffleLeaderList();
      return leaderCardDeck;
   }

   //in caso non funzioni passare il Type dell'action e usare switch per creare un Action sottoclassata
   public static Action buildAction(String payload){
      RuntimeTypeAdapterFactory<Action> actionAdapter = RuntimeTypeAdapterFactory.of(Action.class, "type");
      actionAdapter
              .registerSubtype(EndTurnAction.class, "END_TURN")
              .registerSubtype(InsertMarbleAction.class, "INSERT_MARBLE")
              .registerSubtype(ActivateLeaderAction.class, "ACTIVATE_LEADER")
              .registerSubtype(BaseProductionAction.class, "BASE_PRODUCE")
              .registerSubtype(BuyDevelopCardAction.class, "BUY_CARD")
              .registerSubtype(DiscardLeaderAction.class, "DISCARD_LEADER")
              .registerSubtype(LeaderProductionAction.class, "LEADER_PRODUCE")
              .registerSubtype(ProductionAction.class, "PRODUCE")
              .registerSubtype(ShopMarketAction.class, "SHOP_MARKET")
              .registerSubtype(ChooseInitialResourcesAction.class, "SETUP_CHOOSE_RESOURCES")
              .registerSubtype(ChooseLeaderOnWhiteMarbleAction.class, "CHOOSE_WHITE_LEADER")
              .registerSubtype(DiscardInitialLeaderAction.class, "SETUP_DISCARD_LEADERS");
      //man mano che si aggiungono sottoclassi di Action registarle anche qui

      GsonBuilder builder = new GsonBuilder()
              .enableComplexMapKeySerialization()
              .registerTypeAdapterFactory(actionAdapter);
      Gson gson = builder.create();

      Action action = gson.fromJson(payload, Action.class);

      return action;
   }
}



