package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.DevelopCardDeckSetup;

public interface DeckSetupObservable {

   /**
    * notify the model observers that deck has been set up
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyDeckSetup(DevelopCardDeckSetup msg);

}

