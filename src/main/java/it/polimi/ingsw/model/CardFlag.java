package it.polimi.ingsw.model;

public class CardFlag {

   private final int level;
   private final DevelopCardColor color;

   /**
    * Constructor for cardFlag class.
    * Constructs a cardFlag object with the specified level and color
    * @param level the flag level
    * @param color the flag color
    */
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

   /**
    * Returns true if the specified obj is equal to this
    * Two cardFlags are considered equals if they have matching color and level
    * @param obj the object of which equality to 'this' is to be verified
    * @return true if the 2 objects are equal
    */
   @Override
   public boolean equals(Object obj) {
      if (obj == this)
         return true;
      if (!(obj instanceof CardFlag))
         return false;
      CardFlag c = (CardFlag) obj;
      if(c.getLevel() == 0)
         return (this.color == c.color);
      if(this.getLevel() == 0)
         return (this.color == c.color);
      return (this.level == c.level) && (this.color == c.color);
   }

}
