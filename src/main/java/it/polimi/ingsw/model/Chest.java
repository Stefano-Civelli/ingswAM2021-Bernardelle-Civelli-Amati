package it.polimi.ingsw.model;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.MissingResourceToRemoveException;
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

  public void addResources(ResourceType resource, int quantity) throws AbuseOfFaithException {
    if(resource == ResourceType.FAITH)
      throw new AbuseOfFaithException("Adding faith to chest is not allowed");

    if(tempResourcesMap.containsKey(resource))
      tempResourcesMap.replace(resource, tempResourcesMap.get(resource) + quantity);
    else
      tempResourcesMap.put(resource, quantity);
  }

  public void removeResources(ResourceType resource, int quantity) throws MissingResourceToRemoveException, NotEnoughResourcesException, AbuseOfFaithException{
    if(resource == ResourceType.FAITH)
      throw new AbuseOfFaithException();

    if(resources.containsKey(resource)) {
      if (resources.get(resource) - quantity < 0)
        throw new NotEnoughResourcesException();

      resources.replace(resource, resources.get(resource) - quantity);
    }
    else
      throw new MissingResourceToRemoveException();
  }

  public int totalNumberOfResources(){
    return resources.entrySet().stream().map(i -> i.getValue()).reduce(0, (a, b) -> a + b);
  }

  public int getNumberOf(ResourceType resource){
    return resources.get(resource);
  }

  public void mergeMapResources(){

    tempResourcesMap.entrySet()
             .forEach(entry -> resources.merge(
                    entry.getKey(),
                    entry.getValue(),
                    (key, value) -> entry.getValue()   + value));
  }

}