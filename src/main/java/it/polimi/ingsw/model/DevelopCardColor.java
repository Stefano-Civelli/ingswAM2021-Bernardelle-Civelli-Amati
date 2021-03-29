package it.polimi.ingsw.model;

public enum DevelopCardColor {
   GREEN(0), PURPLE(1), BLUE(2), YELLOW(3);
   private final int column;

   DevelopCardColor(int i){
      column = i;
   }

   public int getColumn(){
      return column;
   }

}
