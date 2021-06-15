package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.updateContainers.DevelopCardDeckSetup;

public interface DeckSetupObservable {

   void notifyDeckSetup(DevelopCardDeckSetup msg);

}

