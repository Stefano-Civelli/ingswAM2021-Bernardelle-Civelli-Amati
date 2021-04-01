package it.polimi.ingsw.model;

public class CardFlag {
   private final int level;
   private final DevelopCardColor color;

   public CardFlag(int level, DevelopCardColor color){
      this.level = level;
      this.color = color;
   }

   public int getLevel(){
      return level;
   }

   public DevelopCardColor getColor(){
      return color;
   }


}
