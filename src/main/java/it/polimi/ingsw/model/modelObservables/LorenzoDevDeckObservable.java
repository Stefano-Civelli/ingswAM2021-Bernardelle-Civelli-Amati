package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.DevelopCardDeckUpdate;

public interface LorenzoDevDeckObservable {
   void notifyLorenzoDeckUpdate(DevelopCardDeckUpdate msg);
}
