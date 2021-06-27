package it.polimi.ingsw.model.modelobservables;

import it.polimi.ingsw.model.updatecontainers.MarketUpdate;

public interface MarketUpdateObservable {
   void notifyMarketUpdate(MarketUpdate msg);

   }

