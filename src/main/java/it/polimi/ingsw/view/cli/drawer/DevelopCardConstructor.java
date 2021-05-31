package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class DevelopCardConstructor {
  private static final int DEV_HEIGHT = 4;
  private static final int DEV_LENGTH = 11;
  private static DevelopCardDeck developCardDeck;

  static {
    try {
      developCardDeck = GSON.cardParser(ConfigParameters.cardConfigFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String[][] constructLeaderFromId(int id) {
    String[][] develop = new String[DEV_HEIGHT][DEV_LENGTH];
    String[][] margin = MarginConstructor.buildMargins(4, 11);
    int r=1, c=1;

    for(int i=0; i<margin.length; i++)
      for(int j=0; j<margin[0].length; j++)
        develop[i][j] = margin[i][j];

    try {
      DevelopCard d = DevelopCardConstructor.getDevelopCardFromId(id);
      DevelopCardColor color = d.getCardFlag().getColor();
      int level = d.getCardFlag().getLevel();
      int victoryPoints = d.getVictoryPoints();
      Map<ResourceType, Integer> cost = d.getCost();
      Map<ResourceType, Integer> requirements = d.getRequirement();
      Map<ResourceType, Integer> products = d.getProduct();


      for(int i=0; i<develop.length; i++)
        for(int j=0; j<develop[0].length; j++)
          develop[i][j] = " ";

      addColoredMargins(develop, color);
      addLevel(develop, level);
      addVictoryPoints(develop, victoryPoints);


      for(Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
        develop[r][c] = entry.getKey().getColor().getColor() + entry.getValue().toString() + Color.RESET.escape();
        c+=2;
      }

      c=1;
      r=2;
      for(Map.Entry<ResourceType, Integer> entry : requirements.entrySet()) {
        develop[r][c] = entry.getKey().getColor().getColor() + entry.getValue().toString() + Color.RESET.escape();
        c+=2;
      }

      develop[r][c-1] = ConfigParameters.arrowCharacter;

      for(Map.Entry<ResourceType, Integer> entry : products.entrySet()) {
        develop[r][c] = entry.getKey().getColor().getColor() + entry.getValue().toString() + Color.RESET.escape();
        c+=2;
      }
    } catch (InvalidCardException e) {}
    return develop;
  }

  public static DevelopCard getDevelopCardFromId(int cardId) throws InvalidCardException {
    return developCardDeck.getCardFromId(cardId);
  }

  private static void addColoredMargins(String[][] cardSkeleton, DevelopCardColor color) {
    String[][] cardMargins = MarginConstructor.buildMargins(4, 11);
    String cardColor = Color.RESET.escape();

    switch (color) {
      case BLUE:
        cardColor = Color.ANSI_BLUE.escape();
        break;
      case YELLOW:
        cardColor = Color.ANSI_YELLOW.escape();
        break;
      case PURPLE:
        cardColor = Color.ANSI_PURPLE.escape();
        break;
      case GREEN:
        cardColor = Color.ANSI_GREEN.escape();
        break;
    }

    for (int i=0; i < cardMargins.length; i++)
      for (int j=0; j < cardMargins[i].length; j++)
        cardSkeleton[i][j] = cardColor + cardMargins[i][j] + Color.RESET.escape();
  }

  private static void addLevel(String[][] cardSkeleton, int level) {
    int col = 1;

    cardSkeleton[0][col] = " ";
    col++;
    for(int i=0; i<level-1; i++, col++)
      cardSkeleton[0][col] = "I";
    cardSkeleton[0][col] = " ";
  }

  private static void addVictoryPoints(String[][] cardSkeleton, int victoryPoint) {
    if(victoryPoint > 9) {
      cardSkeleton[3][5] = " ";
      cardSkeleton[3][6] = "\u25C6";
      cardSkeleton[3][7] = Integer.toString(victoryPoint/10);
      cardSkeleton[3][8] = Integer.toString(victoryPoint%10);
      cardSkeleton[3][9] = " ";
    }
    else {
      cardSkeleton[3][6] = " ";
      cardSkeleton[3][7] = "\u25C6";
      cardSkeleton[3][8] = Integer.toString(victoryPoint);
      cardSkeleton[3][9] = " ";
    }
  }
}
