package it.polimi.ingsw.model;



public enum ResourceType {
  STONE, GOLD, SERVANT, FAITH, SHIELD;


  /**
   * static factory method that constructs enum by string
   * @param value string to create the enum
   * @return a new enumeration
   */
  public static ResourceType fromValue(String value) {
    for (ResourceType resourceType : values()) {
      if (resourceType.name().equals(value)) {
        return resourceType;
      }
    }
    throw new IllegalArgumentException("invalid string value passed: " + value);
  }

}
