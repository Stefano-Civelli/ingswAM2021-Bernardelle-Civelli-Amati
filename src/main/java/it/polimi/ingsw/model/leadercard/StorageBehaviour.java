package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.MaxLeaderCardLevelsException;

/**
 * The CardBehaviour that represents the producing behaviour: a leader card with this behaviour will add a new warehouse level
 */
public class StorageBehaviour extends CardBehaviour{

   private final ResourceType storageType;

   public StorageBehaviour(ResourceType storageType) {
      this.storageType = storageType;
   }

   @Override
   public void createStorage(InterfacePlayerBoard playerboard) throws MaxLeaderCardLevelsException {
      try {
         playerboard.getWarehouse().addLeaderCardLevel(storageType);
      } catch (AbuseOfFaithException e) {e.printStackTrace();}
   }

   @Override
   public ResourceType getResToStore() {
      return storageType;
   }
}
