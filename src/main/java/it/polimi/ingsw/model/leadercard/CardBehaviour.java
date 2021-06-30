package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.InterfacePlayerBoard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.HashMap;

/**
 * Class that contains all possibile leaderCards behaviours.
 * When a leadercard ability gets activated these methods get called to perform the card ability.
 * These methods are Overridden by the specific subclasses that represent the various possible card Types;
 * so in order to create the specific cards it is possible to compose at will this methods in a new implementation of the CardBehaviour interface
 */
public abstract class CardBehaviour {

   /**
    * returns the resource white marble ResourceType produced by the LeaderCard ability.
    * Returns null if the card doesn't have this ability
    *
    * @return can be null if this leadercard doesn't convert white marbles
    */
   public ResourceType getOnWhite() {
      return null;
   }

   /**
    * adds level to the warehouse of the specified playerBoard so that the leaderCard level becomes usable.
    * Does nothing if the card doesn't have this ability
    *
    * @param playerBoard playerBoard in which to add the warehouse level
    * @throws MaxLeaderCardLevelsException if more than the maximum allowed number of additional levels are added
    */
   public void createStorage(InterfacePlayerBoard playerBoard) throws MaxLeaderCardLevelsException {}

   /**
    * activates the leader ability to produce resources.
    * Does nothing if the card doesn't have this ability
    *
    * @param resourceToAdd the resource to add to the Chest
    * @param playerboard the playerBoard in which to add the resources
    * @throws AbuseOfFaithException if resourceToAdd is the faith resource
    * @throws NotEnoughResourcesException if the specified playerBoard doesn't have enough resources to consume for the production
    * @throws NeedAResourceToAddException if the resourceToAdd parameter is not specified
    */
   public void produce(ResourceType resourceToAdd, InterfacePlayerBoard playerboard) throws AbuseOfFaithException, NotEnoughResourcesException, NeedAResourceToAddException {}

   /**
    * applies discount to the map passed as a parameter
    * NOTE: the method modifies the map  passed as a parameter in order to apply the discount
    * does nothing if the card doesn't have this ability
    *
    * @param resources the resource map to be discounted
    */
   public void discount(HashMap<ResourceType, Integer> resources){}


   public ResourceType getResourceToDiscount() {
      return null;
   }

   public ResourceType getProductionRequirement() {
      return null;
   }

   public ResourceType getResToStore() {
      return null;
   }
}
