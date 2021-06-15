package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.updateContainers.MarketUpdate;

public interface MarketUpdateObservable {
   void notifyMarketUpdate(MarketUpdate msg);

   }

