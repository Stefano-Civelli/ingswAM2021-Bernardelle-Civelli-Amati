package it.polimi.ingsw.utility;

import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.leadercard.*;
import it.polimi.ingsw.model.track.LorenzoTrack;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.network.action.*;

import java.io.*;


public class GSON{

   public static DevelopCardDeck cardParser(File file) throws IOException {
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(inputStream);
      DevelopCardDeck developCardDeck = gson.fromJson(reader, DevelopCardDeck.class);
      reader.close();
      developCardDeck.setupClass();
      return developCardDeck;
   }

   public static Track trackParser(File file) throws IOException {
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(inputStream);
      Track track = gson.fromJson(reader, Track.class);
      reader.close();
      return track;
   }

   public static LorenzoTrack lorenzoTrackParser(File file) throws IOException {
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(inputStream);
      LorenzoTrack lorenzoTrack = gson.fromJson(reader, LorenzoTrack.class);
      reader.close();
      return lorenzoTrack;
   }

   //"requiredResources": "NONE" creates a null ResourceType
   //"requiredCardFlags": [] creates an empty Map
   public static LeaderCardDeck leaderCardParser(File file) throws IOException {
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
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(inputStream);
      LeaderCardDeck leaderCardDeck = gson.fromJson(reader, LeaderCardDeck.class);
      reader.close();
      leaderCardDeck.shuffleLeaderList();
      return leaderCardDeck;
   }

   //in caso non funzioni passare il Type dell'action e usare switch per creare un Action sottoclassata
   public static Action buildAction(String payload){
      RuntimeTypeAdapterFactory<Action> actionAdapter = RuntimeTypeAdapterFactory.of(Action.class, "Type");
      actionAdapter
              .registerSubtype(InsertMarbleAction.class, "INSERT_MARBLE")
              .registerSubtype(ActivateLeaderAction.class, "ACTIVATE_LEADER")
              .registerSubtype(BaseProductionAction.class, "BASE_PRODUCE")
              .registerSubtype(BuyDevelopCardAction.class, "BUY_CARD")
              .registerSubtype(DiscardLeaderAction.class, "DISCARD_LEADER")
              .registerSubtype(LeaderProductionAction.class, "LEADER_PRODUCE")
              .registerSubtype(ProductionAction.class, "PRODUCE")
              .registerSubtype(ShopMarketAction.class, "SHOP_MARKET");
      //man mano che si aggiungono sottoclassi di Action registarle anche qui

      GsonBuilder builder = new GsonBuilder()
              .enableComplexMapKeySerialization()
              .registerTypeAdapterFactory(actionAdapter);
      Gson gson = builder.create();

      Action action = gson.fromJson(payload, Action.class);

      return action;
   }
}



