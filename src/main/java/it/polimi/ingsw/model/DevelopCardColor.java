package it.polimi.ingsw.model;

public enum DevelopCardColor {

   GREEN(0, "Green"),
   BLUE(1, "Blue"),
   YELLOW(2, "Yellow"),
   PURPLE(3, "Purple");

   private final int column;
   private final String color;

   DevelopCardColor(int column, String color){
      this.column = column;
      this.color = color;
   }

   /**
    * static factory method that constructs enum by string
    *
    * @param value string to create the enum
    * @return a new enumeration
    */
   public static DevelopCardColor fromValue(String value) {
      for (DevelopCardColor developCardColor : values()) {
         if (developCardColor.name().equals(value)) {
            return developCardColor;
         }
      }
      throw new IllegalArgumentException("invalid string value passed: " + value);
   }

   public int getColumn(){
      return column;
   }

   @Override
   public String toString() {
      return color;
   }
}
