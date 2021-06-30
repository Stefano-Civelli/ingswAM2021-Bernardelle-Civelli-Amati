package it.polimi.ingsw.utility;

import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.leadercard.*;
import it.polimi.ingsw.model.track.LorenzoTrack;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.view.cli.Color;

import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

/**
 * Contains all the possible method used to parse to and from a json string.
 * Can be used for loading configuration files or for sending and receiving messages to and from the network
 */
public final class GSON {

   private static final Gson gsonBuilder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
   private static Gson actionBuilder = null;

   /**
    * Static method to obtain a GsonBuilder with the necessary modifiers enebled
    *
    * @return the gsonBuilder
    */
   public static Gson getGsonBuilder() {
      return gsonBuilder;
   }

   /**
    * construct and setup a DevelopCardDeck class from a JSON config file
    *
    * @return the constructed DevelopCardDeck
    * @throws IOException if it is unable to read the configuration file
    */
   public static DevelopCardDeck cardParser() throws IOException {
      InputStreamReader reader = null;
      try {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/DevelopCardConfig.json"), StandardCharsets.UTF_8);
      }catch (NullPointerException e){
         fatalErrorWhenLoadingJSON("DevelopCardDeck");
      }
      DevelopCardDeck developCardDeck = gsonBuilder.fromJson(reader, DevelopCardDeck.class);
      reader.close();
      developCardDeck.setupClass();
      return developCardDeck;
   }

   /**
    * construct track class from a JSON config file
    *
    * @return the constructed Track
    * @throws IOException if it is unable to read the configuration file
    */
   public static Track trackParser() throws IOException {
      InputStreamReader reader = null;
      try {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/SquareConfig.json"), StandardCharsets.UTF_8);
      }catch (NullPointerException e){
         fatalErrorWhenLoadingJSON("Track");
      }
      Track track = gsonBuilder.fromJson(reader, Track.class);
      reader.close();
      return track;
   }

   /**
    * construct LorenzoTrack class from a JSON config file
    *
    * @return the constructed LorenzoTrack
    * @throws IOException if it is unable to read the configuration file
    */
   public static LorenzoTrack lorenzoTrackParser() throws IOException {
      InputStreamReader reader = null;
      try {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/SquareConfig.json"), StandardCharsets.UTF_8);
      }catch (NullPointerException e){
         fatalErrorWhenLoadingJSON("Track");
      }
      LorenzoTrack lorenzoTrack = gsonBuilder.fromJson(reader, LorenzoTrack.class);
      reader.close();
      return lorenzoTrack;
   }

   /**
    * construct and setup LeaderCardDeck class from a JSON config file
    *
    * @return the constructed LeaderCardDeck
    * @throws IOException if it is unable to read the configuration file
    */
   public static LeaderCardDeck leaderCardParser() throws IOException {
      //"requiredResources": "NONE" creates a null ResourceType
      //"requiredCardFlags": [] creates an empty Map
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
      InputStreamReader reader = null;
      try {
         if (ConfigParameters.TESTING) {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/LeaderCardConfig0Requirements.json"), StandardCharsets.UTF_8);
      } else {
         reader = new InputStreamReader(GSON.class.getResourceAsStream("/configfiles/LeaderCardConfig.json"), StandardCharsets.UTF_8);
      }
      }catch (NullPointerException e){
         fatalErrorWhenLoadingJSON("LeaderCard");
      }
      LeaderCardDeck leaderCardDeck = gson.fromJson(reader, LeaderCardDeck.class);
      reader.close();
      leaderCardDeck.shuffleLeaderList();
      return leaderCardDeck;
   }

   /**
    * constructs an ACTION from the given JSON payload
    *
    * @param payload JSON representation of the ACTION to be constructed
    * @return the newly created action
    */
   public static Action buildAction(String payload) {
      return getActionBuilder().fromJson(payload, Action.class);
   }

   private static Gson getActionBuilder() {
      if (actionBuilder != null)
         return actionBuilder;

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
      //all ACTION subclasses should be registered here

      GsonBuilder builder = new GsonBuilder()
              .enableComplexMapKeySerialization()
              .registerTypeAdapterFactory(actionAdapter);

      actionBuilder = builder.create();

      return actionBuilder;
   }

   private static void fatalErrorWhenLoadingJSON(String missingJsonName){
      System.out.println(Color.ANSI_RED.escape() + "fatal error on server: unable to locate " + missingJsonName + " JSON file " + Color.RESET.escape());
      System.exit(0);
   }

   private GSON() {
      // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
   }
}