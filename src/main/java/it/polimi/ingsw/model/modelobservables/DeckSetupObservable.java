package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckSetup;

public interface DeckSetupObservable {

   /**
    * notify the model observers that deck has been set up
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a develop card deck setup containing the whole develop card deck
    */
   void notifyDeckSetup(DevelopCardDeckSetup msg);

}

