package it.polimi.ingsw.model;

import it.polimi.ingsw.utility.GSON;

import java.io.File;
import java.io.IOException;

public class Game {


   public void gameModelGenerator(){
   //file path to construct the DevelopCardDeck class from JSON config file
   File cardConfigFile = new File("C:\\Users\\PC\\IdeaProjects\\ingswAM2021-Bernardelle-Civelli-Amati\\src\\DevelopCardConfig.json");
   try {
      GSON.cardParser(cardConfigFile);
   } catch (IOException e) {
      e.printStackTrace();
   }
   }

}
