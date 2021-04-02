package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ResourceType {
  STONE, GOLD, SERVANT, FAITH, SHIELD;


  @JsonCreator
  public static ResourceType fromValue(String v) {
    for (ResourceType resourceType : values()) {
      if (resourceType.name().equals(v)) {
        return resourceType;
      }
    }
    throw new IllegalArgumentException("invalid string value passed: " + v);
  }
}
