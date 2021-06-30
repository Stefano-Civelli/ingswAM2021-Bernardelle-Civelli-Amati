package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

public interface DevDeckUpdateObservable {

   void notifyDeckUpdate(DevelopCardDeckUpdate msg);

}
