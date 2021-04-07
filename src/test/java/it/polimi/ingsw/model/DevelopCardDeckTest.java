package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevelopCardDeckTest {

   File cardConfigFile = new File("src/DevelopCardConfig.json");

   @Test
   void developCardParseTest() throws AbuseOfFaithException {
      DevelopCardDeck developCardDeck;
      try {
         developCardDeck = GSON.cardParser(cardConfigFile);
         InterfacePlayerBoard playerBoard = new PlayerBoard("Mario", new ArrayList<LeaderCard>(), Market.getInstance(), developCardDeck);
         playerBoard.getChest().addResources(ResourceType.GOLD,3);
         playerBoard.getChest().mergeMapResources();
         for(DevelopCard d : developCardDeck.buyableCards(playerBoard))
            System.out.println(d.getCost());
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Test
   void developCardParseTest2(){

   }


}