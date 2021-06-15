package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.CardSlotUpdate;

public interface CardSlotObservable {
   void notifyCardSlotUpdate(CardSlotUpdate msg);
}
