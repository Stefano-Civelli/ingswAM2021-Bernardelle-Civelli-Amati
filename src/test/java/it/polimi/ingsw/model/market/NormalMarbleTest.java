package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

class NormalMarbleTest {

    @Test
    void addResourceTest() throws AbuseOfFaithException, NotEnoughSpaceException, MoreWhiteLeaderCardsException, IOException {
        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);

        MarketMarble marble1 = new NormalMarble(ResourceType.GOLD);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
        marble1.addResource(playerBoard);
        assertEquals(2, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));

        MarketMarble marble2 = new NormalMarble(ResourceType.SERVANT);
        marble2.addResource(playerBoard);
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.SERVANT));
    }

    @Test
    void addNoSpaceTest() throws AbuseOfFaithException, IOException, NotEnoughSpaceException {
        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);

        MarketMarble marble1 = new NormalMarble(ResourceType.GOLD);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
        assertThrows(NotEnoughSpaceException.class, () -> marble1.addResource(playerBoard));
        assertEquals(3, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));
    }

    @Test
    void faithExceptionTest() {
        assertThrows(AbuseOfFaithException.class, () -> new NormalMarble(ResourceType.FAITH));
    }

    @Test
    void nullExceptionTest() {
        assertThrows(NullPointerException.class, () -> new NormalMarble(null));
    }

    @Test
    @SuppressWarnings({"Possible", "AssertBetweenInconvertibleTypes"})
    void equalsTest() throws AbuseOfFaithException {
        assertEquals(new NormalMarble(ResourceType.GOLD), new NormalMarble(ResourceType.GOLD));
        assertNotEquals(new NormalMarble(ResourceType.GOLD), new NormalMarble(ResourceType.SERVANT));
        assertNotEquals(new NormalMarble(ResourceType.GOLD), new NormalMarble(ResourceType.SHIELD));
        assertNotEquals(new NormalMarble(ResourceType.GOLD), new NormalMarble(ResourceType.STONE));
        assertEquals(new NormalMarble(ResourceType.SERVANT), new NormalMarble(ResourceType.SERVANT));
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), new NormalMarble(ResourceType.GOLD));
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), new NormalMarble(ResourceType.SHIELD));
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), new NormalMarble(ResourceType.STONE));
        assertEquals(new NormalMarble(ResourceType.SHIELD), new NormalMarble(ResourceType.SHIELD));
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), new NormalMarble(ResourceType.GOLD));
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), new NormalMarble(ResourceType.SERVANT));
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), new NormalMarble(ResourceType.STONE));
        assertEquals(new NormalMarble(ResourceType.STONE), new NormalMarble(ResourceType.STONE));
        assertNotEquals(new NormalMarble(ResourceType.STONE), new NormalMarble(ResourceType.GOLD));
        assertNotEquals(new NormalMarble(ResourceType.STONE), new NormalMarble(ResourceType.SERVANT));
        assertNotEquals(new NormalMarble(ResourceType.STONE), new NormalMarble(ResourceType.SHIELD));

        assertNotEquals(new NormalMarble(ResourceType.GOLD), new RedMarble());
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), new RedMarble());
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), new RedMarble());
        assertNotEquals(new NormalMarble(ResourceType.STONE), new RedMarble());

        assertNotEquals(new NormalMarble(ResourceType.GOLD), new WhiteMarble());
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), new WhiteMarble());
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), new WhiteMarble());
        assertNotEquals(new NormalMarble(ResourceType.STONE), new WhiteMarble());

        assertNotEquals(new NormalMarble(ResourceType.GOLD), new Object());
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), new Object());
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), new Object());
        assertNotEquals(new NormalMarble(ResourceType.STONE), new Object());

        assertNotEquals(new NormalMarble(ResourceType.GOLD), null);
        assertNotEquals(new NormalMarble(ResourceType.SERVANT), null);
        assertNotEquals(new NormalMarble(ResourceType.SHIELD), null);
        assertNotEquals(new NormalMarble(ResourceType.STONE), null);
    }

}
