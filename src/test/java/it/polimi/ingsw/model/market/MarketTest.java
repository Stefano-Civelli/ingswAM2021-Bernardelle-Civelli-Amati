package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

   @Test
   public void getInstanceTest() {
      assertSame(Market.getInstance(), Market.getInstance());
   }

   @Test
   public void pushInRowTest() {
      Market market = Market.getInstance();
      List<MarketMarble> marbles1 = market.pushInRow(0);      // A B C D
      List<MarketMarble> marbles2 = market.pushInRow(0);      // B C D E
      List<MarketMarble> marbles3 = market.pushInRow(0);      // C D E A

      List<MarketMarble> marblesTmp1 = new ArrayList<>(marbles1);
      marblesTmp1.remove(0);
      List<MarketMarble> marblesTmp2 = new ArrayList<>(marbles2);
      marblesTmp2.remove(marbles2.size() - 1);
      assertEquals(marblesTmp1, marblesTmp2);

      marblesTmp1 = new ArrayList<>(marbles2);
      marblesTmp1.remove(0);
      marblesTmp2 = new ArrayList<>(marbles3);
      marblesTmp2.remove(marbles3.size() - 1);
      assertEquals(marblesTmp1, marblesTmp2);

      assertEquals(marbles1.get(0), marbles3.get(marbles3.size() - 1));
   }

   @Test
   public void pushInColumn() {
      Market market = Market.getInstance();
      List<MarketMarble> marbles1 = market.pushInColumn(0);      // A B C
      List<MarketMarble> marbles2 = market.pushInColumn(0);      // B C D
      List<MarketMarble> marbles3 = market.pushInColumn(0);      // C D A

      List<MarketMarble> marblesTmp1 = new ArrayList<>(marbles1);
      marblesTmp1.remove(0);
      List<MarketMarble> marblesTmp2 = new ArrayList<>(marbles2);
      marblesTmp2.remove(marbles2.size() - 1);
      assertEquals(marblesTmp1, marblesTmp2);

      marblesTmp1 = new ArrayList<>(marbles2);
      marblesTmp1.remove(0);
      marblesTmp2 = new ArrayList<>(marbles3);
      marblesTmp2.remove(marbles3.size() - 1);
      assertEquals(marblesTmp1, marblesTmp2);

      assertEquals(marbles1.get(0), marbles3.get(marbles3.size() - 1));
   }

   @Test
   public void getStatus() {

   }
}