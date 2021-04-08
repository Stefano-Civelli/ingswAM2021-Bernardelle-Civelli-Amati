package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;

import java.util.HashMap;

public abstract class CardBehaviour {

   public ResourceType getOnWhite() {
      return null;
   }

   public void createStorage(InterfacePlayerBoard playerboard) {
      //eccezione I can't create a storage
   }

   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException {
      //eccezione I can't produce oppure non produrre niente
   }

   public HashMap<ResourceType, Integer> disconunt(HashMap<ResourceType, Integer> resources){
      return resources;
   }


}
