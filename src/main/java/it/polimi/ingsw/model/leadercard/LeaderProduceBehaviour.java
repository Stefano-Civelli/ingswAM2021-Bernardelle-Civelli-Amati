package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NeedAResourceToAddException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;

public class LeaderProduceBehaviour extends CardBehaviour{

   private ResourceType resourceToRemove;

   public LeaderProduceBehaviour() {}

   public LeaderProduceBehaviour(ResourceType resourceToRemove) {
      this.resourceToRemove = resourceToRemove;
   }

   @Override
   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerBoard) throws AbuseOfFaithException, NotEnoughResourcesException, NeedAResourceToAddException {

      if(resourceToAdd == null){
         throw new NeedAResourceToAddException();
      }

      Chest chest = playerBoard.getChest();
      Warehouse warehouse = playerBoard.getWarehouse();
      int remainingToRemove = 0;
      try {
         remainingToRemove = warehouse.removeResources(resourceToRemove, 1);
      } catch (NegativeQuantityException e) {e.printStackTrace();}

      chest.removeResources(resourceToRemove, remainingToRemove);

      playerBoard.getTrack().moveForward(1);
      try {
         chest.addResources(resourceToAdd,1);
      } catch (NegativeQuantityException e) {e.printStackTrace();}
   }
}