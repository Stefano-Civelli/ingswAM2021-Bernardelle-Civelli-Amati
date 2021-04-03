package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.model.modelexceptions.*;


class WarehouseTest {

    @Test
    public void addInWarehouseTest() throws IncorrectResourceTypeException, LevelNotExistsException,
            NotEnoughSpaceException, AbuseOfFaithException, NotEnoughResourcesException {
        Warehouse warehouse = new Warehouse();
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

}