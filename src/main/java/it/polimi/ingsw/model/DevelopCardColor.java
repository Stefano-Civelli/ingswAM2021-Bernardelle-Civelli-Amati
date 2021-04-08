package it.polimi.ingsw.model;

public enum DevelopCardColor {
   GREEN(0, "Green"), BLUE(1, "Blue"), YELLOW(2, "Yellow"), PURPLE(3, "Purple");

   private final int column;
   private final String color;

   DevelopCardColor(int i, String c){
      column = i;
      color = c;
   }

   public static DevelopCardColor fromValue(String v) {
      for (DevelopCardColor developCardColor : values()) {
         if (developCardColor.name().equals(v)) {
            return developCardColor;
         }
      }
      throw new IllegalArgumentException("invalid string value passed: " + v);
   }

   public int getColumn(){ return column; }

   @Override
   public String toString() { return color; }


}
