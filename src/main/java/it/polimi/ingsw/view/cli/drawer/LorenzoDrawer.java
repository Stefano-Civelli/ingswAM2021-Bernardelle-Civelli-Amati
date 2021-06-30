package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.cli.Color;

/**
 * Class that builds and fills the Cli representation of Lorenzo il Magnifico
 * Builds and Fills the track and the last token played
 */
public class LorenzoDrawer implements Buildable, Fillable {
  private final int LORENZO_LENGTH = 25*3 + 11 + 6 + 11 + 2 + 13;
  private final int LORENZO_HEIGHT = 4;
  private final TrackDrawer trackDrawer = new TrackDrawer();

  @Override
  public String[][] build() {
    String[][] lorenzo = new String[LORENZO_HEIGHT][LORENZO_LENGTH];
    String[][] track = trackDrawer.build();
    String[][] tokenMargin = MarginConstructor.buildMargins(4,11);
    int c=0,b=0;

    for(int i=0; i<lorenzo.length; i++)
      for(int j=0; j<lorenzo[0].length; j++)
        lorenzo[i][j] = " ";

    for(char character : "LORENZO TRACK".toCharArray()) {
      lorenzo[1][c] = Character.toString(character);
      c ++;
    }


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

    c+=13;
    for (int i = 0; i < tokenMargin.length; i++)
      for (int j = 0, d=c; j < tokenMargin[0].length; j++, d++)
        lorenzo[i][d] = tokenMargin[i][j];

    return lorenzo;
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {
    int lorenzoPosition = gameState.getLorenzoTrackPosition();
    fillMe[2][lorenzoPosition*3+1] = Color.ANSI_RED.escape() + "+" + Color.RESET.escape();
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) { /*does nothing*/ }
}
