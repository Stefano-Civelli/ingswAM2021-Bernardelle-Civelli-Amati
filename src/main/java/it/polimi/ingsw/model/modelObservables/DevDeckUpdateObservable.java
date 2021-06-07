package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.CardSlots;
import it.polimi.ingsw.model.DevelopCardDeck;

public interface DevDeckUpdateObservable {
   void notifyDeckUpdate(DevelopCardDeck.DevelopCardDeckUpdate msg);
}
