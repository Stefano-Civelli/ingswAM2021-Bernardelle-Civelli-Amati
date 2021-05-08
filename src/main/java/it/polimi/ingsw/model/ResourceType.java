package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.view.cli.Color;

public enum ResourceType {

  STONE ( Color.ANSI_GREY.escape() + "⚫", MarbleColor.GREY),
  GOLD (Color.ANSI_YELLOW.escape() + "⚫", MarbleColor.YELLOW),
  SERVANT (Color.ANSI_PURPLE.escape() + "⚫", MarbleColor.PURPLE),
  FAITH (Color.ANSI_RED.escape() + "⚫", MarbleColor.RED),
  SHIELD (Color.ANSI_BLUE.escape() + "⚫", MarbleColor.BLUE);

  private String escape;
  private MarbleColor color;

  ResourceType(String escape, MarbleColor m) {
    this.escape = escape;
    this.color = m;
  }

  public String escape() {
    return escape;
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
  public String toString(){
    return this.escape;
  }
}
