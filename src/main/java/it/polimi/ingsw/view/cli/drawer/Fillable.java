package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimplePlayerState;

public interface Fillable {
  public String[][] fill(String[][] fillMe, SimplePlayerState playerState);
}
