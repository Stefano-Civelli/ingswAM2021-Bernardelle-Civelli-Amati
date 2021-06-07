package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.DevelopCardDeck;

public interface DeckSetupObservable {

   void notifyDeckSetup(DevelopCardDeck.DevelopCardDeckSetup msg);

}

