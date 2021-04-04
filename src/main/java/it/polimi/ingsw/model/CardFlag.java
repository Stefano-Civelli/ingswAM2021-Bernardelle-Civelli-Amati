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

   @Override
   public String toString() {
      return "CardFlag{" +
              "level=" + level +
              ", color=" + color +
              '}';
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this)
         return true;
      if (!(obj instanceof CardFlag))
         return false;
      CardFlag c = (CardFlag) obj;
      return (this.level == c.level) && (this.color == c.color);
   }
}
