package it.polimi.ingsw.model;

public enum DevelopCardColor {
   GREEN(0, "Green"), BLUE(1, "Blue"), YELLOW(2, "Yellow"), PURPLE(3, "Purple");

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
