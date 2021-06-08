package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.CardSlots;
import it.polimi.ingsw.model.market.Market;

public interface CardSlotObservable {
   void notifyCardSlotUpdate(CardSlots.CardSlotUpdate msg);
}
