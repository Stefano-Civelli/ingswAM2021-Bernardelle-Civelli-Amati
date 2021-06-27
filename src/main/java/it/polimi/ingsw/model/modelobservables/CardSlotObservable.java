package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.CardSlotUpdate;

public interface CardSlotObservable {
   void notifyCardSlotUpdate(CardSlotUpdate msg);
}
