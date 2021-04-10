package it.polimi.ingsw.utility;

import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.leadercard.*;
import it.polimi.ingsw.model.track.LorenzoTrack;
import it.polimi.ingsw.model.track.Track;

import java.io.*;


public class GSON{

   public static DevelopCardDeck cardParser(File file) throws IOException {
      GsonBuilder builder = new GsonBuilder();
      //builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
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
      //builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
      Gson gson = builder.create();
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(inputStream);
      Track track = gson.fromJson(reader, Track.class);
      reader.close();
      return track;
   }

   public static LorenzoTrack lorenzoTrackParser(File file) throws IOException {
      GsonBuilder builder = new GsonBuilder();
      //builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
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
              .registerSubtype(LeaderProduceBehaviour.class, "ProduceBehaviour")
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
      return leaderCardDeck;
   }

}



