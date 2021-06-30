package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.CardSlotUpdate;

public interface CardSlotObservable {

   /**
    * notify the model observers that a card has been added to a slot
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyCardSlotUpdate(CardSlotUpdate msg);

}
