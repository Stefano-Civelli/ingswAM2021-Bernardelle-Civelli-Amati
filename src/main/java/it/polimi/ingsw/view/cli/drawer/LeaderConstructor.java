package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.CardFlag;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Color;

import java.util.Map;

public class LeaderConstructor {
  private static final int LEADER_HEIGHT = 4;
  private static final int LEADER_LENGTH = 11;

  public static String[][] constructLeaderFromId(int id) {
    String[][] leader = new String[LEADER_HEIGHT][LEADER_LENGTH];
    String[][] margin = MarginConstructor.buildMargins(4,11);

    for(int i=0; i<margin.length; i++)
      for(int j=0; j<margin[0].length; j++)
        leader[i][j] = margin[i][j];

    try {
      LeaderCard l = Cli.getLeaderCardFromId(id);
      Map<CardFlag, Integer> requiredCardFlags = l.getRequiredCardFlags();
      ResourceType requiredResources = l.getRequiredResources();
      int victory = l.getVictoryPoints();
      int c = 2;

      //setto i vp e il costo all'interno della leader
      if (victory > 9) {
        leader[leader.length - 1][5] = " ";
        leader[leader.length - 1][6] = "\u25C6";
        leader[leader.length - 1][7] = Integer.toString(victory / 10);
        leader[leader.length - 1][8] = Integer.toString(victory % 10);
        leader[leader.length - 1][9] = " ";
      } else {
        leader[leader.length - 1][6] = " ";
        leader[leader.length - 1][7] = "\u25C6";
        leader[leader.length - 1][8] = Integer.toString(victory);
        leader[leader.length - 1][9] = " ";
      }

      if (requiredResources != null)
        leader[1][c] = requiredResources.getColor().getColor() + "5" + Color.RESET.escape();
      else {
        for (Map.Entry<CardFlag, Integer> entry : requiredCardFlags.entrySet()) {
          int quantity = entry.getValue();
          int column = entry.getKey().getColor().getColumn();

          if (entry.getKey().getLevel() == 0) {
            while (quantity > 0) {
              leader[1][c] = colorCardFlagChoice(column) + ConfigParameters.squareCharacter + Color.RESET.escape();
              quantity--;
              c+=2;
            }
          } else {
            leader[1][c] = colorCardFlagChoice(column) + ConfigParameters.squareCharacter;
            leader[1][c+1] = "l";
            leader[1][c+2] = "v";
            leader[1][c+3] = "2" + Color.RESET.escape();
          }
        }
      }

      c=1;
      //setto cosa fa la leader
      ResourceType resource = l.getResToDiscount();
      ResourceType resourceToRemove = l.getProductionRequirement();
      ResourceType onWhite = l.getWhite();
      ResourceType storageType = l.getResToStore();

      if(onWhite != null) {
        leader[2][c+4] = "=";
        leader[2][c+2] = ConfigParameters.marbleCharacter;
        leader[2][c+6] = onWhite.toString();
      }

      if(resourceToRemove != null) {
        leader[2][c+3] = ConfigParameters.arrowCharacter;
        leader[2][c+1] = resourceToRemove.getColor().getColor() + "1" + Color.RESET.escape();
        leader[2][c+5] = "?";
        leader[2][c+7] = Color.ANSI_RED.escape() + "1" + Color.RESET.escape();
      }

      if(storageType != null) {
        leader[2][c+5] = "|";
        leader[2][c+3] = "[";
        leader[2][c+7] = "]";
        leader[2][c+1] = storageType.toString();
      }

      if(resource != null) {
        leader[2][c+4] = "1";
        leader[2][c+3] = "-";
        leader[2][c+5] = resource.toString();
      }
    } catch (InvalidCardException e) {}
    return leader;
  }

  private static String colorCardFlagChoice(int column) {
    switch (column) {
      case 0:
        return Color.ANSI_GREEN.escape();
      case 1:
        return Color.ANSI_BLUE.escape();
      case 2:
        return Color.ANSI_YELLOW.escape();
      case 3:
        return Color.ANSI_PURPLE.escape();
    }
    return null;
  }
}