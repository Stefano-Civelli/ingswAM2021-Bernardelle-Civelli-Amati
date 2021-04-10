package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;

import java.util.HashMap;

public abstract class CardBehaviour {

   /**
    * returns the resource white marble ResourceType produced by the LeaderCard ability
    * @return can be null if this leadercard doesn't convert white marbles
    */
   public ResourceType getOnWhite() {
      return null;
   }

   public void createStorage(InterfacePlayerBoard playerboard) {
      //eccezione I can't create a storage
   }

   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException, NotEnoughResourcesException {
      //eccezione I can't produce oppure non produrre niente
   }

   public HashMap<ResourceType, Integer> discount(HashMap<ResourceType, Integer> resources){
      return resources;
   }


}
