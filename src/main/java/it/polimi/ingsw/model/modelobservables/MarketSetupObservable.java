package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.MarketSetup;

public interface MarketSetupObservable {

   /**
    * Notify the model observers that initial market setup has occurred
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a market setup containing the whole market
    */
   void notifyMarketSetup(MarketSetup msg);

}
