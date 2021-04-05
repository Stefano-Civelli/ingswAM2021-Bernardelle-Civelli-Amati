package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class NormalMarbleTest {

    @Test
    void addResourceTest() throws AbuseOfFaithException, IncorrectResourceTypeException, NegativeQuantityException,
            LevelNotExistsException, NotEnoughSpaceException {
        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);

        MarketMarble marble1 = new NormalMarble(ResourceType.GOLD);
        playerBoard.getWarehouse().addResources(ResourceType.GOLD, 0, 1);
        marble1.addResource(playerBoard, 0, null);
        assertEquals(2, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));

        MarketMarble marble2 = new NormalMarble(ResourceType.SERVANT);
        marble2.addResource(playerBoard, 1, null);
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.SERVANT));
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