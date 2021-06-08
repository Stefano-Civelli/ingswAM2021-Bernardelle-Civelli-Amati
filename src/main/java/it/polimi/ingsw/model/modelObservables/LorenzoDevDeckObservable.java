package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.DevelopCardDeck;

public interface LorenzoDevDeckObservable {
   void notifyLorenzoDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate msg);
}
