package it.polimi.ingsw.utility;

import com.google.gson.*;
import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.DevelopCardDeck;
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
      LorenzoTrack track = gson.fromJson(reader, LorenzoTrack.class);
      reader.close();
      return track;
   }
   }



