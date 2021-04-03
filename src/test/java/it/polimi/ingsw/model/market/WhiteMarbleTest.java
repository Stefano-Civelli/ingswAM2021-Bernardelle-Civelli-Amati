package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class WhiteMarbleTest {

    @Test
    public void addResourceTest() throws AbuseOfFaithException, IncorrectResourceTypeException, NegativeQuantityException,
            LevelNotExistsException, NotEnoughSpaceException {
        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);

        MarketMarble marble1 = new WhiteMarble();
        playerBoard.getWarehouse().addResources(ResourceType.GOLD, 0, 1);
        marble1.addResource(playerBoard, 0, ResourceType.GOLD);
        assertEquals(playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD), 2);

        marble1.addResource(playerBoard, 1, ResourceType.SERVANT);
        assertEquals(playerBoard.getWarehouse().getNumberOf(ResourceType.SERVANT), 1);


//        int points = 0, position = 0;
//        for(int i = 0; i < playerBoard.getTrack().getTrack().length; i++)
//            if(playerBoard.getTrack().getTrack()[i].getVictoryPoints() > 0) {
//                points = playerBoard.getTrack().getTrack()[i].getVictoryPoints();
//                position = i;
//                break;
//            }
//        playerBoard.getTrack().moveForward(position - 1);
//        assertEquals(0, playerBoard.getTrack().calculateTrackScore());
//        marble1.addResource(playerBoard, 1, ResourceType.FAITH);
//        assertEquals(0, playerBoard.getWarehouse().getNumberOf(ResourceType.FAITH));
//        assertEquals(points, playerBoard.getTrack().calculateTrackScore());

    }

    @Test
    @SuppressWarnings({"Possible", "AssertBetweenInconvertibleTypes"})
    public void testEqualsTest() throws AbuseOfFaithException {
        assertEquals(new WhiteMarble(), new WhiteMarble());
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.SHIELD));
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.STONE));
        assertNotEquals(new WhiteMarble(), new RedMarble());
    }
}