package it.polimi.ingsw.view;

import it.polimi.ingsw.model.DevelopCardDeck;

public interface LorenzoViewInterface {

  public void displayLorenzoDiscarded(DevelopCardDeck.DevelopCardDeckUpdate state);
  public void displayLorenzoMoved();
  public void displayLorenzoShuffled();
  public void displayGameEnded(String payload);

}