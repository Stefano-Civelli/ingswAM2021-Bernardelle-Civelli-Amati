package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;

public class LeaderProduceBehaviour extends CardBehaviour{

   private ResourceType resourceToRemove;

   @Override
   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException, NotEnoughResourcesException {

      Chest chest = playerboard.getChest();
      Warehouse warehouse = playerboard.getWarehouse();

      //TODO
      // playerboard.getWarehouse().removeResources();
      //se non sono riuscito da warehouse rimuovo da chest
      //se non riesco ancora throw eccezione (mi basta inoltrare quella della chest/magazzino cioè not enough resources)
      playerboard.getTrack().moveForward(1);
      chest.addResources(resourceToAdd,1);
   }

}
