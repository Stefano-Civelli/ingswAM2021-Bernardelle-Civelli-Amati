package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.market.Market;

public interface MarketSetupObservable {

   void notifyMarketSetup(Market.MarketSetup msg);

}
