package it.polimi.ingsw.model.modelObservables;


import it.polimi.ingsw.model.updateContainers.MarketSetup;

public interface MarketSetupObservable {

   void notifyMarketSetup(MarketSetup msg);

}
