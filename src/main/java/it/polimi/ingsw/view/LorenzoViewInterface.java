package it.polimi.ingsw.view;

import it.polimi.ingsw.model.updateContainers.DevelopCardDeckUpdate;

public interface LorenzoViewInterface {

  public void displayLorenzoDiscarded(DevelopCardDeckUpdate state);
  public void displayLorenzoMoved();
  public void displayLorenzoShuffled();
  public void displayGameEnded(String payload);

}