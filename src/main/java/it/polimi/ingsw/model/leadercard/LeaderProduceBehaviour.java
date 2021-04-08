package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;

public class LeaderProduceBehaviour extends CardBehaviour{

   private ResourceType resourceToRemove;

   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException {

      Chest chest = playerboard.getChest();
      Warehouse warehouse = playerboard.getWarehouse();

      //TODO
      // playerboard.getWarehouse().removeResources();
      //se non sono riuscito da warehouse rimuovo da chest
      //se non riesco ancora throw eccezione (mi basta inoltrare quella della chest/magazzino)
      playerboard.getTrack().moveForward(1);
      chest.addResources(resourceToAdd,1);
   }

}
