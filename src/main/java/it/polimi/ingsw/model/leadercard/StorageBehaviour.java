package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.MaxLeaderCardLevelsException;

public class StorageBehaviour extends CardBehaviour{

   private ResourceType storageType;

   public StorageBehaviour() {}

   public StorageBehaviour(ResourceType storageType) {
      this.storageType = storageType;
   }

   @Override
   public void createStorage(InterfacePlayerBoard playerboard) throws MaxLeaderCardLevelsException {
      try {
         playerboard.getWarehouse().addLeaderCardLevel(storageType);
      } catch (AbuseOfFaithException e) {e.printStackTrace();}
   }
}
