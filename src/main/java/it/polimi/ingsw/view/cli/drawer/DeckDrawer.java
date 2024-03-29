package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.cli.Color;

/**
 * Class that builds and fills the Cli representation of the Develop Card Deck
 */
public class DeckDrawer implements Fillable, Buildable{
  private final int DECK_LENGTH = 44;
  private final int DECK_HEIGHT = 13;

  @Override
  public String[][] build() {
    String[][] deckSkeleton = new String[DECK_HEIGHT][DECK_LENGTH];
    int r=4, c=11;

    for (int i=0; i<deckSkeleton.length; i++)
      for (int j=0; j<deckSkeleton[0].length; j++)
        deckSkeleton[i][j] = " ";

    for (int i=0; i<deckSkeleton.length-1; i++)
      for (int j=0; j<deckSkeleton[0].length; j++) {
        if(j<c)
          deckSkeleton[i+1][j] = Color.ANSI_GREEN.escape() + MarginConstructor.buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
        else if(j<c*2)
          deckSkeleton[i+1][j] = Color.ANSI_BLUE.escape() + MarginConstructor.buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
        else if(j<c*3)
          deckSkeleton[i+1][j] = Color.ANSI_YELLOW.escape() + MarginConstructor.buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
        else
          deckSkeleton[i+1][j] = Color.ANSI_PURPLE.escape() + MarginConstructor.buildMargins(r, c)[i % r][j % c] + Color.RESET.escape();
      }

    for (int i=1; i<deckSkeleton.length; i+=r)
      for (int j=1; j<deckSkeleton[0].length; j+=c) {
        deckSkeleton[i][j] = " ";
        if(i == r*2+1) {
          deckSkeleton[i][j+1] = "I";
          deckSkeleton[i][j+2] = "I";
          deckSkeleton[i][j+3] = "I";
          deckSkeleton[i][j+4] = " ";
        }
        if(i == r+1) {
          deckSkeleton[i][j+1] = "I";
          deckSkeleton[i][j+2] = "I";
          deckSkeleton[i][j+3] = " ";
        }
        if(i == 1) {
          deckSkeleton[i][j+1] = "I";
          deckSkeleton[i][j+2] = " ";
        }
      }
    return deckSkeleton;
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {
    int col = 0;
    Integer[][] cards = gameState.visibleCards();

    for (char c : "DEV DECK ".toCharArray()) {
      fillMe[0][col] = Character.toString(c);
      col++;
    }

    for (int i = 0, a = 2; i < cards.length; i++, a+=4) {
      for (int j = 0, b = 1; j < cards[0].length; j++, b += 11) {
        if(cards[i][j]!=null) {
          String[][] card = DevelopCardConstructor.constructDevelopFromId(cards[i][j]);
          for (int k = a, p = 1; p < card.length; k++, p++)
            for (int s = 1, w = b; s < card[0].length - 1; w++, s++)
              fillMe[k][w] = card[p][s];
        }
        else {
          drawPlainCard(fillMe, i, j);
        }
      }
    }
  }

  private void drawPlainCard(String[][] fillMe,int i ,int j) {
    for(; i<4 ;i++)
      for(; j<11 ;j++)
        fillMe[i][j] = " ";
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {/*does nothing*/}
}
