package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.view.cli.Color;

public enum ResourceType {

  STONE (MarbleColor.GREY),
  GOLD (MarbleColor.YELLOW),
  SERVANT (MarbleColor.PURPLE),
  FAITH (MarbleColor.RED),
  SHIELD (MarbleColor.BLUE);

  private MarbleColor color;

  ResourceType(MarbleColor m) {
    this.color = m;
  }


  public MarbleColor getColor(){
    return color;
  }

  /**
   * static factory method that constructs enum by string
   *
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

  @Override
  public String toString () {
    return getColor().escape();
  }
}
