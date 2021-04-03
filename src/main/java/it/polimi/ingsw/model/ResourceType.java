package it.polimi.ingsw.model;



public enum ResourceType {
  STONE, GOLD, SERVANT, FAITH, SHIELD;


  /**
   * static factory method that constructs enum by string
   * @param v string to create the enum
   * @return a new enumeration
   */
  public static ResourceType fromValue(String v) {
    for (ResourceType resourceType : values()) {
      if (resourceType.name().equals(v)) {
        return resourceType;
      }
    }
    throw new IllegalArgumentException("invalid string value passed: " + v);
  }
}
