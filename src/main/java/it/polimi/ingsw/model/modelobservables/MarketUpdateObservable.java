package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.MarketUpdate;

public interface MarketUpdateObservable {

   /**
    * Notify the model observers that a market row or column has been pushed
    * Along with the notification sends the proper update Object as further explained in project documentation
    *
    * @param msg the update object representing a market update
    */
   void notifyMarketUpdate(MarketUpdate msg);

}

