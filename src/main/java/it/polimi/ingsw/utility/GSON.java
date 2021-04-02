package it.polimi.ingsw.utility;

import com.google.gson.*;
import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.DevelopCardDeck;

import java.io.*;

public class GSON{
   public static void cardParser(File file) throws IOException {
      GsonBuilder builder = new GsonBuilder();
      //builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
      Gson gson = builder.create();
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(inputStream);
      DevelopCardDeck developCardDeck = gson.fromJson(reader, DevelopCardDeck.class);

      System.out.println(developCardDeck.getDevelopCard().getCardFlag());
      System.out.println(developCardDeck.getDevelopCard().getCost());

//      for (String property : user.getProperties().keySet()) {
//         System.out.println("Key: " + property + " value: " + user.getProperties().get(property));
//      }
      reader.close();
   }
   }



