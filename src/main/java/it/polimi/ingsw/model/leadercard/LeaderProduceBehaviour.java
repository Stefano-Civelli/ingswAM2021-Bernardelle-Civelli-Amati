package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.Chest;
import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NegativeQuantityException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;

public class LeaderProduceBehaviour extends CardBehaviour{

   private ResourceType resourceToRemove;

   public LeaderProduceBehaviour() {}

   public LeaderProduceBehaviour(ResourceType resourceToRemove) {
      this.resourceToRemove = resourceToRemove;
   }

   @Override
   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException, NegativeQuantityException {

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
//try {
//        for(Map.Entry<ResourceType, Integer> entry : target.entrySet()) {
//        int remainingToRemove = warehouse.removeResources(entry.getKey(),entry.getValue());
//        try {
//        chest.removeResources(entry.getKey(), remainingToRemove);
//        }catch(AbuseOfFaithException e){
//        e.printStackTrace();
//        }
//        }
//        } catch (NegativeQuantityException e) {
//        e.printStackTrace();
//        } catch (NotEnoughResourcesException e) {
//        throw new NotActivatableException("you don't have enough resources to activate this card");
//        }
//
//        for(Map.Entry<ResourceType, Integer> entry : product.entrySet()) {
//        try {
//        if(entry.getKey().equals(ResourceType.FAITH))
//        playerBoard.getTrack().moveForward(entry.getValue());
//        else
//        playerBoard.getChest().addResources(entry.getKey(),entry.getValue());
//        } catch (AbuseOfFaithException | NegativeQuantityException e) {
//        e.printStackTrace();
//        }
//        }