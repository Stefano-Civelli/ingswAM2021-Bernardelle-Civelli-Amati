package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.cli.Color;

public class LorenzoDrawer implements Buildable, Fillable {
  private final int LORENZO_LENGTH = 25*3 + 11*6 + 11 + 2 *2;
  private final int LORENZO_HEIGHT = 4;
  private TrackDrawer trackDrawer = new TrackDrawer();

  @Override
  public String[][] build() {
    String[][] lorenzo = new String[LORENZO_HEIGHT][LORENZO_LENGTH];
    String[][] track = trackDrawer.build();
    String[][] tokenMargin = MarginConstructor.buildMargins(4,11);
    int c,b=0;

    for(int i=0; i<lorenzo.length; i++)
      for(int j=0; j<lorenzo[0].length; j++)
        lorenzo[i][j] = " ";


    for(c=0; c<track[0].length; c++)
      lorenzo[2][c] = track[1][c];

    c+=2;

    while(b<6) {
      for (int i = 0; i < tokenMargin.length; i++)
        for (int j = 0, d=c; j < tokenMargin[0].length; j++, d++)
          lorenzo[i][d] = tokenMargin[i][j];
        b++;
        c++;
    }

    return lorenzo;
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) { /*does nothing*/ }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {
    int lorenzoPosition = gameState.getLorenzoTrackPosition();
    fillMe[2][lorenzoPosition*3+1] = Color.ANSI_RED.escape() + "+" + Color.RESET.escape();
  }
}
