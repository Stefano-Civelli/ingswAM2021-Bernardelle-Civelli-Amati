package it.polimi.ingsw.utility;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.DevelopCard;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");
   @Test
   void parseTest3() {
      try {
         GSON.cardParser(cardConfigFile);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }


   }
