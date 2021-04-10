package it.polimi.ingsw.model;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.NotEnoughResourcesException;

import java.util.HashMap;
import java.util.Map;

public class Chest {
  private final Map<ResourceType, Integer> resources;
  private final Map<ResourceType, Integer> tempResourcesMap;

  public Chest(){
    resources = new HashMap<>();
    tempResourcesMap = new HashMap<>();
  }

  public void addResources(ResourceType resource, int quantity) throws AbuseOfFaithException, NotEnoughResourcesException {
    if(resource == ResourceType.FAITH)
      throw new AbuseOfFaithException("Adding faith to chest is not allowed");
      //if the key is not present adds a new element to the map with value quantity
    if(quantity < 0)
      throw new NotEnoughResourcesException("U are adding a negative quantity of a resource, that's not allowed");
      tempResourcesMap.compute(resource, (k,v) -> (v==null) ? quantity : v + quantity);

  }

  public void removeResources(ResourceType resource, int quantity) throws NotEnoughResourcesException, AbuseOfFaithException{
    if(resource == ResourceType.FAITH)
      throw new AbuseOfFaithException();

    if(resources.containsKey(resource)) {
      if (resources.get(resource) - quantity < 0 || quantity < 0)
        throw new NotEnoughResourcesException("not enough resources");

      resources.replace(resource, resources.get(resource) - quantity);
    }
    else
      throw new NotEnoughResourcesException("you have 0 of the specified resource");
  }

  public int totalNumberOfResources(){
    return resources.entrySet().stream().map(i -> i.getValue()).reduce(0, Integer::sum);
  }

  public int getNumberOf(ResourceType resource){
    if(!resources.containsKey(resource))
      return 0;
    return resources.get(resource);
  }

  public void mergeMapResources(){

    tempResourcesMap.entrySet()
             .forEach(entry -> resources.merge(
                    entry.getKey(),
                    entry.getValue(),
                    (key, value) -> entry.getValue() + value));
  }
}