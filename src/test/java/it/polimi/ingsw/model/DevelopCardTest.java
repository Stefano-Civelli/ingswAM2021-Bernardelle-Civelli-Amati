package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


class DevelopCardTest {
   File leaderCardConfigFile = new File("src/LeaderCardConfig.json");

   @Test
   void Test() throws IOException{
      LeaderCardDeck leaderCardDeck;
      leaderCardDeck = GSON.leaderCardParser(leaderCardConfigFile);

   }




}