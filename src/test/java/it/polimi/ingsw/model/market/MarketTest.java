package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.modelexceptions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class MarketTest {

    @Test
    void getInstanceTest() {
        assertSame(Market.getInstance(), Market.getInstance());
    }

    @Test
    void pushInRowTest() throws RowOrColumnNotExistsException {
        Market market = Market.getInstance();
        for(int i = 0; i < market.getNumberOfRow(); i++) {
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
    }

    @Test
    void pushInColumn() throws RowOrColumnNotExistsException {
        Market market = Market.getInstance();
        for(int i = 0; i < market.getNumberOfColumn(); i++) {
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
    }

    @Test
    void mixedPushRowColumn() throws RowOrColumnNotExistsException {
        //slide: M            E           d
        // a b c d        a b c d     a b c M
        // E f g h <  =>  f g h M  => f g h L
        // i j k L        i j k L     i j k E
        //                      ^           ^
        Market market = Market.getInstance();
        List<MarketMarble> marbles1 = market.pushInRow(1);
        List<MarketMarble> marbles2 = market.pushInColumn(3);
        List<MarketMarble> marbles3 = market.pushInColumn(3);

        assertEquals(marbles1.get(0), marbles3.get(2));
        assertEquals(marbles2.get(1), marbles3.get(0));
        assertEquals(marbles2.get(2), marbles3.get(1));
    }

    @Test
    void exceptionTest() {
        Market market = Market.getInstance();
        MarketMarble[][] status1 = market.getStatus();
        assertThrows(RowOrColumnNotExistsException.class, () -> market.pushInRow(-1) );
        assertThrows(RowOrColumnNotExistsException.class, () -> market.pushInRow(market.getNumberOfRow()) );
        assertThrows(RowOrColumnNotExistsException.class, () -> market.pushInColumn(-1) );
        assertThrows(RowOrColumnNotExistsException.class, () -> market.pushInColumn(market.getNumberOfColumn()) );
        MarketMarble[][] status2 = market.getStatus();

        assertEquals(status1.length, status2.length);
        for(int i = 0; i < status1.length; i++) {
            assertEquals(status1[i].length, status2[i].length);
            for(int j = 0; j < status1[i].length; j++)
                assertEquals(status1[i][j], status2[i][j]);
        }
    }

}
