package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

public interface LorenzoDevDeckObservable {

   /**
    * Notify the model observers that lorenzo drew cards from the deck
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a develop card deck update
    */
   void notifyLorenzoDeckUpdate(DevelopCardDeckUpdate msg);

}
