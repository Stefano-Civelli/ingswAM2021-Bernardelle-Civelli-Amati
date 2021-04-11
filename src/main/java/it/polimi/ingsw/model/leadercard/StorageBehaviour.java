package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.LevelAlreadyPresentException;
import it.polimi.ingsw.model.modelexceptions.MaxLeaderCardLevelsException;

public class StorageBehaviour extends CardBehaviour{

   private ResourceType storageType;

   @Override
   public void createStorage(InterfacePlayerBoard playerboard) throws MaxLeaderCardLevelsException, LevelAlreadyPresentException, AbuseOfFaithException {
      playerboard.getWarehouse().addLeaderCardLevel(storageType);
   }
}
