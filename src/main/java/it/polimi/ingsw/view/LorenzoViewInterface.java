package it.polimi.ingsw.view;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

public interface LorenzoViewInterface {

  public void displayLorenzoDiscarded(DevelopCardDeckUpdate state);
  public void displayLorenzoMoved();
  public void displayLorenzoShuffled();
  public void displayGameEnded(String payload);

}