package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

public class MarketDrawer implements Fillable, Buildable{
  private static final int MARKET_LENGTH = 13;
  private static final int MARKET_HEIGHT = 9;

  @Override
  public String[][] build() {
    String[][] marketSkeleton = new String[MARKET_HEIGHT][MARKET_LENGTH];
    String[][] marketMargins = MarginConstructor.buildMargins(MARKET_HEIGHT-4, MARKET_LENGTH-2);
    int col, row;

    for(int i=0; i<marketSkeleton.length; i++)
      for(int j=0; j<marketSkeleton[0].length; j++) {
        if(i == marketSkeleton.length-1)
          marketSkeleton[i][j] = "\u203E";
          else
        marketSkeleton[i][j] = " ";
      }

    col=0;
    for(char c : "MARKET".toCharArray()) {
      marketSkeleton[0][col] = Character.toString(c);
      col ++;
    }

    for(int i=0, a=2; i<marketMargins.length; i++, a++)
      for(int j=0; j<marketMargins[0].length; j++)
        marketSkeleton[a][j] = marketMargins[i][j];

    col=0;
    for(char c : "slide".toCharArray()) {
      marketSkeleton[MARKET_HEIGHT-2][col] = Character.toString(c);
      col ++;
    }
    col = col + 2;
    marketSkeleton[MARKET_HEIGHT-2][col] = ConfigParameters.arrowCharacter;

    col = 2;
    row = 3;
    for(int i=0; i<4; i++) {
      marketSkeleton[1][col] = Integer.toString(i+1);
      if(i<3)
        marketSkeleton[row][MARKET_LENGTH-1] = Integer.toString(i+1);
      col += 2;
      row++;
    }

    return marketSkeleton;
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {
    MarbleColor[][] marketColor = gameState.getMarket();
    MarbleColor slide = gameState.getSlide();

    for(int i=3, a=0; a<marketColor.length; i++, a++)
      for (int j=2, b=0; b<marketColor[a].length; j+=2, b++)
          fillMe[i][j] = marketColor[a][b].toString();

    fillMe[MARKET_HEIGHT-2][MARKET_LENGTH-3] = slide.toString();
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {}
}
