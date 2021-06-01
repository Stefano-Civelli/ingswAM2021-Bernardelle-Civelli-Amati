package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

import java.util.List;

public class CardSlotsDrawer implements Buildable, Fillable{
  private final int CARD_SLOTS_LENGTH = 49;
  private final int CARD_SLOTS_HEIGHT = 9;

  @Override
  public String[][] build() {
    String[][] cardSlotsSkeleton = new String[CARD_SLOTS_HEIGHT][CARD_SLOTS_LENGTH];
    String[][] slotMargins = MarginConstructor.buildMargins(8,13);
    int col=14;

    for(int i=0; i<cardSlotsSkeleton.length; i++)
      for(int j=0; j<cardSlotsSkeleton[0].length; j++)
        cardSlotsSkeleton[i][j] = " ";

    for(int i=0; i<slotMargins.length; i++)
      for(int j=0, b=10; j<slotMargins[0].length; j++, b++) {
        cardSlotsSkeleton[i][b] = slotMargins[i][j];
        cardSlotsSkeleton[i][b+13] = slotMargins[i][j];
        cardSlotsSkeleton[i][b+26] = slotMargins[i][j];
      }

    for(int i=0; i<3; i++) {
      for (char c : "slot ".toCharArray()) {
        cardSlotsSkeleton[cardSlotsSkeleton.length-1][col] = Character.toString(c);
        col++;
      }
      cardSlotsSkeleton[cardSlotsSkeleton.length-1][col-1] = Integer.toString(i+1);
      col+=8;
    }

    col=0;
    for(char c : "BASE".toCharArray()) {
      cardSlotsSkeleton[2][col] = Character.toString(c);
      col ++;
    }

    col=0;
    for(char c : "PROD.".toCharArray()) {
      cardSlotsSkeleton[3][col] = Character.toString(c);
      col ++;
    }

    cardSlotsSkeleton[5][0] = "?";
    cardSlotsSkeleton[5][2] = "?";
    cardSlotsSkeleton[5][4] = ConfigParameters.arrowCharacter;
    cardSlotsSkeleton[5][6] = "?";

    return cardSlotsSkeleton;
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {
    List<Integer>[] slots = playerState.getCardSlots();
    String[][] constructedCard;
    int a=fillMe.length-6, b=11;

    for(int i=0; i<slots.length; i++) {
      for (int j = 0; j < slots[i].size(); j++) {
        try {
          DevelopCard d = DevelopCardConstructor.getDevelopCardFromId(slots[i].get(j));
          constructedCard = DevelopCardConstructor.constructDevelopFromId(d.getCardId());

          for(int k=0, c=a; k<constructedCard.length; k++, c++)
            for(int w=0, e=b; w<constructedCard[0].length; w++, e++)
              fillMe[c][e] = constructedCard[k][w];

          a--;
        } catch (InvalidCardException e) {}
      }
      b=b+13;
      a=fillMe.length-6;
    }
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {}
}
