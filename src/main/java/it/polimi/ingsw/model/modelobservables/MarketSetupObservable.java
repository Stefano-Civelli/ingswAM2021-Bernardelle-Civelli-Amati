package it.polimi.ingsw.model.modelobservables;


import it.polimi.ingsw.model.updatecontainers.MarketSetup;

public interface MarketSetupObservable {

   void notifyMarketSetup(MarketSetup msg);

}
