package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.model.modelexceptions.*;


class WarehouseTest {

    @Test
    void addAndRemoveInWarehouseTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResources(ResourceType.SERVANT, 0, 0);
        warehouse.addResources(ResourceType.GOLD, 0, 2);
        warehouse.addResources(ResourceType.SERVANT, 1, 2);
        warehouse.addResources(ResourceType.GOLD, 0, 1);
        warehouse.removeResources(ResourceType.GOLD, 0, 3);
        warehouse.removeResources(ResourceType.SERVANT, 1, 1);
        warehouse.addResources(ResourceType.GOLD, 0, 2);
        warehouse.addResources(ResourceType.STONE, 2, 1);
        warehouse.removeResources(ResourceType.SERVANT, 1, 1);
        warehouse.removeResources(ResourceType.STONE, 2, 1);
        warehouse.removeResources(ResourceType.GOLD, 0, 1);
    }

    @Test
    void addAndRemoveInWarehouseIncorrectResourceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResources(ResourceType.GOLD, 0, 2);
        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.addResources(ResourceType.GOLD, 1, 2));
        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.addResources(ResourceType.SERVANT, 0, 2));
        assertThrows(IncorrectResourceTypeException.class, () -> warehouse.removeResources(ResourceType.SERVANT, 0, 2));
    }

    @Test
    void addAndRemoveInWarehouseNotEnoughSpaceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, 4));
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.STONE, 1, 3));
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.SHIELD, 2, 2));
        warehouse.addResources(ResourceType.GOLD, 0, 2);
        warehouse.addResources(ResourceType.STONE, 1, 1);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, 2));
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.STONE, 1, 2));
        warehouse.addResources(ResourceType.GOLD, 0, 1);
        warehouse.addResources(ResourceType.STONE, 1, 1);
        warehouse.addResources(ResourceType.GOLD, 0, 0);
        warehouse.addResources(ResourceType.STONE, 1, 0);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, 1));
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.addResources(ResourceType.STONE, 1, 1));
    }

    @Test
    void addAbuseOfFaithTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(AbuseOfFaithException.class, () -> warehouse.addResources(ResourceType.FAITH, 0, 1));
    }

    @Test
    void addAndRemoveLevelNotExistsTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(LevelNotExistsException.class, () -> warehouse.addResources(ResourceType.GOLD, -1, 1));
        assertThrows(LevelNotExistsException.class, () -> warehouse.addResources(ResourceType.GOLD, 3, 1));
        assertThrows(LevelNotExistsException.class, () -> warehouse.removeResources(ResourceType.GOLD, -1, 1));
        assertThrows(LevelNotExistsException.class, () -> warehouse.removeResources(ResourceType.GOLD, 3, 1));
    }

    @Test
    void addAndRemoveNegativeQuantityTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(NegativeQuantityException.class, () -> warehouse.addResources(ResourceType.GOLD, 0, -2));
        assertThrows(NegativeQuantityException.class, () -> warehouse.removeResources(ResourceType.GOLD, 1, -1));
    }

    @Test
    void removeNotEnoughResourcesTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        assertThrows(NotEnoughResourcesException.class, () -> warehouse.removeResources(ResourceType.GOLD, 0, 1));
        warehouse.addResources(ResourceType.GOLD, 0, 1);
        warehouse.removeResources(ResourceType.GOLD, 0, 0);
        assertThrows(NotEnoughResourcesException.class, () -> warehouse.removeResources(ResourceType.GOLD, 0, 2));
        warehouse.removeResources(ResourceType.GOLD, 1, 0);
        warehouse.removeResources(ResourceType.GOLD, 0, 1);
        warehouse.removeResources(ResourceType.GOLD, 1, 0);
    }

    @Test
    void swapTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResources(ResourceType.GOLD, 0, 2);        // l0: 2 gold    | l1:            | l2:
        warehouse.addResources(ResourceType.SERVANT, 1, 2);     // l0: 2 gold    | l1: 2 servant  | l2:
        warehouse.swapLevels(0, 1);                                         //  l0: 2 servant | l1: 2 gold     | l2:
        warehouse.removeResources(ResourceType.SERVANT, 0, 1);  // l0: 1 servant | l1: 2 gold     | l2:
        warehouse.removeResources(ResourceType.GOLD, 1, 1);    // l0: 1 servant  | l1: 1 gold     | l2:
        warehouse.swapLevels(1, 2);                                         //  l0: 1 servant | l1:            | l2: 1 gold
        warehouse.swapLevels(1, 1);                                         //  l0: 1 servant | l1:            | l2: 1 gold
        warehouse.removeResources(ResourceType.GOLD, 2, 1);    //  l0: 1 servant | l1:            | l2:
        warehouse.swapLevels(0, 1);                                         //  l0:           | l1: 1 servant  | l2:
        warehouse.swapLevels(0, 1);                                         //  l0: 1 servant | l1:            | l2:
        warehouse.swapLevels(1, 2);                                         //  l0: 1 servant | l1:            | l2:
        warehouse.swapLevels(0, 1);                                         //  l0:           | l1: 1 servant  | l2:
        warehouse.swapLevels(1, 2);                                         //  l0:           | l1:            | l2: 1 servant
        warehouse.swapLevels(0, 0);
    }

    @Test
    void swapNotEnoughSpaceTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NegativeQuantityException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResources(ResourceType.GOLD, 0, 2);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.swapLevels(0, 2));
        warehouse.addResources(ResourceType.GOLD, 0, 1);
        warehouse.addResources(ResourceType.STONE, 1, 2);
        assertThrows(NotEnoughSpaceException.class, () -> warehouse.swapLevels(0, 1));
    }

    @Test
    void swapLevelNotExistTest() {
        Warehouse warehouse = new Warehouse();
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-1, 2));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(0, 20));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(1, 20));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-1, 20));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-1, -2));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(20, 21));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(20, 20));
        assertThrows(LevelNotExistsException.class, () -> warehouse.swapLevels(-5, -5));
    }

    @Test
    void resourcesAmountTest() throws IncorrectResourceTypeException, NegativeQuantityException,
            LevelNotExistsException, NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException {
        Warehouse warehouse = new Warehouse();
        assertEquals(warehouse.getNumberOf(ResourceType.FAITH), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.STONE), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.SHIELD), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.SERVANT), 0);
        warehouse.addResources(ResourceType.GOLD, 0, 3);
        warehouse.addResources(ResourceType.STONE, 1, 1);
        warehouse.addResources(ResourceType.SHIELD, 2, 0);
        assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 3);
        assertEquals(warehouse.getNumberOf(ResourceType.STONE), 1);
        assertEquals(warehouse.getNumberOf(ResourceType.SHIELD), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.SERVANT), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.FAITH), 0);
        warehouse.removeResources(ResourceType.GOLD, 0, 1);
        warehouse.addResources(ResourceType.STONE, 1, 1);
        warehouse.addResources(ResourceType.SERVANT, 2, 1);
        assertEquals(warehouse.getNumberOf(ResourceType.GOLD), 2);
        assertEquals(warehouse.getNumberOf(ResourceType.STONE), 2);
        assertEquals(warehouse.getNumberOf(ResourceType.SHIELD), 0);
        assertEquals(warehouse.getNumberOf(ResourceType.SERVANT), 1);
        assertEquals(warehouse.getNumberOf(ResourceType.FAITH), 0);
    }

    @Test
    void totalResourcesTest() throws IncorrectResourceTypeException, NegativeQuantityException,
            LevelNotExistsException, NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException {
        Warehouse warehouse = new Warehouse();
        assertEquals(warehouse.totalResources(), 0);
        warehouse.addResources(ResourceType.GOLD, 0, 3);
        warehouse.addResources(ResourceType.STONE, 1, 1);
        warehouse.addResources(ResourceType.SHIELD, 2, 0);
        assertEquals(warehouse.totalResources(), 4);
        warehouse.removeResources(ResourceType.GOLD, 0, 1);
        warehouse.addResources(ResourceType.STONE, 1, 1);
        warehouse.addResources(ResourceType.SERVANT, 2, 1);
        assertEquals(warehouse.totalResources(), 5);
    }

}