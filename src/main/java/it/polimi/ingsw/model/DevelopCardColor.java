package it.polimi.ingsw.model;

public enum DevelopCardColor {
   GREEN(0, "Green"), PURPLE(1, "Purple"), BLUE(2, "Blue"), YELLOW(3, "Yellow");

   private final int column;
   private final String color;

   DevelopCardColor(int i, String c){
      column = i;
      color = c;
   }

   public int getColumn(){ return column; }

   @Override
   public String toString() { return color; }


}
