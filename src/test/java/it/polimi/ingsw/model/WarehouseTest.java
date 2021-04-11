package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.model.modelexceptions.*;


class WarehouseTest {

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
    void removeNegativeQuantityTest() throws AbuseOfFaithException, NotEnoughSpaceException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResource(ResourceType.GOLD);
        assertThrows(NegativeQuantityException.class, () -> warehouse.removeResources(ResourceType.GOLD, -1) );
        warehouse.removeResources(ResourceType.GOLD, 0);
        warehouse.removeResources(ResourceType.GOLD, 1);
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
    void addLeaderCardTest() throws LevelAlreadyPresentException, MaxLeaderCardLevelsException,
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
    void maxLeaderCardTest() throws LevelAlreadyPresentException, MaxLeaderCardLevelsException,
            AbuseOfFaithException, NotEnoughSpaceException {
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
    void leaderAlreadyPresentTest()
            throws LevelAlreadyPresentException, AbuseOfFaithException, MaxLeaderCardLevelsException {
        Warehouse warehouse = new Warehouse();
        warehouse.addLeaderCardLevel(ResourceType.GOLD);
        assertThrows(LevelAlreadyPresentException.class, () -> warehouse.addLeaderCardLevel(ResourceType.GOLD));
        assertEquals(1, warehouse.numberOfLeaderCardsLevels());
    }



//    @Test
//    void addAndRemoveInWarehouseIncorrectResourceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.addResources(ResourceType.GOLD, 1, 2));
//        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.addResources(ResourceType.SERVANT, 0, 2));
//        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.removeResources(ResourceType.SERVANT, 0, 2));
//    }
//
//    @Test
//    void addAndRemoveInWarehouseNotEnoughSpaceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, 4));
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.STONE, 1, 3));
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.SHIELD, 2, 2));
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        warehouse.addResources(ResourceType.STONE, 1, 1);
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, 2));
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.STONE, 1, 2));
//        warehouse.addResources(ResourceType.GOLD, 0, 1);
//        warehouse.addResources(ResourceType.STONE, 1, 1);
//        warehouse.addResources(ResourceType.GOLD, 0, 0);
//        warehouse.addResources(ResourceType.STONE, 1, 0);
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, 1));
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.STONE, 1, 1));
//    }
//
//    @Test
//    void addAbuseOfFaithTest() {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(AbuseOfFaithException.class, () -> warehouse.addResources(ResourceType.FAITH, 0, 1));
//    }
//
//    @Test
//    void addAndRemoveLevelNotExistsTest() {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(LevelNotExistsException.class, () -> warehouse.addResources(ResourceType.GOLD, -1, 1));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.addResources(ResourceType.GOLD, 3, 1));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.removeResources(ResourceType.GOLD, -1, 1));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.removeResources(ResourceType.GOLD, 3, 1));
//    }
//
//    @Test
//    void addAndRemoveNegativeQuantityTest() {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(NegativeQuantityException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, -2));
//        assertThrows(NegativeQuantityException.class, () -> warehouse.removeResources(ResourceType.GOLD, 1, -1));
//    }
//
//    @Test
//    void removeNotEnoughResourcesTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(NotEnoughResourcesException.class, () -> warehouse.removeResources(ResourceType.GOLD, 0, 1));
//        warehouse.addResources(ResourceType.GOLD, 0, 1);
//        warehouse.removeResources(ResourceType.GOLD, 0, 0);
//        assertThrows(NotEnoughResourcesException.class, () -> warehouse.removeResources(ResourceType.GOLD, 0, 2));
//        warehouse.removeResources(ResourceType.GOLD, 1, 0);
//        warehouse.removeResources(ResourceType.GOLD, 0, 1);
//        warehouse.removeResources(ResourceType.GOLD, 1, 0);
//    }
//
//    @Test
//    void swapTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);        // l0: 2 gold    | l1:            | l2:
//        warehouse.addResources(ResourceType.SERVANT, 1, 2);     // l0: 2 gold    | l1: 2 servant  | l2:
//        warehouse.swapLevels(0, 1);                                         //  l0: 2 servant | l1: 2 gold     | l2:
//        warehouse.removeResources(ResourceType.SERVANT, 0, 1);  // l0: 1 servant | l1: 2 gold     | l2:
//        warehouse.removeResources(ResourceType.GOLD, 1, 1);    // l0: 1 servant  | l1: 1 gold     | l2:
//        warehouse.swapLevels(1, 2);                                         //  l0: 1 servant | l1:            | l2: 1 gold
//        warehouse.swapLevels(1, 1);                                         //  l0: 1 servant | l1:            | l2: 1 gold
//        warehouse.removeResources(ResourceType.GOLD, 2, 1);    //  l0: 1 servant | l1:            | l2:
//        warehouse.swapLevels(0, 1);                                         //  l0:           | l1: 1 servant  | l2:
//        warehouse.swapLevels(0, 1);                                         //  l0: 1 servant | l1:            | l2:
//        warehouse.swapLevels(1, 2);                                         //  l0: 1 servant | l1:            | l2:
//        warehouse.swapLevels(0, 1);                                         //  l0:           | l1: 1 servant  | l2:
//        warehouse.swapLevels(1, 2);                                         //  l0:           | l1:            | l2: 1 servant
//        warehouse.swapLevels(0, 0);
//    }
//
//    @Test
//    void swapNotEnoughSpaceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.swapLevels(0, 2));
//        warehouse.addResources(ResourceType.GOLD, 0, 1);
//        warehouse.addResources(ResourceType.STONE, 1, 2);
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.swapLevels(0, 1));
//    }
//
//    @Test
//    void swapLevelNotExistTest() {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-1, 2));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(0, 20));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(1, 20));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-1, 20));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-1, -2));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(20, 21));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(20, 20));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-5, -5));
//    }
//
//    @Test
//    void moveTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        warehouse.moveResource(0, 0, 2);
//        warehouse.moveResource(0, 1, 2);
//        warehouse.moveResource(0, 1, 0);
//        warehouse.moveResource(1, 0, 0);
//        assertThrows(NotEnoughResourcesException.class, () -> warehouse.removeResources(ResourceType.GOLD, 0, 2));
//        warehouse.removeResources(ResourceType.GOLD, 1, 2);
//    }
//
//    @Test
//    void moveNegativeQuantityTest() throws IncorrectResourceTypeException, NegativeQuantityException,
//            LevelNotExistsException, NotEnoughSpaceException, AbuseOfFaithException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        assertThrows(NegativeQuantityException.class, () -> warehouse.moveResource(0, 1, -1));
//    }
//
//    @Test
//    void moveNotEnoughResourcesTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 1);
//        assertThrows(NotEnoughResourcesException.class, () -> warehouse.moveResource(0, 1, 2));
//        assertThrows(NotEnoughResourcesException.class, () -> warehouse.moveResource(1, 2, 2));
//    }
//
//    @Test
//    void moveNotEnoughSpaceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.moveResource(0, 2, 2));
//        warehouse.addResources(ResourceType.GOLD, 0, 1);
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.moveResource(0, 1, 3));
//        assertThrows(NotEnoughSpaceException.class, () -> warehouse.moveResource(0, 2, 3));
//    }
//
//    @Test
//    void moveIncorrectResourceTypeTest() throws IncorrectResourceTypeException, LevelNotExistsException,
//            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
//        Warehouse warehouse = new Warehouse();
//        warehouse.addResources(ResourceType.GOLD, 0, 2);
//        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.moveResource(0, 2, 1));
//        warehouse.addResources(ResourceType.SERVANT, 1, 1);
//        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.moveResource(0, 1, 1));
//    }
//
//    @Test
//    void moveLevelNotExistTest() {
//        Warehouse warehouse = new Warehouse();
//        assertThrows(LevelNotExistsException.class, () -> warehouse.moveResource(0, 4, 1));
//        assertThrows(LevelNotExistsException.class, () -> warehouse.moveResource(-1, 1, 1));
//    }
//
//    @Test
//    void resourcesAmountTest() throws IncorrectResourceTypeException, NegativeQuantityException,
//            LevelNotExistsException, NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException {
//        Warehouse warehouse = new Warehouse();
//        assertEquals(warehouse.getNumberOf(ResourceType.FAITH), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.STONE), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.SHIELD), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.SERVANT), 0);
//        warehouse.addResources(ResourceType.GOLD, 0, 3);
//        warehouse.addResources(ResourceType.STONE, 1, 1);
//        warehouse.addResources(ResourceType.SHIELD, 2, 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 3);
//        assertEquals(warehouse.getNumberOf(ResourceType.STONE), 1);
//        assertEquals(warehouse.getNumberOf(ResourceType.SHIELD), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.SERVANT), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.FAITH), 0);
//        warehouse.removeResources(ResourceType.GOLD, 0, 1);
//        warehouse.addResources(ResourceType.STONE, 1, 1);
//        warehouse.addResources(ResourceType.SERVANT, 2, 1);
//        assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 2);
//        assertEquals(warehouse.getNumberOf(ResourceType.STONE), 2);
//        assertEquals(warehouse.getNumberOf(ResourceType.SHIELD), 0);
//        assertEquals(warehouse.getNumberOf(ResourceType.SERVANT), 1);
//        assertEquals(warehouse.getNumberOf(ResourceType.FAITH), 0);
//    }
//
//    @Test
//    void totalResourcesTest() throws IncorrectResourceTypeException, NegativeQuantityException,
//            LevelNotExistsException, NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException {
//        Warehouse warehouse = new Warehouse();
//        assertEquals(warehouse.totalResources(), 0);
//        warehouse.addResources(ResourceType.GOLD, 0, 3);
//        warehouse.addResources(ResourceType.STONE, 1, 1);
//        warehouse.addResources(ResourceType.SHIELD, 2, 0);
//        assertEquals(warehouse.totalResources(), 4);
//        warehouse.removeResources(ResourceType.GOLD, 0, 1);
//        warehouse.addResources(ResourceType.STONE, 1, 1);
//        warehouse.addResources(ResourceType.SERVANT, 2, 1);
//        assertEquals(warehouse.totalResources(), 5);
//    }

}
