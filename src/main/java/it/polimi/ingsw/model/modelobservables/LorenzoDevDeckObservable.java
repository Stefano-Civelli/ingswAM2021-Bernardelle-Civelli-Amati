package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckUpdate;

public interface LorenzoDevDeckObservable {

   /**
    * notify the model observers that lorenzo drew cards from the deck
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyLorenzoDeckUpdate(DevelopCardDeckUpdate msg);

}
