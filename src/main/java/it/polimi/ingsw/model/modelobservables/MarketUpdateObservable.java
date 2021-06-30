package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.MarketUpdate;

public interface MarketUpdateObservable {

   /**
    * notify the model observers that a market row or column has been pushed
    * Along with the notification sends the proper update Object as further explained in project documentation
    */
   void notifyMarketUpdate(MarketUpdate msg);

}

