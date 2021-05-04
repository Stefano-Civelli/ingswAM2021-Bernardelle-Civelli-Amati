package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.model.modelexceptions.*;


class WarehouseTest {

    @Test
    void numberLevelsTest() throws AbuseOfFaithException, MaxLeaderCardLevelsException {
        Warehouse warehouse = new Warehouse();
        assertEquals(2, warehouse.maxLeaderCardsLevels());
        assertEquals(3, warehouse.numberOfNormalLevels());
        assertEquals(0, warehouse.numberOfLeaderCardsLevels());
        assertEquals(3, warehouse.numberOfAllLevels());
        assertEquals(warehouse.numberOfNormalLevels(), warehouse.numberOfAllLevels());
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        assertEquals(1, warehouse.numberOfLeaderCardsLevels());
        assertEquals(warehouse.numberOfAllLevels(), warehouse.numberOfLeaderCardsLevels() + warehouse.numberOfNormalLevels());
        warehouse.addLeaderCardLevel(ResourceType.SERVANT);
        assertEquals(2, warehouse.numberOfLeaderCardsLevels());
        assertEquals(warehouse.numberOfAllLevels(), warehouse.numberOfLeaderCardsLevels() + warehouse.numberOfNormalLevels());
        assertEquals(2, warehouse.maxLeaderCardsLevels());
        assertEquals(3, warehouse.numberOfNormalLevels());
        assertEquals(2, warehouse.numberOfLeaderCardsLevels());
        assertEquals(5, warehouse.numberOfAllLevels());
    }

    @Test
    void addTest() throws AbuseOfFaithException, NotEnoughSpaceException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(1, warehouse.totalResources());
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(2, warehouse.totalResources());
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(3, warehouse.totalResources());
        warehouse.addResource(ResourceType.GOLD);
        assertEquals(4, warehouse.totalResources());
        warehouse.addResource(ResourceType.STONE);
        assertEquals(5, warehouse.totalResources());
        warehouse.addResource(ResourceType.STONE);
        assertEquals(6, warehouse.totalResources());
        assertEquals(3, warehouse.getNumberOf(ResourceType.SERVANT));
        assertEquals(2, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(1, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
    }

    @Test
    void removeTest() throws AbuseOfFaithException, NotEnoughSpaceException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(0, warehouse.removeResources(ResourceType.SERVANT, 2));
        assertEquals(4, warehouse.totalResources());
        assertEquals(1, warehouse.getNumberOf(ResourceType.SERVANT));
        assertEquals(1, warehouse.removeResources(ResourceType.GOLD, 2));
        assertEquals(3, warehouse.totalResources());
        assertEquals(0, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(2, warehouse.getNumberOf(ResourceType.STONE));
    }

    @Test
    void addAndRemoveTest() throws NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(0, warehouse.removeResources(ResourceType.SERVANT, 2));
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        assertEquals(2, warehouse.removeResources(ResourceType.GOLD, 5));
        assertEquals(1, warehouse.removeResources(ResourceType.SERVANT, 2));
    }

    @Test
    void addNoSpaceTest() throws NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.SERVANT));
        assertEquals(3, warehouse.totalResources());
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.STONE));
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.GOLD));
        assertEquals(6, warehouse.totalResources());
        assertEquals(3, warehouse.removeResources(ResourceType.GOLD, 5));
        warehouse.addResource(ResourceType.STONE);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.STONE));
        assertEquals(5, warehouse.totalResources());
        warehouse.addResource(ResourceType.SHIELD);
        assertEquals(0, warehouse.removeResources(ResourceType.SERVANT, 2));
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(6, warehouse.totalResources());
    }

    @Test
    void addNullTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(NullPointerException.class, () -> warehouse.addResource(null));
        assertEquals(0, warehouse.totalResources());
    }

    @Test
    void addFaithTest() throws NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        assertThrows(AbuseOfFaithException.class, () -> warehouse.addResource(ResourceType.FAITH));
        assertEquals(1, warehouse.removeResources(ResourceType.FAITH, 1));
    }

    @Test
    void removeNullTest() throws AbuseOfFaithException, NotEnoughSpaceException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.GOLD);
        assertThrows(NullPointerException.class, () -> warehouse.removeResources(null, 1) );
        assertEquals(1, warehouse.totalResources());
    }

    @Test
    void resourcesAmountTest() throws NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
        assertEquals(0, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SERVANT));
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SHIELD);
        assertEquals(3, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(1, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(1, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SERVANT));
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
        warehouse.removeResources(ResourceType.GOLD, 1);
        warehouse.removeResources(ResourceType.SHIELD, 1);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(2, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(2, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(1, warehouse.getNumberOf(ResourceType.SERVANT));
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
    }

    @Test
    void totalResourcesTest() throws NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        assertEquals(warehouse.totalResources(), 0);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(4, warehouse.totalResources());
        warehouse.removeResources(ResourceType.GOLD, 1);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(warehouse.totalResources(), 5);
    }

    @Test
    void addLeaderCardTest() throws MaxLeaderCardLevelsException,
            AbuseOfFaithException, NotEnoughSpaceException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        assertEquals(1, warehouse.numberOfLeaderCardsLevels());
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
    }

    @Test
    void maxLeaderCardTest() throws MaxLeaderCardLevelsException, AbuseOfFaithException, NotEnoughSpaceException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        warehouse.addLeaderCardLevel(ResourceType.STONE);
        assertThrows(MaxLeaderCardLevelsException.class, () -> warehouse.addLeaderCardLevel(ResourceType.SHIELD));
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.SHIELD));
        assertEquals(2, warehouse.numberOfLeaderCardsLevels());
    }

    @Test
    void nullLeaderCardTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(NullPointerException.class, () -> warehouse.addLeaderCardLevel(null));
        assertEquals(0, warehouse.numberOfLeaderCardsLevels());
    }

    @Test
    void faithLevelTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(AbuseOfFaithException.class, () -> warehouse.addLeaderCardLevel(ResourceType.FAITH));
        assertEquals(0, warehouse.numberOfLeaderCardsLevels());
    }

    @Test
    void addInLeaderTest() throws AbuseOfFaithException, MaxLeaderCardLevelsException, NotEnoughSpaceException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addLeaderCardLevel(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.STONE);
    }

    @Test
    void removeFromLeaderTest() throws AbuseOfFaithException, MaxLeaderCardLevelsException, NotEnoughSpaceException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        warehouse.addLeaderCardLevel(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(0, warehouse.removeResources(ResourceType.GOLD, 1));
        assertEquals(4, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(3, warehouse.removeResources(ResourceType.GOLD, 7));
        assertEquals(1, warehouse.removeResources(ResourceType.SERVANT, 3));
        assertEquals(2, warehouse.removeResources(ResourceType.STONE, 3));
        assertEquals(1, warehouse.removeResources(ResourceType.SHIELD, 3));
        assertEquals(0, warehouse.totalResources());
    }

    @Test
    void addAndRemoveInLeaderTest()
            throws AbuseOfFaithException, MaxLeaderCardLevelsException, NotEnoughSpaceException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        warehouse.addLeaderCardLevel(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.removeResources(ResourceType.GOLD, 2);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.removeResources(ResourceType.SHIELD, 2);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SHIELD);
    }

    @Test
    void addNoSpaceInLeader() throws AbuseOfFaithException, MaxLeaderCardLevelsException, NotEnoughSpaceException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.SHIELD);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.GOLD));
        assertEquals(1, warehouse.removeResources(ResourceType.GOLD, 5));
        warehouse.addResource(ResourceType.SERVANT);
        warehouse.addResource(ResourceType.STONE);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.STONE));
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.GOLD));
        warehouse.addLeaderCardLevel(ResourceType.STONE);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.GOLD));
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.STONE);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.GOLD));
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResource(ResourceType.STONE));
    }

    @Test
    void resourcesAmountInLeaderTest()
            throws NotEnoughSpaceException, AbuseOfFaithException, MaxLeaderCardLevelsException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
        assertEquals(0, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SERVANT));
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SHIELD);
        assertEquals(3, warehouse.getNumberOf(ResourceType.GOLD));
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        assertEquals(5, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(1, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(1, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SERVANT));
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
        warehouse.addLeaderCardLevel(ResourceType.STONE);
        warehouse.removeResources(ResourceType.GOLD, 1);
        warehouse.removeResources(ResourceType.SHIELD, 1);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(4, warehouse.getNumberOf(ResourceType.GOLD));
        warehouse.removeResources(ResourceType.GOLD, 2);
        assertEquals(2, warehouse.getNumberOf(ResourceType.GOLD));
        assertEquals(2, warehouse.getNumberOf(ResourceType.STONE));
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(4, warehouse.getNumberOf(ResourceType.STONE));
        warehouse.addResource(ResourceType.STONE);
        assertEquals(5, warehouse.getNumberOf(ResourceType.STONE));
        assertEquals(0, warehouse.getNumberOf(ResourceType.SHIELD));
        assertEquals(1, warehouse.getNumberOf(ResourceType.SERVANT));
        assertEquals(0, warehouse.getNumberOf(ResourceType.FAITH));
    }

    @Test
    void totalResourcesInLeaderTest()
            throws NotEnoughSpaceException, AbuseOfFaithException, MaxLeaderCardLevelsException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        assertEquals(warehouse.totalResources(), 0);
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        assertEquals(warehouse.totalResources(), 0);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.GOLD);
        warehouse.addResource(ResourceType.STONE);
        assertEquals(4, warehouse.totalResources());
        warehouse.addResource(ResourceType.GOLD);
        assertEquals(5, warehouse.totalResources());
        warehouse.addResource(ResourceType.GOLD);
        assertEquals(6, warehouse.totalResources());
        warehouse.removeResources(ResourceType.GOLD, 1);
        assertEquals(5, warehouse.totalResources());
        warehouse.removeResources(ResourceType.GOLD, 3);
        warehouse.addResource(ResourceType.STONE);
        warehouse.addResource(ResourceType.SERVANT);
        assertEquals(4, warehouse.totalResources());
    }

    @Test
    void removeNegativeQuantityTest() throws AbuseOfFaithException, NotEnoughSpaceException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.GOLD);
        assertThrows(NegativeQuantityException.class, () -> warehouse.removeResources(ResourceType.GOLD, -1) );
        warehouse.removeResources(ResourceType.GOLD, 0);
        warehouse.removeResources(ResourceType.GOLD, 1);
    }

}
