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

        MarketMarble marble = new WhiteMarble();
        playerBoard.getWarehouse().addResources(ResourceType.GOLD, 0, 1);
        marble.addResource(playerBoard, 0, ResourceType.GOLD);
        assertEquals(2, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));

        marble.addResource(playerBoard, 1, ResourceType.SERVANT);
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.SERVANT));

        int total = playerBoard.getWarehouse().totalResources();
        marble.addResource(playerBoard, 2, null);
        assertEquals(total, playerBoard.getWarehouse().totalResources());

        int points0 = playerBoard.getTrack().getTrack()[0].getVictoryPoints(),
                points1 = 0,
                position = 0;
        for(int i = 0; i < playerBoard.getTrack().getTrack().length; i++)
            if(playerBoard.getTrack().getTrack()[i].getVictoryPoints() > points0) {
                points1 = playerBoard.getTrack().getTrack()[i].getVictoryPoints();
                position = i;
                break;
            }
        playerBoard.getTrack().moveForward(position - 1);
        assertEquals(points0, playerBoard.getTrack().calculateTrackScore());
        marble.addResource(playerBoard, 1, ResourceType.FAITH);
        assertEquals(0, playerBoard.getWarehouse().getNumberOf(ResourceType.FAITH));
        assertEquals(points1, playerBoard.getTrack().calculateTrackScore());

    }

    @Test
    @SuppressWarnings({"Possible", "AssertBetweenInconvertibleTypes"})
    public void testEqualsTest() throws AbuseOfFaithException {
        assertEquals(new WhiteMarble(), new WhiteMarble());
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.SHIELD));
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.STONE));
        assertNotEquals(new WhiteMarble(), new RedMarble());
        assertNotEquals(new WhiteMarble(), null);
        assertNotEquals(new WhiteMarble(), new Object());
    }
}