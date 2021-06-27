package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckSetup;

public interface DeckSetupObservable {

   void notifyDeckSetup(DevelopCardDeckSetup msg);

}

