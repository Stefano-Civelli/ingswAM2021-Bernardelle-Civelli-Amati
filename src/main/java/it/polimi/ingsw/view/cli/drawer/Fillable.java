package it.polimi.ingsw.view.cli.drawer;

import it.polimi.ingsw.view.SimpleGameState;
import it.polimi.ingsw.view.SimplePlayerState;

public interface Fillable {
  public void fill(String[][] fillMe, SimplePlayerState playerState);
  public void fill(String[][] fillMe, SimpleGameState gameState);
}
