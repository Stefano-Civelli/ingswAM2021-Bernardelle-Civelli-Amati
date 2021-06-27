package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

public interface LorenzoDevDeckObservable {
   void notifyLorenzoDeckUpdate(DevelopCardDeckUpdate msg);
}
