package it.polimi.ingsw.model;
import java.util.HashMap;
import java.util.Map;

public class Chest {
  private final Map<ResourceType, Integer> resources;

  public Chest(){
    resources = new HashMap<>();
  }

  public void addResources(ResourceType resource, int quantity) throws Exception {
    if(resource == ResourceType.FAITH)
      throw new Exception();

    if(resources.containsKey(resource))
      resources.replace(resource, resources.get(resource) + quantity);
    else
      resources.put(resource, quantity);
  }

  public void removeResources(ResourceType resource, int quantity) throws Exception {
    if(resource == ResourceType.FAITH)
      throw new Exception();

    if(resources.containsKey(resource)) {
      if (resources.get(resource) - quantity < 0)
        throw new Exception();

      resources.replace(resource, resources.get(resource) - quantity);
    }
    else
      throw new Exception();
  }

  public int totalNumberOfResources(){
    return resources.entrySet().stream().map( i -> i.getValue()).reduce(0, (a, b) -> a + b);
  }

  public int getNumberOf(ResourceType resource){
    return resources.get(resource);
  }
}
