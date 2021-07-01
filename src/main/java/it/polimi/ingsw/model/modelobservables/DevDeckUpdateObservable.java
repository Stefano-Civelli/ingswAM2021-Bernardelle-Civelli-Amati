package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

public interface DevDeckUpdateObservable {

   /**
    * Notify the model observers that a card has been drawn from the deck
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a develop card deck update
    */
   void notifyDeckUpdate(DevelopCardDeckUpdate msg);

}
