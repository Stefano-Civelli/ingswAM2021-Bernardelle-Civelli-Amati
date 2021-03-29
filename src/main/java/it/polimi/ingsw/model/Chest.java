package it.polimi.ingsw.model;
import it.polimi.ingsw.model.modelException.AbuseOfFaithException;
import it.polimi.ingsw.model.modelException.MissingResourceToRemoveException;
import it.polimi.ingsw.model.modelException.NotEnoughResourcesExeption;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class Chest {
  private final Map<ResourceType, Integer> resources;

  public Chest(){
    resources = new HashMap<>();
  }

  public void addResources(ResourceType resource, int quantity) throws AbuseOfFaithException {
    if(resource == ResourceType.FAITH)
      throw new AbuseOfFaithException();

    if(resources.containsKey(resource))
      resources.replace(resource, resources.get(resource)+quantity);
    else
      resources.put(resource, quantity);
  }

  public void removeResources(ResourceType resource, int quantity) throws AbuseOfFaithException, NotEnoughResourcesExeption, MissingResourceToRemoveException {
    if(resource == ResourceType.FAITH)
      throw new AbuseOfFaithException();

    if(resources.containsKey(resource)) {
      if (resources.get(resource) - quantity < 0)
        throw new NotEnoughResourcesExeption();

      resources.replace(resource, resources.get(resource) - quantity);
    }
    else
      throw new MissingResourceToRemoveException();
  }

  public int totalNumberOfResources(){
    return resources.entrySet().stream().map( i -> i.getValue()).reduce(0, (a, b) -> a+b);
  }
}