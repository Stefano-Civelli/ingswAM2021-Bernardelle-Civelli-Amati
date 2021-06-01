package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;
import it.polimi.ingsw.view.cli.Color;

public class TrackDrawer implements Fillable, Buildable{
  private final int TRACK_LENGTH = 25*3;
  private final int TRACK_HEIGHT = 3;

  @Override
  public String[][] build() {
    String[][] trackSkeleton = new String[TRACK_HEIGHT][TRACK_LENGTH];
    int i;

    for(int k=0; k<TRACK_HEIGHT; k++)
      for(int w=0; w<TRACK_LENGTH; w++)
        trackSkeleton[k][w] = " ";

    trackSkeleton[1][0] = "[";
    for (i=1; i<TRACK_LENGTH-2; i++) {
      if(i%3==0)
        trackSkeleton[1][i] = "[";
      if(i%3==1)
        trackSkeleton[1][i] = " ";
      if(i%3==2)
        trackSkeleton[1][i] = "]";
    }
    trackSkeleton[1][i] = " ";
    i++;
    trackSkeleton[1][i] = ("]");

    for(int c=8*3; c<TRACK_LENGTH; c=c+8*3) {
      trackSkeleton[2][c] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
      trackSkeleton[2][c + 2] = Color.ANSI_RED.escape() + "│" + Color.RESET.escape();
    }

    addVp(trackSkeleton);
    colorCells(trackSkeleton);

    return trackSkeleton;
  }

  private void addVp(String[][] track) {
    track[0][0] = "v";
    track[0][1] = "p";
    track[0][2] = "\u25C6";
    track[0][3*3+1] = "1";
    track[0][6*3+1] = "2";
    track[0][9*3+1] = "4";
    track[0][12*3+1] = "6";
    track[0][15*3+1] = "9";
    track[0][18*3+1] = "1";
    track[0][18*3+2] = "2";
    track[0][21*3+1] = "1";
    track[0][21*3+2] = "6";
    track[0][24*3+1] = "2";
    track[0][24*3+2] = "0";
  }

  private void colorCells(String[][] trackAndVatican) {
    trackAndVatican[1][8*3] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3] + Color.RESET.escape();
    trackAndVatican[1][8*3+2] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3+2] + Color.RESET.escape();
    trackAndVatican[1][16*3] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3] + Color.RESET.escape();
    trackAndVatican[1][16*3+2] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3+2] + Color.RESET.escape();
    trackAndVatican[1][24*3] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3] + Color.RESET.escape();
    trackAndVatican[1][24*3+2] = Color.ANSI_RED.escape() + trackAndVatican[1][8*3+2] + Color.RESET.escape();

    trackAndVatican[1][23*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][23*3] + Color.RESET.escape();
    trackAndVatican[1][23*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][23*3+2] + Color.RESET.escape();
    trackAndVatican[1][22*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][22*3] + Color.RESET.escape();
    trackAndVatican[1][22*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][22*3+2] + Color.RESET.escape();
    trackAndVatican[1][21*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][21*3] + Color.RESET.escape();
    trackAndVatican[1][21*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][21*3+2] + Color.RESET.escape();
    trackAndVatican[1][20*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][20*3] + Color.RESET.escape();
    trackAndVatican[1][20*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][20*3+2] + Color.RESET.escape();
    trackAndVatican[1][19*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][19*3] + Color.RESET.escape();
    trackAndVatican[1][19*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][19*3+2] + Color.RESET.escape();

    trackAndVatican[1][15*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][15*3] + Color.RESET.escape();
    trackAndVatican[1][15*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][15*3+2] + Color.RESET.escape();
    trackAndVatican[1][14*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][14*3] + Color.RESET.escape();
    trackAndVatican[1][14*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][14*3+2] + Color.RESET.escape();
    trackAndVatican[1][13*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][13*3] + Color.RESET.escape();
    trackAndVatican[1][13*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][13*3+2] + Color.RESET.escape();
    trackAndVatican[1][12*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][12*3] + Color.RESET.escape();
    trackAndVatican[1][12*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][12*3+2] + Color.RESET.escape();

    trackAndVatican[1][7*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][7*3] + Color.RESET.escape();
    trackAndVatican[1][7*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][7*3+2] + Color.RESET.escape();
    trackAndVatican[1][6*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][6*3] + Color.RESET.escape();
    trackAndVatican[1][6*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][6*3+2] + Color.RESET.escape();
    trackAndVatican[1][5*3] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][5*3] + Color.RESET.escape();
    trackAndVatican[1][5*3+2] = Color.ANSI_BRIGHT_YELLOW.escape() + trackAndVatican[1][5*3+2] + Color.RESET.escape();
  }

  @Override
  public void fill(String[][] fillMe, SimplePlayerState playerState) {
    int playerPosition = playerState.getTrackPosition();
    boolean[] popeCards = playerState.getVaticanFlipped();

    for(int i=0, c=8; i<popeCards.length; i++, c+=8) {
      if (popeCards[i]) {
        fillMe[2][c * 3 + 1] = Integer.toString(i + 2);
      } else {
        fillMe[2][c * 3 + 1] = Color.ANSI_RED.escape() + "X" + Color.RESET.escape();
      }
    }

    fillMe[1][playerPosition*3+1] = Color.ANSI_RED.escape() + "+" + Color.RESET.escape();
  }

  @Override
  public void fill(String[][] fillMe, SimpleGameState gameState) {}
}
