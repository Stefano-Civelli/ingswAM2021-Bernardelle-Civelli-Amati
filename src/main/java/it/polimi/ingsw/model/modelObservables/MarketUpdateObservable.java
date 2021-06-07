package it.polimi.ingsw.model.modelObservables;

import it.polimi.ingsw.model.market.Market;

public interface MarketUpdateObservable {
   void notifyMarketUpdate(Market.MarketUpdate msg);

   }

