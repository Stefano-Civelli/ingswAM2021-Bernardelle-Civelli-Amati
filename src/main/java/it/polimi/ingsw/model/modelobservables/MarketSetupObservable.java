package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.MarketSetup;

public interface MarketSetupObservable {

   /**
    * notify the model observers that initial market setup has occurred
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyMarketSetup(MarketSetup msg);

}
