package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.DevelopCardDeckUpdate;

public interface DevDeckUpdateObservable {
   void notifyDeckUpdate(DevelopCardDeckUpdate msg);
}
