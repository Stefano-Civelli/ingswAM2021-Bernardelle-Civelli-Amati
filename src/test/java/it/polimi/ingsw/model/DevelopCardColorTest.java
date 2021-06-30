package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevelopCardColorTest {

   @Test
   void fromValueTest() {
      DevelopCardColor developCardColor = DevelopCardColor.fromValue("GREEN");
      assertEquals(developCardColor, DevelopCardColor.GREEN);
      assertThrows(IllegalArgumentException.class, () -> DevelopCardColor.fromValue("green"));
   }

   @Test
   void fromValueTest2() {
      DevelopCardColor developCardColor = DevelopCardColor.fromValue("YELLOW");
      assertEquals(developCardColor, DevelopCardColor.YELLOW);
      DevelopCardColor developCardColor1 = DevelopCardColor.fromValue("BLUE");
      assertEquals(developCardColor1, DevelopCardColor.BLUE);
      DevelopCardColor developCardColor2 = DevelopCardColor.fromValue("PURPLE");
      assertEquals(developCardColor2, DevelopCardColor.PURPLE);
   }

   @Test
   void fromValueTest3() {
      DevelopCardColor developCardColor = DevelopCardColor.fromValue(DevelopCardColor.YELLOW.toString().toUpperCase());
      assertEquals(developCardColor, DevelopCardColor.YELLOW);
      DevelopCardColor developCardColor2 = DevelopCardColor.fromValue(DevelopCardColor.PURPLE.toString().toUpperCase());
      assertEquals(developCardColor2, DevelopCardColor.PURPLE);
   }
}